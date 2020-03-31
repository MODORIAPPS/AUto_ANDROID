package com.modori.kwonkiseokee.AUto.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DevicePhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(devicePhotoDTO: DevicePhoto)

    @Query("DELETE FROM device_photos WHERE photoId =:photoId")
    fun deletePhotoById(photoId: String)

    @Query("SELECT count(*) FROM device_photos")
    fun getPhotosCount(): Int

    @Query("SELECT * FROM device_photos")
    fun getDevicePhotoList(): LiveData<List<DevicePhoto>>
}