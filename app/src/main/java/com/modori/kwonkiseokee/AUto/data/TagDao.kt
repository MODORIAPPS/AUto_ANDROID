package com.modori.kwonkiseokee.AUto.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TagDao {
    @Insert()
    fun insert(tag:Tag)

    @Query("UPDATE tag_table SET tag=:newTag WHERE id=:targetId")
    fun update(newTag:String, targetId:Int)

    @Query("DELETE FROM tag_table")
    fun deleteAll()

    @Query("SELECT * FROM tag_table")
    fun getTagLists():LiveData<List<Tag>>

    @Query("SELECT count(*) FROM tag_table")
    fun getSize() : Int
}