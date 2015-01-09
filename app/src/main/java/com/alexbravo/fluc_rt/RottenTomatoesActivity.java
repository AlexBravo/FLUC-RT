package com.alexbravo.fluc_rt;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MovieListFragment extends Fragment {

        private RottenTomatoesRetrofitClient.RottenTomatoesService rottenTomatoesService;
        private RestAdapter restAdapter;

        @InjectView(R.id.movieListView)
        ListView movieListView;
        
        public MovieListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
            ButterKnife.inject(this, rootView);
            setUpRetroFit();
            rottenTomatoesService.getMoviesFromServer(new RottenTomatoesMoviesResponseHandler());
            return rootView;
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
                movieListView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                // TODO: Add error handling here
                Log.e(TAG, retrofitError.getMessage());
            }
        }
    }
}
