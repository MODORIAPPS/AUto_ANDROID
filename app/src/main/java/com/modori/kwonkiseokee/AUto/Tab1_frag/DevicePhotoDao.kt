package com.modori.kwonkiseokee.AUto.Tab1_frag

import androidx.lifecycle.LiveData
import androidx.room.*
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO

@Dao
interface DevicePhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(devicePhotoDTO: DevicePhotoDTO)
    @Delete()
    fun deletePhotoById(photoId:String)

    @Query("SELECT count(*) FROM device_photos")
    fun getPhotosCount():Int

    @Query("SELECT * FROM device_photos")
    fun getDevicePhotoList():LiveData<List<DevicePhotoDTO>>
}