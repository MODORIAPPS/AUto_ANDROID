package com.modori.kwonkiseokee.AUto

import android.content.Context
import androidx.lifecycle.LiveData
import com.modori.kwonkiseokee.AUto.Tab1_frag.DevicePhotoDao
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO_OLD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoRepository(val context:Context) {

    lateinit var devicePhotoDao:DevicePhotoDao

    companion object{
        // For Singleton instantiation
        @Volatile private var instance:PhotoRepository? = null

        fun getInstance(context:Context) =
                instance ?: synchronized(this){
                    instance ?: PhotoRepository(context).also {
                        instance = it
                    }
                }
    }

    fun insertDevicePhotos(devicePhotoDTO: DevicePhotoDTO){
        CoroutineScope(Dispatchers.IO).launch {
            devicePhotoDao.insert(devicePhotoDTO)
        }
    }

    fun deleteDevicePhotosById(photoId:String){

    }

    fun getDevicePhotos(): LiveData<List<DevicePhotoDTO>> {
        return devicePhotoDao.getDevicePhotoList()
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