package com.modori.kwonkiseokee.AUto.data.api;


import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface SearchApi {
    // https://api.unsplash.com/search/
    // photos/?client_id=YOUR_KEY
    // &page=1&query=office 또는 &query=office

    String auth_key = "790a2bb347b11e1167cad7a85d7c01c2fc3ccd781b4265a5f7fabae767ed0b38";


    @GET("search/photos?client_id=" + auth_key+"&per_page=10")
    public Call<PhotoSearch> getPhotobyKeyward(@Query("query") String key);


}
