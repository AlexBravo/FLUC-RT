package com.alexbravo.fluc_rt;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class RottenTomatoesActivity extends ActionBarActivity 
        implements MovieListFragment.OnItemSelectedListener, DetailFragment.MovieProvider {
    private static final String TAG = RottenTomatoesActivity.class.getName();
    private Movie selected_movie;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotten_tomatoes);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new MovieListFragment())
                    .commit();
        }
        selected_movie = null;
    }

    @Override
    public void onItemSelected(Movie movie) {
        // Switch to DetailFragment
        selected_movie = movie;
        DetailFragment detailFragment = new DetailFragment();
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("detailFragment")
                .replace(R.id.fragment_container, detailFragment, "detailFragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    // Return info about currently selected movie
    @Override
    public Movie getMovie() {
        return selected_movie;
    }
}
