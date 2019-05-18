package com.modori.kwonkiseokee.AUto.data.data;

import com.google.gson.annotations.SerializedName;

public class ProfileImage {
    @SerializedName("small")
    String small;

    @SerializedName("medium")
    String medium;

    @SerializedName("large")
    String large;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }
}
