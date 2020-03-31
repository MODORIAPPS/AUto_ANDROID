package com.modori.kwonkiseokee.AUto.RetrofitService.api;


import com.modori.kwonkiseokee.AUto.data.api.PhotoSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface SearchApi {
    // https://api.unsplash.com/search/
    // photos/?client_id=YOUR_KEY
    // &page=1&query=office 또는 &query=office

    @Headers(
            {"Authorization: Client-ID 790a2bb347b11e1167cad7a85d7c01c2fc3ccd781b4265a5f7fabae767ed0b38"}
    )

    @GET("search/photos")
    public Call<PhotoSearch> getPhotobyKeyward(@Query("query") String key,
                                               @Query("page") Integer page,
                                               @Query("per_page") Integer per_page

    );

//    @GET("search/photos")
//    Call<SearchPhotosResult> searchPhotos(@Query("query") String query,
//                                          @Query("page") Integer page,
//                                          @Query("per_page") Integer per_page,
//                                          @Query("collections") String collections,
//                                          @Query("orientation") String orientation);


}
