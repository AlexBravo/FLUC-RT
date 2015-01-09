package com.alexbravo.fluc_rt;

import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;


import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RottenTomatoesActivity extends ActionBarActivity {
    private static final String TAG = RottenTomatoesActivity.class.getName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotten_tomatoes);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieListFragment())
                    .commit();
        }
        ButterKnife.inject(this);
    }

    public static class MovieListFragment extends ListFragment {

        private RottenTomatoesRetrofitClient.RottenTomatoesService rottenTomatoesService;
        private RestAdapter restAdapter;


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            
            setUpRetroFit();
            rottenTomatoesService.getMoviesFromServer(new RottenTomatoesMoviesResponseHandler());
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i(TAG, "Item clicked: " + id);
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
}
