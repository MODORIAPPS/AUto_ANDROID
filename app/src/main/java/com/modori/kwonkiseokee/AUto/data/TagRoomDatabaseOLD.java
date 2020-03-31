package com.modori.kwonkiseokee.AUto.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Tag.class}, version = 1)
public abstract class TagRoomDatabaseOLD extends RoomDatabase {

    public abstract TagDao tagDao();

    public static volatile TagRoomDatabase INSTANCE;

    public static TagRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TagRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TagRoomDatabase.class, "tag_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
