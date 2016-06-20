package pt.alex.flickrized.network;

import android.os.Handler;
import android.os.Looper;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.REST;
import com.googlecode.flickrjandroid.RequestContext;
import com.googlecode.flickrjandroid.oauth.OAuth;
import com.googlecode.flickrjandroid.oauth.OAuthToken;

import javax.xml.parsers.ParserConfigurationException;

import pt.alex.flickrized.aplication.FlickApplication;
import pt.alex.flickrized.data.User;

/**
 * Created by nb19875 on 19/06/16.
 */
public class FlickrHelper {

    private static  FlickrHelper instance;

    private static final Handler MAIN_THREAD = new Handler(
            Looper.getMainLooper());

    private FlickrHelper() {
    }

    public static FlickrHelper of(){
        if(instance == null){
            instance = new FlickrHelper();
        }
        return instance;
    }

    public Flickr getFlickr()  {
        Flickr flick = null;
        try {
            flick = new Flickr(FlickApplication.KEY,FlickApplication.SECRET, new REST());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return  flick;
    }

    public Flickr getFlickerAuthed(){
        Flickr f = getFlickr();
        RequestContext rCtx = RequestContext.getRequestContext();

        OAuth a = new OAuth();
        a.setToken(new OAuthToken(User.of().getAuthToken(), User.of().getTokenSecret()));
        rCtx.setOAuth(a);

        return f;
    }
}
