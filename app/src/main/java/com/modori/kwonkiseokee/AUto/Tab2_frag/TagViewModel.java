package com.modori.kwonkiseokee.AUto.Tab2_frag;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TagViewModel extends AndroidViewModel {
    private TagListRepository repository;
    private LiveData<List<Tag>> tagLists;

    public TagViewModel(Application application) {
        super(application);
        repository = new TagListRepository(application);
        tagLists = repository.getTagLists();
    }

    public int getSize(){
        return repository.getSize();
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public void update(String newTag, int targetId){
        repository.update(newTag, targetId);
    }

    public LiveData<List<Tag>> getTagLists() {
        return tagLists;
    }

    public void insert(Tag word) {
        repository.insert(word);
    }
}
