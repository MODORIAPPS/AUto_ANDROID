package com.modori.kwonkiseokee.AUto.data.data

import com.google.gson.annotations.SerializedName

data class PhotoSearchID(
        @SerializedName("id") val id: String,
        @SerializedName("created_at") val created_at: String,
        @SerializedName("update_at") val update_at: String,
        @SerializedName("width")val width: Int,
        @SerializedName("height")val height: Int,
        @SerializedName("color")val color: String,
        @SerializedName("downloads")val downloads: Int,
        @SerializedName("likes")val likes: Int,
        @SerializedName("description")val description: String,
        @SerializedName("exif")val exif: Exif,
        @SerializedName("urls")val urls: Urls,
        @SerializedName("links")val links: Links,
        @SerializedName("user") val user: User)