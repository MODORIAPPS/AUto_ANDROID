package com.modori.kwonkiseokee.AUto;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.modori.kwonkiseokee.AUto.RetrofitService.RetrofitService;
import com.modori.kwonkiseokee.AUto.RetrofitService.api.SearchApi;
import com.modori.kwonkiseokee.AUto.data.api.PhotoSearch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfPhotosRepository {

    private static ListOfPhotosRepository photosRepository;
    private SearchApi searchApi;
    int photoCnt = 1;

    public static ListOfPhotosRepository getInstance() {
        if (photosRepository == null) {
            photosRepository = new ListOfPhotosRepository();
        }
        return photosRepository;
    }

    public ListOfPhotosRepository() {
        searchApi = RetrofitService.createService(SearchApi.class);
    }


    public MutableLiveData<PhotoSearch> getListOfPhotos(String tag) {


        MutableLiveData<PhotoSearch> photoData = new MutableLiveData<>();
        searchApi.getPhotobyKeyward(tag, photoCnt, 20).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    photoData.setValue(response.body());
                    photoCnt++;

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                //photoData.setValue(null);
                Log.d("사진 검색 오류", t.getMessage());


            }
        });

        return photoData;
    }


}
