package es.upm.miw.dasm.marvelfanatics.api.managers.twitter;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;

import es.upm.miw.dasm.marvelfanatics.R;
import es.upm.miw.dasm.marvelfanatics.api.models.twitter.Tweet;
import es.upm.miw.dasm.marvelfanatics.api.services.twitter.TweetService;
import es.upm.miw.dasm.marvelfanatics.social.twitter.TwitterPreferences_;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;
import twitter4j.Twitter;

@EBean(scope = EBean.Scope.Singleton)
public class TwitterManager {
    private final String API_BASE_URL = "https://api.twitter.com/";
    private Retrofit retrofit;

    @RootContext
    Context context;

    @Pref
    TwitterPreferences_ twitterPreferences;

    private static TwitterManager ourInstance = null;
    private TweetService apiService;
    private OkHttpClient client;
    private Twitter twitter;

    @AfterInject
    void init() {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(
                context.getString(R.string.public_key_twitter),
                context.getString(R.string.private_key_twitter));
        consumer.setTokenWithSecret(twitterPreferences.oauthToken().get(),
                twitterPreferences.oauthSecret().get());

        OkHttpClient okHttpClient = new OkHttpClient();

        String token = twitterPreferences.userName().get();
        token = consumer.getToken();

        okHttpClient.interceptors().add(new SigningInterceptor(consumer));

        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.apiService = retrofit.create(TweetService.class);

    }

    public Call<Tweet> postTweet(String status) {
        return apiService.update(status);
    }
}
