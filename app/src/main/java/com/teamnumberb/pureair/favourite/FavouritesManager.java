package com.teamnumberb.pureair.favourite;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FavouritesManager {
    private static String fileName = "favourites";

    private Context context;
    private ArrayList<Place> favourites = new ArrayList<>();

    public FavouritesManager(Context c) {
        context = c;
        readData();
    }

    public List<Place> getFavourites() {
        readData();
        return favourites;
    }

    public void addFavourite(Place place) {
        favourites.add(place);
        saveData();
    }

    private void readData() {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            FavouritesData data = (FavouritesData) is.readObject();
            favourites = data.getFavourites();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found, probably first time to use application.");
        } catch (Exception e) {
            System.err.println("We shouldn't get here!");
            e.printStackTrace();
        }
    }

    private void saveData() {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(new FavouritesData(favourites));
            os.close();
            fos.close();
        } catch (Exception e) {
            System.err.println("We shouldn't get here!");
            e.printStackTrace();
        }
    }
}
