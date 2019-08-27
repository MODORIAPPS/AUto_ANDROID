package com.modori.kwonkiseokee.AUto.Tab2_frag;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import java.util.List;

public class TagListRepository {
    private TagDao tagDao;
    private LiveData<List<Tag>> tagLists;

    TagListRepository(Application application) {
        TagRoomDatabase db = TagRoomDatabase.getDatabase(application);
        tagDao = db.tagDao();
        tagLists = tagDao.getTagLists();
    }

    public void deleteAll() {
        tagDao.deleteAll();
    }

    public int getSize() {
        return tagDao.getSize();
    }

    LiveData<List<Tag>> getTagLists() {
        return tagLists;
    }

    public void update(String newTag, int targetId) {
        Tag tag = new Tag(newTag, targetId);
        new UpdateAsyncTask(tagDao).execute(tag);
    }

    public void insert(Tag word) {
        new insertAsyncTask(tagDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Tag, Void, Void> {

        private TagDao mAsyncTaskDao;

        insertAsyncTask(TagDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Tag... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Tag, Void, Void> {

        private TagDao mAsyncTaskDao;

        UpdateAsyncTask(TagDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Tag... params) {
            mAsyncTaskDao.update(params[0].getTag(), params[0].getTagId());
            return null;
        }
    }


}
