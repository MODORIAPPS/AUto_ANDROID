package com.modori.kwonkiseokee.AUto.data

import androidx.lifecycle.LiveData

class PhotoRepository(private val dao: DevicePhotoDao) {

    val devicePhotoList:LiveData<List<DevicePhoto>> = dao.getDevicePhotoList()

    suspend fun insertDevicePhotos(devicePhoto: DevicePhoto) {
        dao.insert(devicePhoto)
    }

    suspend fun deleteDevicePhotosById(devicePhoto: DevicePhoto) {
        dao.deletePhotoById(devicePhoto.photoID_d)
    }

    // Android 10 Support
//    fun getDownloadedPhotos():LiveData<List<DevicePhotoDTO>>{
//        // android 10 ->
//        //val rootSD = this.gete.toString()
//    }

    // Under android 10
//    fun getDownloadedPhotosOld(): LiveData<List<DevicePhotoDTO>> {
//        //val okFileExtensions = arrayOf("jpeg","jpg","png","gif")
//
//        try {
//            val rootSD = File(Environment.getExternalStorageDirectory().absolutePath + "/AUtoImages/")
//            //if (!rootSD.exists()) rootSD.createNewFile()
//            var downloadLists: MutableList<DevicePhotoDTO> = arrayListOf()
//            val fileLists = rootSD.listFiles()
//
//            if (fileLists != null) {
//                for (item in fileLists) {
//                    if (item.path.endsWith(".jpeg") || item.path.endsWith(".jpg") || item.path.endsWith(".png") || item.path.endsWith(".gif")) {
//                        downloadLists.add(DevicePhotoDTO("test", "${Uri.fromFile(item.absoluteFile)}"))
//
//                    }
//                }
//
//                devicePhotoList.value = downloadLists
//                Log.d("PhotoRepository", downloadLists.toString())
//
//                return devicePhotoList
//
//            } else {
//                throw Exception("fileLists is Null")
//            }
//
//        } catch (e: Error) {
//            throw Exception(e)
//        }
//
//
//    }


}