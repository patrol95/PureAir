package com.teamnumberb.pureair.favourite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamnumberb.pureair.R;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavoriteViewHolder> {
    private List<Place> favourites = new ArrayList<>();
    FavouritesManager favouritesManager;

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favourite, viewGroup, false);
        favouritesManager = new FavouritesManager(viewGroup.getContext());
        return new FavoriteViewHolder(view);
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

        public void bind(final Place place) {
            TextView textView = itemView.findViewById(R.id.favouriteNameTextView);
            textView.setText(place.getName());
            View view = itemView.findViewById(R.id.deleteFavourite);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favouritesManager.deleteFavorite(place);
                    updateFavourites(favouritesManager.getFavourites());
                }
            });
        }
    }
}
