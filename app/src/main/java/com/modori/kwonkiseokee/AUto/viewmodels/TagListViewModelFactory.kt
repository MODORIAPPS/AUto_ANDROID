package com.modori.kwonkiseokee.AUto.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.modori.kwonkiseokee.AUto.data.TagListRepository

class TagListViewModelFactory(private val repository: TagListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TagListViewModel::class.java)) {
            return TagListViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")
    }

}