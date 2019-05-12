package com.teamnumberb.pureair.favourite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavoriteViewHolder> {
    private List<Place> favourites = new ArrayList<>();

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView textView = new TextView(viewGroup.getContext());
        return new FavoriteViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder favoriteViewHolder, int i) {
        favoriteViewHolder.bind(favourites.get(i));
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public void updateFavourites(List<Place> favourites) {
        this.favourites = favourites;
        notifyDataSetChanged();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Place place) {
            ((TextView) itemView).setText(place.getName());
        }
    }
}
