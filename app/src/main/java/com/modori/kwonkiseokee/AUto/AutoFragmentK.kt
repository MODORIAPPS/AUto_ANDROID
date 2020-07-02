package com.modori.kwonkiseokee.AUto

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.modori.kwonkiseokee.AUto.utilities.InjectorUtils
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModel
import com.modori.kwonkiseokee.AUto.viewmodels.PhotoViewModel
import kotlinx.android.synthetic.main.tab3_frag.view.*

class AutoFragmentK : Fragment(), View.OnClickListener{

    private val photoViewModel: PhotoViewModel by viewModels {
        InjectorUtils.provideDevicePhotoViewModelFactory(requireContext())
    }
    private val autoSettingsViewModel: AutoSettingsViewModel by viewModels {
        InjectorUtils.provideAutoSettingsViewModelFactory(requireContext())
    }

    //var isWorking = autoSettingsViewModel.isWorking
    //var periodType = autoSettingsViewModel.periodType
    //var sourceType = autoSettingsViewModel.sourceType

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab3_frag, container, false)

        photoViewModel.devicePhotos.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(photoViewModel.devicePhotos.value != null){

            }
        })

        autoSettingsViewModel.isWorking.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        autoSettingsViewModel.periodType.observe(viewLifecycleOwner, Observer {

        })

        autoSettingsViewModel.sourceType.observe(viewLifecycleOwner, Observer {

        })

        view.goInfo.setOnClickListener(this)
        view.showDirBtn.setOnClickListener(this)
        view.actSwitch.setOnClickListener(this)
        view.shuffleSwitch.setOnClickListener(this)

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
                startActivity(Intent(activity, ManageImageSourceActivity::class.java))
            }

            // ActSwitch
            R.id.actSwitch -> {

            }

            R.id.shuffleSwitch -> {

            }
        }
    }
}