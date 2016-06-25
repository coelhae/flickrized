package pt.alex.flickrized.modules.details.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import pt.alex.flickrized.R;
import pt.alex.flickrized.data.DataHelper;
import pt.alex.flickrized.modules.details.ActivityDetails;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private final static String POSITION ="POSITION";

    private int pageNumber;
    private View fView;
    private int position;

    private AppCompatImageView itemImage;
    private TextView lbName;

    // bind activity
    private ActivityDetails acitvity;

    private PhotoViewAttacher attacher;


    public  DetailsFragment() {
        // Required empty public constructor

    }

    public static DetailsFragment getInstance(int pos){
        DetailsFragment df = new DetailsFragment();
        Bundle b = new Bundle(1);
        b.putInt(POSITION, pos);
        df.setArguments(b);
        return df;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        position = getArguments().getInt(POSITION);
        Log.w("Create Pos :" ,position+"");

        fView = inflater.inflate(R.layout.details_fragment,container,false);
        viewHocks();
        initDetailsViews();

        return fView;
    }

    private void viewHocks() {
        lbName = (TextView)fView.findViewById(R.id.lb_photo_name);
        itemImage = (AppCompatImageView) fView.findViewById(R.id.item_image);
    }

    private void initDetailsViews() {
        //1 carrega imagem que vem da lista
        Picasso.with(acitvity).load(DataHelper.of().getPhotos().get(position).getSmall320Url()).into(itemImage);
        //2 Carrega a imagem por trás com mais qualidade
        attacher = new PhotoViewAttacher(itemImage); //attach photoImage Handler
        Picasso.with(acitvity).load(DataHelper.of().getPhotos().get(position).getMedium800Url()).into(itemImage, new Callback() {
            @Override
            public void onSuccess() {
                attacher.update();
            }

            @Override
            public void onError() {

            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        acitvity = (ActivityDetails) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // clean up attacher eventlistener quando view é removida
        attacher.cleanup();
    }
}
