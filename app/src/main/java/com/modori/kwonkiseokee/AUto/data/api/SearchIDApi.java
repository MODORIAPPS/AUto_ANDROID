package com.modori.kwonkiseokee.AUto.data.api;


import com.modori.kwonkiseokee.AUto.data.data.PhotoSearchID;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;


public interface SearchIDApi {

    @Headers(
            {"Authorization: Client-ID 790a2bb347b11e1167cad7a85d7c01c2fc3ccd781b4265a5f7fabae767ed0b38"}
    )


    // https://api.unsplash.com/photos/?client_id=YOUR_KEY&id=Dwu85P9SOIk

    @GET("photos/{id}/")
    public Call<PhotoSearchID> getPhotoByID(@Path("id") String photoID);

}
