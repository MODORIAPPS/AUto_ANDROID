package com.modori.kwonkiseokee.AUto.data.data;

import com.google.gson.annotations.SerializedName;

public class Exif {
    @SerializedName("make")
    private String make;

    @SerializedName("model")
    private String model;

    @SerializedName("exposure_time")
    private String exposure_time;

    @SerializedName("iso")
    private String iso;
}
