package com.teamnumberb.pureair.favourite;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamnumberb.pureair.DirectionsFragment;
import com.teamnumberb.pureair.HomeFragment;
import com.teamnumberb.pureair.R;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {
    private List<Place> favourites = new ArrayList<>();
    FavouritesManager favouritesManager;

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favourite, viewGroup, false);
        favouritesManager = new FavouritesManager(viewGroup.getContext());
        final FavoriteViewHolder favoriteViewHolder = new FavoriteViewHolder(view, new FavoriteViewHolder.FavouriteClick() {
            @Override
            public void onDelete(ImageView imageView) {
                askDelete(imageView.getContext(), favourites.get());
            }

            @Override
            public void onCard(View view) {

            }
        });
        return favoriteViewHolder;
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

    private void askDelete(Context c, final Place place) {
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
    }
}
