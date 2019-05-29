package com.teamnumberb.pureair.favourite;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
    public FavouriteClick mListener;
    public TextView mTextView;
    public ImageView mImageView;

    public FavoriteViewHolder(@NonNull View itemView, FavouriteClick listener) {
        super(itemView);
        mListener = listener;
        mTextView = itemView.findViewById(R.id.favouriteNameTextView);
        mImageView = itemView.findViewById(R.id.deleteFavourite);
        mImageView.setOnClickListener(this);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(v instanceof ImageView)
            mListener.onDelete((ImageView)v);
        else
            mListener.onCard(v);
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

    public GeoPoint getGeoPoint(final Place place){
        return new GeoPoint(place.getLatitude(), place.getLongitude());
    }



    public interface FavouriteClick{
        public void onDelete(ImageView imageView);
        public void onCard(View view);
    }
}

