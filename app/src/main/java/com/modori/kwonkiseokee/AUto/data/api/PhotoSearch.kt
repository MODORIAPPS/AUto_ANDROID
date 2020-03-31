package com.modori.kwonkiseokee.AUto.data.api

import com.google.gson.annotations.SerializedName

data class PhotoSearch(
        @SerializedName("total") val total: String,
        @SerializedName("total_pages") val total_pages: String,
        @SerializedName("results") val results: List<Results> )