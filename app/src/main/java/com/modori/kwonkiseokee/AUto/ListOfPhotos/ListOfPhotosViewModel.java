package com.modori.kwonkiseokee.AUto.ListOfPhotos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;

public class ListOfPhotosViewModel extends ViewModel {
    private MutableLiveData<PhotoSearch> mutableLiveData;
    private ListOfPhotosRepository photosRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        photosRepository = ListOfPhotosRepository.getInstance();
    }

    public LiveData<PhotoSearch> getListofPhotos(String tag) {
        mutableLiveData = photosRepository.getListOfPhotos(tag);
        return mutableLiveData;
    }
}
