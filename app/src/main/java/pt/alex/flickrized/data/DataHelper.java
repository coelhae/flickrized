package pt.alex.flickrized.data;

import com.googlecode.flickrjandroid.photos.PhotoList;

/**
 * Created by nb19875 on 24/06/16.
 */
public class DataHelper {

    /***
     * Data que é consistente e deve ser assevivel em todas as actividades.
     * Definir como singleton para facil acesso.
     */

    private static DataHelper instance;

    private DataHelper() {
        photos = new PhotoList();
    }

    public static DataHelper of(){
        if(instance == null){
            instance = new DataHelper();
        }
        return instance;
    }

    // vars
    private PhotoList photos ;


    /** TODO:  add realm DB objects */
    // fazer peristencia em bd para maior rapidez


    public PhotoList getPhotos() {
        return photos;
    }

    public void setPhotos(PhotoList photos) {
        this.photos =  photos;
    }
}
