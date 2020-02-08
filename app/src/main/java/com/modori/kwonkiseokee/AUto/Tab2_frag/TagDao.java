package com.modori.kwonkiseokee.AUto.Tab2_frag;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TagDao {
    @Insert()
    void insert(Tag tag);

    @Query("UPDATE tag_table SET tag=:newTag WHERE id=:targetId")
    void update(String newTag, int targetId);

    @Query("DELETE FROM tag_table")
    void deleteAll();

    @Query("SELECT * from tag_table")
    LiveData<List<Tag>> getTagLists();

    @Query("SELECT count(*) FROM tag_table")
    int getSize();
}
