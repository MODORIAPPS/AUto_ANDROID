package com.modori.kwonkiseokee.AUto

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO_OLD

class PhotoViewModel(val context: Context, var repository: PhotoRepository) : ViewModel() {

    private var devicePhotos: LiveData<List<DevicePhotoDTO>>
    private lateinit var downloadedPhotos: MutableLiveData<List<DevicePhotoDTO>>

    init {
        repository = PhotoRepository(context)
        devicePhotos = repository.getDevicePhotos()
    }


    // 갤러리에서 지정한 사진 가져옴
    fun getDevicePhotos(): LiveData<List<DevicePhotoDTO>> {
        return devicePhotos
    }

    // 이 앱으로 다운로드 받은 사진 가져옴
//    fun getDownloadedPhotos(): LiveData<List<DevicePhotoDTO>> {
//        val repo = PhotoRepository()
//        try {
//            downloadedPhotos = repo.getDownloadedPhotosOld()
//
//        } catch (error: Exception) {
//            throw Exception(error)
//        }
//        Log.d("PhotoRepository 결과물",downloadedPhotos.value.toString())
//
//        return downloadedPhotos
//    }

    override fun onCleared() {
        super.onCleared()
    }
}