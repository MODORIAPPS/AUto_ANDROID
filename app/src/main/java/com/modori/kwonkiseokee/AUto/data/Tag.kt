package com.modori.kwonkiseokee.AUto.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag_table")
data class Tag(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val tagId: Int,

        @ColumnInfo(name = "tag")
        public val tag: String
)