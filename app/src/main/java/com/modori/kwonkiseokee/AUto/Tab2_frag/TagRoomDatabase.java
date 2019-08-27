package com.modori.kwonkiseokee.AUto.Tab2_frag;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Tag.class}, version = 1)
public abstract class TagRoomDatabase extends RoomDatabase {

    public abstract TagDao tagDao();

    private static volatile TagRoomDatabase INSTANCE;

    static TagRoomDatabase getDatabase(final Context context) {
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

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    public static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TagDao mDao;

        PopulateDbAsync(TagRoomDatabase db) {
            mDao = db.tagDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll();
            String[] tags = new String[]{"Landscape", "Office","MilkyWay","Yosemite","Roads", "Home"};
            Tag tag;
            for (int i = 0; i < 6; i++) {
                 tag = new Tag(tags[i],i);
                 mDao.insert(tag);
            }

            return null;
        }
    }



}
