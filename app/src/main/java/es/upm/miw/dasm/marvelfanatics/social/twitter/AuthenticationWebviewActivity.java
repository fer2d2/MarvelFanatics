package es.upm.miw.dasm.marvelfanatics.social.twitter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import es.upm.miw.dasm.marvelfanatics.R;

@EActivity(R.layout.activity_authentication_webview)
public class AuthenticationWebviewActivity extends AppCompatActivity {
    @ViewById(R.id.twitterAuthenticationWebView)
    WebView twitterAuthenticationWebView;

    @AfterViews
    void init() {
        String url = this.getIntent().getStringExtra("OAUTH_URL");

        if (url == null) {
            Log.e("TWITTER", "URL is null");
            finish();
        }

        configureWebviewClient();

        twitterAuthenticationWebView.loadUrl(url);
    }

    private void configureWebviewClient() {
        twitterAuthenticationWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(getString(R.string.twitter_callback_url))) {
                    return false;
                }

                Uri uri = Uri.parse(url);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("KEY_URI", uri.toString());
                setResult(RESULT_OK, resultIntent);
                finish();

                return true;
            }
        });
    }
}
