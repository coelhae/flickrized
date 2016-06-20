package pt.alex.flickrized.network;

import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth10aService;

import pt.alex.flickrized.aplication.FlickApplication;

/**
 * Created by nb19875 on 18/06/16.
 */
public class FlickrService {


    private static FlickrService instance ;
    private OAuth10aService service;
    public static final String URI= "https://flickrized.com";

    private FlickrService() {
        service = new ServiceBuilder()
                .apiKey(FlickApplication.KEY)
                .apiSecret(FlickApplication.SECRET)
                .callback(FlickrService.URI).build(FlickrApi.instance());
    }



    public static FlickrService of(){

        if(instance == null)
        {
            instance = new FlickrService();
        }
        return instance;
    }

    public OAuth10aService getService(){
        return   service;
    }
}
