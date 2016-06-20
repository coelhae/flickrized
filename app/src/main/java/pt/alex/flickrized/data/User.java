package pt.alex.flickrized.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import pt.alex.flickrized.aplication.FlickApplication;
import pt.alex.flickrized.network.FlickrService;

/**
 * Created by nb19875 on 18/06/16.
 */
public class User {

    public static final String DUMMY_USER ="enneafive";

    private final String PREF_FILE= "f1";
    private final String TAG ="User";
    private final String name= "a1";
    private final String token= "a2";
    private final String secret= "a3";
    private final String nsid= "a4";
    private final String uname= "a5";
    private final String verifier="a6";

    private static  User user;

    private String fullName;
    private String authToken;
    private String tokenSecret;
    private String userNsid;
    private String username;
    private String outhVerifier;
    private String requestCode;

    private SharedPreferences sPrefs;


    private  User() {
        // guarda na area piv da app
        sPrefs = FlickApplication.getAppContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);

    }
    public static User of(){
        if(user == null){
            user = new User();
        }

        return user;
    }

    /**
     * vou usar shared prefs por uma questão de facilitismo
     */
    public void persistUser(){
        Log.w(TAG,"Persisting user" );
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putString(name, fullName );
        editor.putString(token, authToken );
        editor.putString(secret, tokenSecret);
        editor.putString(nsid , userNsid);
        editor.putString(uname, username);
        editor.putString(verifier, outhVerifier);

        editor.commit();
    }

    /***
     * True se o rapaz existir e for bem carregado
     * false se nao existir
     * @return
     */
    public void loadPersistedUser(){
        fullName   = sPrefs.getString(name, "" );
        authToken   = sPrefs.getString(token, "");
        tokenSecret = sPrefs.getString(secret, "");
        userNsid  = sPrefs.getString(nsid ,"");
        username    = sPrefs.getString(uname, "");
        outhVerifier = sPrefs.getString(verifier, "");
    }

    public boolean isUserLoaded(){

        // lets assume this is valid for the porpuse
        if( username != null && !username.isEmpty() ){
            return true;
        }
        return false;
    }


    // Bla bla
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getUserNsid() {
        return userNsid;
    }

    public void setUserNsid(String userNsid) {
        this.userNsid = userNsid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOuthVerifier() {
        return outhVerifier;
    }

    public void setOuthVerifier(String outhVerifier) {
        this.outhVerifier = outhVerifier;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }
}
