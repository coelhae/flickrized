package pt.alex.flickrized.modules.main.grid;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pt.alex.flickrized.R;

/**
 * Created by nb19875 on 19/06/16.
 */
public  class WallViewHolder extends RecyclerView.ViewHolder  {


    TextView username;
    AppCompatImageView thumb;
    TextView photoName;
    TextView year;


    public WallViewHolder(View itemView) {
        super(itemView);

        username = (TextView) itemView.findViewById(R.id.lb_user_name);
        thumb = (AppCompatImageView) itemView.findViewById(R.id.card_thumb);
        photoName = (TextView) itemView.findViewById(R.id.lb_photo_name);
        year = (TextView) itemView.findViewById(R.id.lb_year);


    }

}
