package com.modori.kwonkiseokee.AUto.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.modori.kwonkiseokee.AUto.data.api.PhotoSearchID

class PhotoDetailViewModel  : ViewModel(){
    private val _results = MutableLiveData<PhotoSearchID>()


    fun setResults(data: PhotoSearchID) {
        Log.d("ActivityModel", "Bitmap이 ViewModel에 저장됨")
        _results.value = data
    }


    fun getResults(): MutableLiveData<PhotoSearchID> {
        Log.d("ActivityModel", "ViewModel 이 Bitmap을 반환함")
        return _results
    }


    override fun onCleared() {
        super.onCleared()
    }
}