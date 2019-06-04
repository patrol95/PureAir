package com.teamnumberb.pureair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamnumberb.pureair.favourite.AddFavouriteActivity;
import com.teamnumberb.pureair.favourite.FavouritesAdapter;
import com.teamnumberb.pureair.favourite.FavouritesManager;

import org.osmdroid.util.GeoPoint;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private FavouritesManager favouritesManager;
    private FavouritesAdapter favouritesAdapter = new FavouritesAdapter();
    private IFavouriteSelectListener mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favouritesManager = new FavouritesManager(getContext());
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddFavouriteActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        final RecyclerView recyclerView = view.findViewById(R.id.favorites_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(favouritesAdapter);
        favouritesAdapter.updateFavourites(favouritesManager.getFavourites());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        favouritesAdapter.updateFavourites(favouritesManager.getFavourites());
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFavouriteSelectListener) {
            mCallback = (IFavouriteSelectListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FavouriteSelectListener");
        }
    }

    public interface IFavouriteSelectListener{
        void selectLocation(GeoPoint geoPoint); //todo connect with rebuilt adapter
    }



    /*public void setFavouriteGeoPoint(){
        mCallback.selectLocation();
    }*/
}