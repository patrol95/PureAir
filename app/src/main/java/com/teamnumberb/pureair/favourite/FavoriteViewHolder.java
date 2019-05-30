package com.teamnumberb.pureair.favourite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamnumberb.pureair.R;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private List<Place> favourites = new ArrayList<>();
    FavouritesManager favouritesManager;

    public IFavoriteViewHolderClick mListener;
    public TextView mTextView;
    public ImageView mImageView;

    public FavoriteViewHolder(@NonNull View itemView, IFavoriteViewHolderClick listener) {
        super(itemView);
        mListener = listener;
        mTextView = itemView.findViewById(R.id.favouriteNameTextView);
        mImageView = itemView.findViewById(R.id.deleteFavourite);
        mImageView.setOnClickListener(this);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        int itemPosition = this.getAdapterPosition();
        if(v instanceof ImageView)
            mListener.onDelete((ImageView)v, itemPosition);
        else
            mListener.onText(v, itemPosition);
    }


    public void bind(final Place place) {
        TextView textView = itemView.findViewById(R.id.favouriteNameTextView);
        textView.setText(place.getName());
        /*textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeoPoint recyclerGeoPoint = new GeoPoint(place.getLatitude(), place.getLongitude());
                favouriteSelectListener.selectLocation(recyclerGeoPoint);
            }
        });*/
        ImageView view = itemView.findViewById(R.id.deleteFavourite);
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askDelete(v.getContext(), place);

            }
        });*/
    }

    /*private void askDelete(Context c, final Place place) {
        new AlertDialog.Builder(c)
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        favouritesManager.deleteFavorite(place);
                        updateFavourites(favouritesManager.getFavourites());
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .show();
    }*/

    public void updateFavourites(List<Place> favourites) {
        this.favourites = favourites;
        //notifyDataSetChanged();
    }

    public GeoPoint getGeoPoint(final Place place){
        return new GeoPoint(place.getLatitude(), place.getLongitude());
    }





    public interface IFavoriteViewHolderClick {
        public void onDelete(ImageView imageView, int position);
        public void onText(View view, int position);
    }
}

