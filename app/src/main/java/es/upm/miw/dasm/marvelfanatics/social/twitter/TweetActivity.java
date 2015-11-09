package es.upm.miw.dasm.marvelfanatics.social.twitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import es.upm.miw.dasm.marvelfanatics.R;
import es.upm.miw.dasm.marvelfanatics.api.managers.twitter.TwitterManager;
import es.upm.miw.dasm.marvelfanatics.api.models.twitter.Tweet;
import es.upm.miw.dasm.marvelfanatics.api.models.twitter.TweetAppData;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

@EActivity(R.layout.activity_tweet)
@Fullscreen
public class TweetActivity extends AppCompatActivity {

    @ViewById(R.id.tweetContent)
    EditText tweetContent;

    @ViewById(R.id.charactersLeft)
    TextView charactersLeft;

    @Bean
    TwitterManager twitterManager;

    @Pref
    TwitterPreferences_ twitterPreferences;

    private TweetAppData tweetAppData;

    @AfterViews
    void init() {
        getSupportActionBar().setTitle(R.string.share_title);

        redirectIfUserIsNotLoggedIn();

        tweetAppData = getIntent().getParcelableExtra("COMIC_TWEET_DATA");
        tweetContent.setText(tweetAppData.getHashtag() + " " + tweetAppData.getComicName() + " ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        finishIfUserIsNotLoggedIn();
    }

    private void redirectIfUserIsNotLoggedIn() {
        if (!this.twitterPreferences.isUserLoggedIn().get()) {
            Intent authenticationIntent = new Intent(TweetActivity.this, AuthenticationActivity_.class);
            startActivity(authenticationIntent);
        }
    }

    private void finishIfUserIsNotLoggedIn() {
        if (!this.twitterPreferences.isUserLoggedIn().get()) {
            Toast.makeText(TweetActivity.this, "You must be logged in to send tweets", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @AfterTextChange(R.id.tweetContent)
    void updateCharactersLeft() {
        charactersLeft.setText(140 - tweetContent.getText().length() + "/140");
    }

    @Click(R.id.actionPostTweet)
    void postTweet() {
        twitterManager.postTweet(tweetContent.getText().toString())
                .enqueue(new Callback<Tweet>() {
                    @Override
                    public void onResponse(Response<Tweet> response, Retrofit retrofit) {
                        Log.i("REQUEST", response.headers().toString());
                        Log.i("REQUEST_SUCCESSFUL", String.valueOf(response.isSuccess()));
                        Toast.makeText(TweetActivity.this, "Your message has been sent", Toast.LENGTH_SHORT).show();
                        TweetActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("ERROR ON REQUEST", t.getMessage());
                        Toast.makeText(TweetActivity.this, "Failed to send your message", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
