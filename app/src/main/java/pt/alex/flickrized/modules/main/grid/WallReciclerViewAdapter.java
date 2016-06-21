package pt.alex.flickrized.modules.main.grid;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.googlecode.flickrjandroid.photos.PhotoList;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import pt.alex.flickrized.R;

/**
 * Created by nb19875 on 19/06/16.
 */
public class WallReciclerViewAdapter extends RecyclerView.Adapter<WallViewHolder> {


    private Context ctx;
    private PhotoList photos;

    private Bitmap[] preFetchedList;

    ContinuumScrollListener scroolListener;
    ItemClickListner clickListner;

    // numero suficiente
    private final int VISIBEL_THREASHOLD = 8;

    public WallReciclerViewAdapter(Context ctx, ItemClickListner listener) {
        this.clickListner = listener;
        this.ctx = ctx;
    }

    public void setPhotos(PhotoList photos) {
        this.photos = photos;
    }



    public void setEndlessListener(WallReciclerViewAdapter.ContinuumScrollListener scroolListener) {
        this.scroolListener = scroolListener;
    }


    @Override
    public WallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.flickr_card,parent,false);
        return new WallViewHolder(layout);

    }

    @Override
    public void onBindViewHolder(final WallViewHolder holder, final int position) {


        holder.username.setText(photos.get(position).getOwner().getUsername());


        if(position == getItemCount() - VISIBEL_THREASHOLD){
            if(scroolListener !=null){
                scroolListener.onContinueLoad(position);
            }
        }
        //TESTING PERFORMANCE ISSUE
        //TODO : fazer condição que diferencie o tamanho mediante o tamanho de ecrã
        if(photos.get(position).getMediumSize() != null){

            holder.thumb.getLayoutParams().height=photos.get(position).getLargeSize().getHeight();

            Picasso.with(ctx).load(photos.get(position).getSmall320Url())
                .into(holder.thumb);
        }

        holder.thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListner.onItemClick(position);
            }
        });

    }

    @Override
    public void onViewRecycled(WallViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        if(photos != null)
            return photos.size();
        else
            return 0;
    }


    /***
     *
     * coisa simples para continuar a carregar mais
     * Se com tempo alterar para um onScroolListener
     */

    public interface ContinuumScrollListener{
        boolean onContinueLoad(int pos);
    }

    public interface ItemClickListner{
        void onItemClick(int position);
    }


}
