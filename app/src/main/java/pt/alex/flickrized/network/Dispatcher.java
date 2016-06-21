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
import java.util.Objects;

import pt.alex.flickrized.aplication.FlickApplication;
import pt.alex.flickrized.data.User;

/**
 * Created by nb19875 on 19/06/16.
 */
public class Dispatcher {



    private static final Handler MAIN_THREAD = new Handler();

    private  Dispatcher() {
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
                        uiSync(callback,byUsername,NeedSync.OK);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (FlickrException e) {
                        e.printStackTrace();
                        uiSync(callback,e,NeedSync.ERROR);
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
                    /***
                     * improve load performace, apenas retorna quando tiver os tamanhos disponiveis
                     */
                    for(Photo p : publicPhotos) {
                        List<Size> sizes = (List<Size>) f.getPhotosInterface().getSizes(p.getId());
                        p.setSizes(sizes);
                    }

                    uiSync(callback,publicPhotos,NeedSync.OK);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FlickrException e) {
                    e.printStackTrace();
                    uiSync(callback,e,NeedSync.ERROR);
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


                    uiSync(callback,info,NeedSync.OK);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FlickrException e) {
                    e.printStackTrace();
                    uiSync(callback,e,NeedSync.ERROR);
                }
            }
        }).start();
    }



    /**
     * Este é o unico metodo necessário para ficar sync com a UI para Já
     * @param callback
     * @param photoId
     */
    public  static  void getPhotoSizes(final Callback callback, final String photoId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Flickr f = FlickrHelper.of().getFlickerAuthed();
                     List<Size> sizes = (List<Size>) f.getPhotosInterface().getSizes(photoId);
                        uiSync(callback,sizes,NeedSync.OK);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (final FlickrException e) {
                    e.printStackTrace();
                    uiSync(callback,e,NeedSync.ERROR);
                }
            }
        }).start();
    }

    /**
     * Sync resposta com a ui
     * @param c
     * @param response
     * @param type
     */
    private static void uiSync(final Callback c, final Object response, final NeedSync type){
        MAIN_THREAD.post(new Runnable() {
            @Override
            public void run() {
                switch (type){
                    case OK:{
                        c.onResponse(response);
                        break;
                    }
                }
            }
        });
    }


    private enum NeedSync{
        OK, ERROR
    }

    public interface Callback<T>{
        public void onResponse(T response);
        public void onError(FlickrException response);
    }
}
