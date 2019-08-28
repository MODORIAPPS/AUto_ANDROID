package com.modori.kwonkiseokee.AUto.DevicePhotos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO;

import java.util.List;

@Dao
public interface DevicePhotoDao {
    @Insert()
    void insert(DevicePhotoDTO devicePhoto);

    @Delete()
    void deletePhotobyId(String photoId);

    @Query("SELECT count(*) FROM tag_table")
    int getPhotosCount();

    @Query("SELECT * from device_photos")
    LiveData<List<DevicePhotoDTO>> getPhotoLists();
}
