package com.modori.kwonkiseokee.AUto.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modori.kwonkiseokee.AUto.data.TagListRepository
import kotlinx.coroutines.launch

class TagListViewModel(private val repository: TagListRepository) : ViewModel() {

    val tagLists = repository.tagList
    val tagAvailable = repository.isAvailable

    fun update(newTag:String, targetId:Int) = viewModelScope.launch {
        repository.update(newTag,targetId)
    }

    fun resetTagList() = viewModelScope.launch {
        repository.resetTagList()
    }

}