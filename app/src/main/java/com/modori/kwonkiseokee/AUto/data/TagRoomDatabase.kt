package com.modori.kwonkiseokee.AUto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.modori.kwonkiseokee.AUto.utilities.TAG_DATABASE_NAME

@Database(entities = [Tag::class], version = 2)
abstract class TagRoomDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: TagRoomDatabase? = null

        fun getInstance(context: Context): TagRoomDatabase {
            return instance ?: synchronized(this) {
                instance
                        ?: Room.databaseBuilder(context, TagRoomDatabase::class.java, TAG_DATABASE_NAME).build().also {
                            instance = it
                        }
            }
        }
    }


}