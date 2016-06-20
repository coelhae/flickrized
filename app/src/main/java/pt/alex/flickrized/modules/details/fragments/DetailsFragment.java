package pt.alex.flickrized.modules.details.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.alex.flickrized.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private int pageNumber;



    public  DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment of(int position){
        DetailsFragment frag = new DetailsFragment();
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_fragment,container,false);
        return view;
    }

}
