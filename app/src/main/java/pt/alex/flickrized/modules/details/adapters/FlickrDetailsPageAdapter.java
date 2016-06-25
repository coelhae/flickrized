package pt.alex.flickrized.modules.details.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.googlecode.flickrjandroid.photos.PhotoList;

import pt.alex.flickrized.data.DataHelper;
import pt.alex.flickrized.modules.details.fragments.DetailsFragment;

/**
 * Created by nb19875 on 24/06/16.
 */
public class FlickrDetailsPageAdapter extends FragmentStatePagerAdapter{

    PhotoList photos = DataHelper.of().getPhotos();

    public FlickrDetailsPageAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        Log.e("FlickrDetailsPageAdapter", "Instancia "+position);
        return DetailsFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i("FlickrDetailsPageAdapter", "Position "+position);
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


}
