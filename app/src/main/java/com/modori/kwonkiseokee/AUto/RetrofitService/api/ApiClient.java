package com.modori.kwonkiseokee.AUto.RetrofitService.api;

import com.modori.kwonkiseokee.AUto.RetrofitService.RetrofitService;

public class ApiClient {
    private static final String BASE_URL = "https://api.unsplash.com/";

    public static SearchApi getPhotoByKeyword() {
        return RetrofitService.createService((SearchApi.class));
    }

    public static SearchIDApi getPhotoById() {
        return RetrofitService.createService(SearchIDApi.class);
    }

    public static FileDownloadClient downloadClient(){
        return RetrofitService.createService(FileDownloadClient.class);
    }
}
