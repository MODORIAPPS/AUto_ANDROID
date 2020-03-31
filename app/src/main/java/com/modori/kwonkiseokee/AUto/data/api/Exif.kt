package com.modori.kwonkiseokee.AUto.data.api

import com.google.gson.annotations.SerializedName

data class Exif(
        @SerializedName("make") val make: String,
        @SerializedName("model") val model: String,
        @SerializedName("exposure_time") val exposure_time: String,
        @SerializedName("iso") val iso: String)