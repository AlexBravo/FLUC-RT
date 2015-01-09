package com.alexbravo.fluc_rt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by alex on 1/9/15.
 */
public class DetailFragment extends Fragment {

    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvCast;
    private TextView tvAudienceScore;
    private TextView tvCriticsScore;
    private TextView tvSynopsis;

    public interface MovieProvider {
        public Movie getMovie();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,
                container, false);

        ivPosterImage = (ImageView) view.findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvCast = (TextView) view.findViewById(R.id.tvCast);
        tvSynopsis = (TextView) view.findViewById(R.id.tvSynopsis);
        tvAudienceScore =  (TextView) view.findViewById(R.id.tvAudienceScore);
        tvCriticsScore = (TextView) view.findViewById(R.id.tvCriticsScore);

        if (getActivity() instanceof MovieProvider) {
            Movie movie = ((MovieProvider) getActivity()).getMovie();
            if (movie != null) {
                update (movie);
            }
        }
        return view;
    }

    public void update(Movie movie) {
        // Populate data
        tvTitle.setText(movie.title);
        tvCriticsScore.setText(Html.fromHtml("<b>Critics Score:</b> " + movie.ratings.critics_score));
        tvAudienceScore.setText(Html.fromHtml("<b>Audience Score:</b> " + movie.ratings.audience_score));
        String cast = "";
        for (int i = 0; i < movie.cast.size(); i++) {
            cast += movie.cast.get(i).name;
            if (i < movie.cast.size() - 1 ) {
                cast += ", ";
            }
        }
        tvCast.setText(Html.fromHtml("<b>Cast:</b> " + cast));
        tvSynopsis.setText(Html.fromHtml("<b>Synopsis:</b> " + movie.synopsis));
        Picasso.with(getActivity()).
                load(movie.posters.detailed).
                into(ivPosterImage);
    }
}
