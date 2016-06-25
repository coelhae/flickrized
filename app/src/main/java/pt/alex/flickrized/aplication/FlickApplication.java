package pt.alex.flickrized.aplication;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

/**
 * Created by nb19875 on 18/06/16.
 */
public class FlickApplication extends Application {

    public static final String SECRET = "48ef59000a755a6b";
    public static final String KEY = "8675207c288d4770f2dac68f878b508d";
    private static Context ctx;
//    private static Realm flickrRealm;


    @Override
    public void onCreate() {
        super.onCreate();
        // vamos iniciar um context a apontar para a nossa Aplication class
        ctx = this;

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.memoryCache(new LruCache(this));
        Picasso picasso = builder.build();
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);

        Picasso.setSingletonInstance(picasso);

//        initRealm();

    }

//    private void initRealm() {
//         flickrRealm = Realm.getInstance(new RealmConfiguration.Builder(this)
//                .name("flickrRealm")
//                .build()
//        );
//    }


    public static Context getAppContext() {
        return ctx;
    }


//    public static Realm getFlickrRealm() {
//        return flickrRealm;
//    }
}
