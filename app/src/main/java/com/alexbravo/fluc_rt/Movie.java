package com.alexbravo.fluc_rt;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by alex on 1/9/15.
 */
public class Movie {
    public String id;
    public String title;
    public String synopsis;
    public Posters posters;

    @SerializedName("abridged_cast")
    public ArrayList<Actor> cast;

    public Ratings ratings;

    public class Actor {
        public String name;
    }

    public class Ratings {
        public int critics_score;
        public int audience_score;
    }

    public class Posters {
        public String thumbnail;
        public String detailed;
    }

}
