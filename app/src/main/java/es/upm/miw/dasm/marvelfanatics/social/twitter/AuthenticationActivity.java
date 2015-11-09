package es.upm.miw.dasm.marvelfanatics.social.twitter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import es.upm.miw.dasm.marvelfanatics.R;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@EActivity(R.layout.activity_authentication)
public class AuthenticationActivity extends AppCompatActivity {

    @ViewById(R.id.twitterLogin)
    RelativeLayout twitterLogin;

    @ViewById(R.id.twitterProfile)
    RelativeLayout twitterProfile;

    @ViewById(R.id.twitterUserName)
    TextView twitterUserName;

    @ViewById(R.id.twitterUserPicture)
    ImageView twitterUserPicture;

    private Twitter twitter;
    private RequestToken requestToken;
    final int WEBVIEW_RESULT_CODE = 100;

    @Pref
    TwitterPreferences_ twitterPreferences;

    @AfterViews
    void init() {
        this.updateUserProfileView();
    }

    @Click(R.id.actionTwitterLogin)
    void login() {
        new AsyncTwitterLogin().execute();
    }

    @Click(R.id.actionTwitterLogout)
    void logout() {
        twitterPreferences.clear();
        updateUserProfileView();
    }

    private class AsyncTwitterLogin extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

            configurationBuilder.setOAuthConsumerKey(getString(R.string.public_key_twitter));
            configurationBuilder.setOAuthConsumerSecret(getString(R.string.private_key_twitter));

            Configuration configuration = configurationBuilder.build();

            twitter = new TwitterFactory(configuration).getInstance();

            try {
                requestToken = twitter.getOAuthRequestToken(getString(R.string.twitter_callback_url));

                Intent AuthenticationWebviewIntent = new Intent(AuthenticationActivity.this,
                        AuthenticationWebviewActivity_.class);
                AuthenticationWebviewIntent.putExtra("OAUTH_URL", requestToken.getAuthenticationURL());

                startActivityForResult(AuthenticationWebviewIntent, WEBVIEW_RESULT_CODE);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @OnActivityResult(WEBVIEW_RESULT_CODE)
    void onResult(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return; // guard clause
        }

        if (data == null) {
            return; // guard clause
        }

        final Uri uri = Uri.parse(data.getStringExtra("KEY_URI"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                String verifier = uri.getQueryParameter(getString(R.string.url_twitter_oauth_verifier));

                try {
                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

                    updatePreferences(accessToken);

                    AuthenticationActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUserProfileView();
                        }
                    });

                } catch (Exception e) {
                    Log.e("TWITTER_ERROR", e.getMessage());
                }
            }
        }).start();
    }

    private void updatePreferences(AccessToken accessToken) {
        try {
            User user = twitter.showUser(accessToken.getUserId());

            twitterPreferences.edit()
                    .isUserLoggedIn().put(true)
                    .userName().put(user.getName())
                    .userPicture().put(user.getOriginalProfileImageURL())
                    .oauthToken().put(accessToken.getToken())
                    .oauthSecret().put(accessToken.getTokenSecret())
                    .apply();
        } catch (TwitterException e) {
            Log.e("TWITTER_ERROR", e.getMessage());
        }
    }

    private void updateUserProfileView() {
        if (this.twitterPreferences.isUserLoggedIn().get()) {
            twitterLogin.setVisibility(View.GONE);
            twitterProfile.setVisibility(View.VISIBLE);

            Picasso.with(this)
                    .load(twitterPreferences.userPicture().get())
                    .into(twitterUserPicture);

            twitterUserName.setText(twitterPreferences.userName().get());
        } else {
            twitterLogin.setVisibility(View.VISIBLE);
            twitterProfile.setVisibility(View.GONE);
        }
    }
}
