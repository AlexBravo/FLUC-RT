package com.alexbravo.fluc_rt;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by alex on 1/9/15.
 */
public class Movies {

    @SerializedName("movies")
    public ArrayList<Movie> moviesFromJSONToJavaList;
}
