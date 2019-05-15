package com.teamnumberb.pureair.favourite;

import java.io.Serializable;
import java.util.ArrayList;

class FavouritesData implements Serializable {
    private ArrayList<Place> favourites;

    public FavouritesData(ArrayList<Place> favourites) {
        this.favourites = favourites;
    }

    public ArrayList<Place> getFavourites() {
        return favourites;
    }
}
