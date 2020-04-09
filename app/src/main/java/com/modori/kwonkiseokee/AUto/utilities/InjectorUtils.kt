package com.modori.kwonkiseokee.AUto.utilities

import android.content.Context
import com.modori.kwonkiseokee.AUto.data.AppDatabase
import com.modori.kwonkiseokee.AUto.data.AutoSettingsRepository
import com.modori.kwonkiseokee.AUto.data.PhotoRepository
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModelFactory
import com.modori.kwonkiseokee.AUto.viewmodels.PhotoViewModelFactory

object InjectorUtils {

    private fun getPhotoRepository(context:Context) : PhotoRepository{
        return PhotoRepository.getInstance(AppDatabase.getInstance(context.applicationContext).devicePhotoDao)
    }

    private fun getAutoSettingsRepository(context: Context) : AutoSettingsRepository{
        return AutoSettingsRepository.getInstance(AppDatabase.getInstance(context.applicationContext).autoSettingsDao)
    }

    fun provideDevicePhotoViewModelFactory(
            context:Context
    ) : PhotoViewModelFactory{
        val repository = getPhotoRepository(context)
        return PhotoViewModelFactory(repository)
    }

    fun provideAutoSettingsViewModelFactory(
            context: Context
    ) : AutoSettingsViewModelFactory{
        val repository = getAutoSettingsRepository(context)
        return AutoSettingsViewModelFactory(repository)
    }


}