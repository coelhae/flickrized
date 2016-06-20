package pt.alex.flickrized.modules.login;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pt.alex.flickrized.R;
import pt.alex.flickrized.data.User;
import pt.alex.flickrized.network.FlickrService;


public class WebAuthActivity extends AppCompatActivity {


    private WebView webView;
    private WebViewClient webClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_auth);

        setViewHooks();
        setWeClient();

        webView.setWebViewClient(webClient);

        String url = (String) getIntent().getExtras().get("AUTH_URL");


        webView.loadUrl(url);

    }

    /**
     * Bem podia fazer isto com butterknife, mas...
     */
    private void setViewHooks() {
        webView = (WebView) findViewById(R.id.webview);

    }


    private void setWeClient(){

        // set properties
        webView.getSettings().setJavaScriptEnabled(true);


        webClient = new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //super.onPageStarted(view, url, favicon);
                if(url!=null && url.startsWith(FlickrService.URI)){

                        webView.stopLoading();
                        Uri callback = Uri.parse(url);
                        Log.d("auth token", callback.getQueryParameter("oauth_token"));
                        Log.d("auth verifier", callback.getQueryParameter("oauth_verifier"));
                        // finish it
                        User.of().setAuthToken(callback.getQueryParameter("oauth_token"));
                        User.of().setOuthVerifier(callback.getQueryParameter("oauth_verifier"));
                        setResult(1);
                        WebAuthActivity.this.finish(); // ok
                }else {
                    super.onPageStarted(view, url, favicon);
                    Log.e("OnPgestarted", url);
                }
            }
        };
    }
}
