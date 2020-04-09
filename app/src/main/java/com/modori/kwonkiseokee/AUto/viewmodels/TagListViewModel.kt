package com.modori.kwonkiseokee.AUto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.modori.kwonkiseokee.AUto.data.Tag
import com.modori.kwonkiseokee.AUto.data.TagListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagListViewModel(private val repository: TagListRepository) : ViewModel() {

    private var _tagLists: LiveData<List<Tag>> = repository.tagList
    val tagLists: LiveData<List<Tag>>
        get() = _tagLists

    val tagAvailable = repository.isAvailable

    fun update(newTag: String, targetId: Int) = CoroutineScope(Dispatchers.IO).launch {
        repository.update(newTag, targetId)
    }

    fun resetTagList() = CoroutineScope(Dispatchers.IO).launch {
        repository.resetTagList()
    }

}