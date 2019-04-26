package com.modori.kwonkiseokee.AUto.data.api;


import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface SearchApi {
    // https://api.unsplash.com/search/
    // photos/?client_id=YOUR_KEY
    // &page=1&query=office 또는 &query=office

    @Headers(
            {"Authorization: Client-ID YOUR_KEY"}
    )

    @GET("search/photos")
    public Call<PhotoSearch> getPhotobyKeyward(@Query("query") String key);


}
