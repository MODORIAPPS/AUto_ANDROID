package com.modori.kwonkiseokee.AUto.Tab1_frag

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO_OLD
import com.modori.kwonkiseokee.AUto.utilities.DATABASE_NAME

@Database(entities = [DevicePhotoDTO_OLD::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }

}
