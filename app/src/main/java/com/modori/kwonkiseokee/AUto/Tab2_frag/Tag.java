package com.modori.kwonkiseokee.AUto.Tab2_frag;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;

@Entity(tableName = "tag_table")
public class Tag {

    @NonNull
    @ColumnInfo(name = "tag")
    private String tag;


    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int tagId;

    public Tag(String tag, int tagId) {
        this.tag = tag;
        this.tagId = tagId;

        System.out.println(tag + " | " + tagId);
    }

    public String getTag() {
        return this.tag;
    }

    public int getTagId(){
        return this.tagId;
    }
}
