package com.modori.kwonkiseokee.AUto

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.modori.kwonkiseokee.AUto.data.AppDatabase
import com.modori.kwonkiseokee.AUto.data.AutoSettingsRepository
import com.modori.kwonkiseokee.AUto.data.PhotoRepository
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModel
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModelFactory
import com.modori.kwonkiseokee.AUto.viewmodels.PhotoViewModel
import com.modori.kwonkiseokee.AUto.viewmodels.PhotoViewModelFactory

class AutoFragmentK : Fragment(), View.OnClickListener{

    lateinit var photoViewModel: PhotoViewModel
    lateinit var autoSettingsViewModel: AutoSettingsViewModel

    //var isWorking = autoSettingsViewModel.isWorking
    //var periodType = autoSettingsViewModel.periodType
    //var sourceType = autoSettingsViewModel.sourceType

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab3_frag, container, false)

        // SetUp ViewModel
        val dao = AppDatabase.getInstance(requireContext()).devicePhotoDao
        val repo = PhotoRepository(dao)
        val factory = PhotoViewModelFactory(repo)
        photoViewModel = ViewModelProvider(viewModelStore, factory).get(PhotoViewModel::class.java)

        val dao2 = AppDatabase.getInstance(requireContext()).autoSettingsDao
        val repo2 = AutoSettingsRepository(dao2)
        val factory2 = AutoSettingsViewModelFactory(repo2)
        autoSettingsViewModel = ViewModelProvider(viewModelStore, factory2).get(AutoSettingsViewModel::class.java)

        autoSettingsViewModel.isWorking.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        autoSettingsViewModel.periodType.observe(viewLifecycleOwner, Observer {

        })

        autoSettingsViewModel.sourceType.observe(viewLifecycleOwner, Observer {

        })

        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            // Navigate to LicenseActivity which shows this app's license info.
            R.id.goInfo -> {
                startActivity(Intent(activity, LicenseActivity::class.java))
            }

            // Navigate to SetGetImagesDir_layout which determine resource of images.
            R.id.showDirBtn -> {
                startActivity(Intent(activity, SetGetImagesDir_layout::class.java))
            }

            // ActSwitch
            R.id.actSwitch -> {

            }

            R.id.shuffleSwitch -> {

            }
        }
    }
}