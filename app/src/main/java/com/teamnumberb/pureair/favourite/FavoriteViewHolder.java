package com.teamnumberb.pureair.favourite;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamnumberb.pureair.R;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public IFavoriteViewHolderClick mListener;
    public TextView mTextView;
    public ImageView mImageView;
    public CardView mCardView;

    public FavoriteViewHolder(@NonNull View itemView, IFavoriteViewHolderClick listener) {
        super(itemView);
        mListener = listener;
        mTextView = itemView.findViewById(R.id.favouriteNameTextView);
        mImageView = itemView.findViewById(R.id.deleteFavourite);
        mCardView = itemView.findViewById(R.id.favourite_card);
        mImageView.setOnClickListener(this);
        mCardView.setOnClickListener(this);
        mTextView.setOnClickListener(this);
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
            }





    public interface IFavoriteViewHolderClick {
        void onDelete(ImageView imageView, int position);
        void onText(View view, int position);
    }
}

