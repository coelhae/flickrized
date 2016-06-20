package pt.alex.flickrized.modules.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;

import pt.alex.flickrized.R;
import pt.alex.flickrized.data.User;
import pt.alex.flickrized.modules.main.MainDrawerActivity;
import pt.alex.flickrized.network.FlickrService;
import pt.alex.flickrized.utils.UrlUtils;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    static final int AUTH_RESULT = 1;
    private OAuth1RequestToken  requestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        actions();
    }

    private void actions(){
        findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validate if user already is loggedIn
                // check user is persisted
                User.of().loadPersistedUser();
                if(User.of().isUserLoaded()){
                    // lets always present first page
                    goFlicker();
                }else {
                    getAuthToken();
                }
            }
        });
    }


    // catch
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            switch (resultCode){
                case AUTH_RESULT : {
                    finalAuthCall();
                    break;
                }
                default:{
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }


    }

    /**
     * Starting da web web view
     * Vamos optar por uma actividade nova para nao ficar muito codigo no mesmo sitio mas podia ficar aqui também
     * @param url
     */
    private void displayLoginScreen(String url){
        Intent webIntent =  new Intent(this, WebAuthActivity.class);
        webIntent.putExtra("AUTH_URL", url );
        startActivityForResult(webIntent,AUTH_RESULT);
    }


    private void getAuthToken (){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                LoginActivity.this.requestToken =  FlickrService.of().getService().getRequestToken();
                Log.d(TAG, requestToken.getRawResponse());
                // add request Code To USer
                User.of().setAuthToken(LoginActivity.this.requestToken.getToken());
                User.of().setTokenSecret(LoginActivity.this.requestToken.getTokenSecret());
                return FlickrService.of().getService().getAuthorizationUrl(requestToken);
            }

            @Override
            protected void onPostExecute(String s) {

                displayLoginScreen(s);
            }
        }.execute();
    }

    private void goFlicker(){
        Intent i = new Intent(this, MainDrawerActivity.class);
        startActivity(i);
    }


    /**
     * feio
     */
    private void finalAuthCall (){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                OAuth1AccessToken accessToken = FlickrService.of().getService().getAccessToken(requestToken, User.of().getOuthVerifier());
                User.of().setAuthToken(accessToken.getToken());
                User.of().setTokenSecret(accessToken.getTokenSecret());
                User.of().setUsername(accessToken.getParameter("fullname"));
                User.of().setUserNsid(accessToken.getParameter("user_nsid"));
                User.of().setUsername(accessToken.getParameter("username"));

                Log.d(TAG, "raw : " + UrlUtils.decodeUrl(accessToken.getRawResponse()));

                // save user
                User.of().persistUser();

                return null;
            }

            @Override
            protected void onPostExecute(Void s) {
                goFlicker();
            }
        }.execute();


    }


}
