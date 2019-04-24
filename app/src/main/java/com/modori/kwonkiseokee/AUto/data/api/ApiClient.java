package com.modori.kwonkiseokee.AUto.data.api;

import com.modori.kwonkiseokee.AUto.RetrofitService.RetrofitAPI;

public class ApiClient {
    private static final String BASE_URL = "https://api.unsplash.com/";

    public static SearchApi getPhotoByKeyword(){
        return RetrofitAPI.getRetrofit(BASE_URL).create(SearchApi.class);
    }
}
