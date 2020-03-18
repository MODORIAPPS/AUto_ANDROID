package com.modori.kwonkiseokee.AUto

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO
import io.realm.RealmResults

class PhotoViewModel : ViewModel() {
    private lateinit var devicePhotos: MutableLiveData<RealmResults<DevicePhotoDTO>>
    private lateinit var downloadedPhotos: MutableLiveData<List<DevicePhotoDTO>>



//    // 갤러리에서 지정한 사진 가져옴
//    fun getDevicePhotos():  LiveData<RealmResults<DevicePhotoDTO>> {
//        val repo = PhotoRepository()
//        devicePhotos = repo.getDevicePhotos()
//        return devicePhotos
//    }

    // 이 앱으로 다운로드 받은 사진 가져옴
    fun getDownloadedPhotos(): LiveData<List<DevicePhotoDTO>> {
        val repo = PhotoRepository()
        try {
            downloadedPhotos = repo.getDownloadedPhotosOld()

        } catch (error: Exception) {
            throw Exception(error)
        }
        Log.d("PhotoRepository 결과물",downloadedPhotos.value.toString())

        return downloadedPhotos
    }

    override fun onCleared() {
        super.onCleared()
    }
}