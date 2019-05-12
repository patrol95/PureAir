package com.teamnumberb.pureair.favourite;

import android.content.Context;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddFavouriteManager {
    private static String fileName = "favourites";

    private Context context;
    private FavouritesData data = new FavouritesData();

    public AddFavouriteManager(Context c) {
        context = c;
        readData();
    }

    public ArrayList<Place> getFavourites() {
        return data.getFavourites();
    }

    public void addFavourite(Place place) {
        getFavourites().add(place);
        saveData();
    }

    private void readData() {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            data = (FavouritesData) is.readObject();
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
            os.writeObject(data);
            os.close();
            fos.close();
        } catch (Exception e) {
            System.err.println("We shouldn't get here!");
            e.printStackTrace();
        }
    }
}
