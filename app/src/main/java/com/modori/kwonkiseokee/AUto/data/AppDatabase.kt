package com.modori.kwonkiseokee.AUto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.modori.kwonkiseokee.AUto.utilities.APP_DATABASE_NAME

@Database(entities = [DevicePhoto::class, Tag::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val tagDao : TagDao
    abstract val devicePhotoDao : DevicePhotoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        public fun getInstance(context: Context): AppDatabase {
            return instance
                    ?: synchronized(this) {
                        instance
                                ?: buildDatabase(context).also { instance = it }
                    }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME).build()
        }
    }

}
