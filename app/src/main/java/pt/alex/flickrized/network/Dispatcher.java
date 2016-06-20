package pt.alex.flickrized.network;

import android.os.Handler;
import android.os.Looper;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.Size;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import pt.alex.flickrized.data.User;

/**
 * Created by nb19875 on 19/06/16.
 */
public class Dispatcher {

    private static final Handler MAIN_THREAD = new Handler(
            Looper.getMainLooper());



        public static void call(Runnable runnable){

            MAIN_THREAD.post(runnable);
        }


    /**
     * feio
     * @param callback
     */
    public static  void getUserByUserName(final Callback callback){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Flickr f = FlickrHelper.of().getFlickerAuthed();
                        com.googlecode.flickrjandroid.people.User byUsername = f.getPeopleInterface().findByUsername(User.DUMMY_USER); // usar um user ramdomU
                        callback.onResponse(byUsername);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (FlickrException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


    public static  void getPublicPhotos(final Callback callback, final String userId, final int perPage,final int page ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Flickr f = FlickrHelper.of().getFlickerAuthed();
                    PhotoList publicPhotos = f.getPeopleInterface().getPublicPhotos(userId, perPage, page);
                    callback.onResponse(publicPhotos);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FlickrException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public static  void getPhotoGetInfo(final Callback callback, final String photoId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Flickr f = FlickrHelper.of().getFlickerAuthed();
                    Photo info = f.getPhotosInterface().getInfo(photoId, null);
                    callback.onResponse(info);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FlickrException e) {
                    e.printStackTrace();
                    callback.onError(e);
                }
            }
        }).start();
    }

    public static  void getPhotoSizes(final Callback callback, final String photoId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Flickr f = FlickrHelper.of().getFlickerAuthed();
                    List<Size> sizes = (List<Size>) f.getPhotosInterface().getSizes(photoId);
                    callback.onResponse(sizes);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FlickrException e) {
                    e.printStackTrace();
                    callback.onError(e);
                }
            }
        }).start();
    }
    public static  void getLockingPhotoSizes(final Callback callback, final String photoId){

                try {
                    Flickr f = FlickrHelper.of().getFlickerAuthed();
                    List<Size> sizes = (List<Size>) f.getPhotosInterface().getSizes(photoId);
                    callback.onResponse(sizes);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FlickrException e) {
                    e.printStackTrace();
                    callback.onError(e);
                }
    }





    public interface Callback<T>{
        public void onResponse(T response);
        public void onError(FlickrException response);
    }
}
