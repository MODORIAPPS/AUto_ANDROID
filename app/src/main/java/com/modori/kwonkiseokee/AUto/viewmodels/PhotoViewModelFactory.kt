package com.modori.kwonkiseokee.AUto.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.modori.kwonkiseokee.AUto.data.PhotoRepository

class PhotoViewModelFactory(private val repository: PhotoRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PhotoViewModel::class.java)){
            return PhotoViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")
    }

}