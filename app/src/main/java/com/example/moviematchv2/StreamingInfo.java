package com.example.moviematchv2;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StreamingInfo implements Serializable {

    @SerializedName("disney")
    Disney disney;

    @SerializedName("netflix")
    Netflix netflix;

    @SerializedName("prime")
    Prime prime;
}

class Disney {
    @SerializedName("ca")
    CA canada;
}

class Netflix {
    @SerializedName("ca")
    CA canada;
}

class Prime {
    @SerializedName("ca")
    CA canada;
}

class CA {
    @SerializedName("link")
    String link;
}
