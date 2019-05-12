package com.teamnumberb.pureair.favourite;

import java.io.Serializable;
import java.util.ArrayList;

class FavouritesData implements Serializable {
    private ArrayList<Place> favourites;

    public ArrayList<Place> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<Place> favourites) {
        this.favourites = favourites;
    }
}
