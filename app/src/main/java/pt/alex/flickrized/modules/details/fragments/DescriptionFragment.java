package pt.alex.flickrized.modules.details.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.photos.Photo;

import pt.alex.flickrized.R;
import pt.alex.flickrized.network.Dispatcher;

/**
 * Created by nb19875 on 25/06/16.
 */
public class DescriptionFragment extends Fragment {


    private View dView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        super.onCreateView(inflater, container, savedInstanceState);
            dView = inflater.inflate(R.layout.description_frag,container,false);

        /**
         * TODO : Carregar os dados das fotos
         */ 

        return dView;
    }


    private  void getPhotoInfo(final Photo ph){

        Dispatcher.getPhotoGetInfo(new Dispatcher.Callback() {
            @Override
            public void onResponse(Object response) {
                Log.d("grp", response.toString());
            }

            @Override
            public void onError(FlickrException response) {

            }
        }, ph.getId());
    }

}
