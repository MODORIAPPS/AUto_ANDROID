package com.modori.kwonkiseokee.AUto.Tab1_frag;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.modori.kwonkiseokee.AUto.Tab2_frag.Tag;

import java.util.List;

@Dao
public interface AlbumDao {
    @Insert()
    void insert(AlbumDTO albumDTO);

    @Query("SELECT * from album_table")
    LiveData<List<AlbumDTO>> getAlbumLists();

    @Query("SELECT count(*) FROM album_table")
    int getSize();
}
