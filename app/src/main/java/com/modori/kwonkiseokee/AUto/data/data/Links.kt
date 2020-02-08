package com.modori.kwonkiseokee.AUto.data.data

import com.google.gson.annotations.SerializedName

data class Links(
        @SerializedName("self") val self: String,
        @SerializedName("html") val html: String,
        @SerializedName("download") val download: String,
        @SerializedName("download_location")val download_location: String)