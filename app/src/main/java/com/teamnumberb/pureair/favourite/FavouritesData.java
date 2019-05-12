package com.teamnumberb.pureair.favourite;

import java.io.Serializable;
import java.util.ArrayList;

class FavouritesData implements Serializable {
    private ArrayList<Place> favourites = new ArrayList<>();

    public ArrayList<Place> getFavourites() {
        return favourites;
    }
}
