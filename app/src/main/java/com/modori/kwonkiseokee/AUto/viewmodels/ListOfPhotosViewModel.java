package com.modori.kwonkiseokee.AUto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.modori.kwonkiseokee.AUto.data.ListOfPhotosRepository;
import com.modori.kwonkiseokee.AUto.data.api.PhotoSearch;

public class ListOfPhotosViewModel extends ViewModel {
    private MutableLiveData<PhotoSearch> mutableLiveData;
    private ListOfPhotosRepository photosRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        photosRepository = ListOfPhotosRepository.getInstance();
    }

    public LiveData<PhotoSearch> getListOfPhotos(String tag) {
        mutableLiveData = photosRepository.getListOfPhotos(tag);
        return mutableLiveData;
    }
}
