package com.modori.kwonkiseokee.AUto.data.api

import com.google.gson.annotations.SerializedName

data class ProfileImage(
        @SerializedName("small") val small: String,
        @SerializedName("medium") val medium: String,
        @SerializedName("large") val large: String )