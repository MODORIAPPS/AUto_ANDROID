package com.modori.kwonkiseokee.AUto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modori.kwonkiseokee.AUto.data.DevicePhoto
import com.modori.kwonkiseokee.AUto.data.PhotoRepository
import kotlinx.coroutines.launch

class PhotoViewModel(private val repository: PhotoRepository) : ViewModel() {

    // 갤러리에서 지정한 사진 가져옴
    private var _devicePhotos: LiveData<List<DevicePhoto>> = repository.devicePhotoList
    val devicePhotos: LiveData<List<DevicePhoto>>
        get() = _devicePhotos


    fun insertDevicePhoto(devicePhoto: DevicePhoto) = viewModelScope.launch {
        repository.insertDevicePhotos(devicePhoto)
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
}