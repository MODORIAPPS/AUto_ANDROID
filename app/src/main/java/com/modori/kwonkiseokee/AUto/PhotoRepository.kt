package com.modori.kwonkiseokee.AUto

import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.modori.kwonkiseokee.AUto.Tab1_frag.LiveRealmData
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import java.io.File

class PhotoRepository {
    val realm: Realm = Realm.getDefaultInstance()
    private fun <T : RealmModel> RealmResults<T>.asLiveData() = LiveRealmData(this)
    lateinit var mutableFiles: MutableLiveData<List<DevicePhotoDTO>>

    // android 10
//    fun getDownloadedPhotos():LiveData<List<DevicePhotoDTO>>{
//        // android 10 ->
//        //val rootSD = this.gete.toString()
//    }

    // Under android 10
    fun getDownloadedPhotosOld(): MutableLiveData<List<DevicePhotoDTO>> {
        //val okFileExtensions = arrayOf("jpeg","jpg","png","gif")

        try {
            val rootSD = File(Environment.getExternalStorageDirectory().absolutePath + "/AUtoImages/")
            //if (!rootSD.exists()) rootSD.createNewFile()
            var downloadLists: MutableList<DevicePhotoDTO> = arrayListOf()
            val fileLists = rootSD.listFiles()

            if (fileLists != null) {
                for (item in fileLists) {
                    if (item.path.endsWith(".jpeg") || item.path.endsWith(".jpg") || item.path.endsWith(".png") || item.path.endsWith(".gif")) {
                        downloadLists.add(DevicePhotoDTO("test", "${Uri.fromFile(item.absoluteFile)}"))

                    }
                }

                mutableFiles.value = downloadLists
                Log.d("PhotoRepository", downloadLists.toString())

                return mutableFiles

            } else {
                throw Exception("fileLists is Null")
            }

        } catch (e: Error) {
            throw Exception(e)
        }


    }

//    fun getDevicePhotos(): MutableLiveData<RealmResults<DevicePhotoDTO>> {
//
//        return realm.where(DevicePhotoDTO::class.java).findAllAsync().asLiveData()
//
//    }


}