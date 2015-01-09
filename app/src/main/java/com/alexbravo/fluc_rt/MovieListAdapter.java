package com.alexbravo.fluc_rt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.squareup.picasso.Picasso;
/**
 * Created by alex on 1/9/15.
 */
public class MovieListAdapter extends ArrayAdapter<Movie> {

    public ArrayList<Movie> listOfMovies;
    public final Context context;

    public MovieListAdapter(Context context , ArrayList<Movie> objects) {
        super(context, 0 , objects);
        this.listOfMovies = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;

        // Get the data item for this position
        Movie movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {

            view = LayoutInflater.from(getContext()).inflate( R.layout.movie_list_item, parent , false );

            viewHolder = new ViewHolder( view );

            view.setTag( viewHolder );
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.title.setText(movie.title);
        String cast = "Cast: ";
        for (int i = 0; i < movie.cast.size(); i++) {
            cast += movie.cast.get(i).name;
            if (i < movie.cast.size() - 1 ) {
                cast += ", ";
            }
        }
        viewHolder.cast.setText(cast);
        viewHolder.criticScore.setText("Score: " +String.valueOf(movie.ratings.critics_score) );

        Picasso.with(getContext()).load(movie.posters.thumbnail).into(viewHolder.posterImage);

        return  view;
    }

    protected static class ViewHolder
    {
        @InjectView( R.id.titleText )
        TextView title;

        @InjectView( R.id.cast )
        TextView cast;

        @InjectView( R.id.criticsScore  )
        TextView criticScore;

        @InjectView( R.id.posterImage )
        ImageView posterImage;

        public ViewHolder ( View view )
        {
            ButterKnife.inject(this, view);
        }
    }
}
