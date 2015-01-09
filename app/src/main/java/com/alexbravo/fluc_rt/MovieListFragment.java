package com.alexbravo.fluc_rt;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alex on 1/9/15.
 */
public class MovieListFragment extends ListFragment {
    private static final String TAG = MovieListFragment.class.getName();
    private RottenTomatoesRetrofitClient.RottenTomatoesService rottenTomatoesService;
    private RestAdapter restAdapter;
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        public void onItemSelected(Movie movie);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpRetroFit();
        rottenTomatoesService.getMoviesFromServer(new RottenTomatoesMoviesResponseHandler());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MovieListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, "Item clicked: " + id);
        //Toast.makeText(getActivity(), "Item clicked: " + id, Toast.LENGTH_SHORT).show();
        Movie movie = (Movie)getListAdapter().getItem(position);
        listener.onItemSelected(movie);
    }

    protected void setUpRetroFit()
    {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(RottenTomatoesRetrofitClient.API_BASE_URL)
                .build();
        rottenTomatoesService = restAdapter.create(RottenTomatoesRetrofitClient.RottenTomatoesService.class);
    }

    public class RottenTomatoesMoviesResponseHandler implements Callback<Movies>
    {
        private ArrayList<Movie> movies = new ArrayList<Movie>();
        private MovieListAdapter adapter = new MovieListAdapter(getActivity().getApplicationContext(), movies);

        @Override
        public void success(Movies movies, Response response) {
            for(Movie movie : movies.moviesFromJSONToJavaList) {
                adapter.add(movie);
            }
            setListAdapter(adapter);
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            // TODO: Add error handling here
            Log.e(TAG, retrofitError.getMessage());
        }
    }
}
