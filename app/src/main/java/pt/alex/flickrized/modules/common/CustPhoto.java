package pt.alex.flickrized.modules.common;

import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.Size;

import java.util.Collection;

/**
 * Created by nb19875 on 19/06/16.
 */
public class CustPhoto extends Photo{

    private Collection<Size> photoSizes;


    public CustPhoto() {
        super();

    }

    public Collection<Size> getPhotoSizes() {
        return photoSizes;
    }

    public void setPhotoSizes(Collection<Size> photoSizes) {
        this.photoSizes = photoSizes;
    }
}
