package com.modori.kwonkiseokee.AUto.AutoSettings

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.modori.kwonkiseokee.AUto.R

class Fragment_SourceSettings(): Fragment(), View.OnClickListener {

    lateinit var viewModel: SettingsViewModel

    lateinit var nextBtnInSourceSettings:Button
    lateinit var setSourceAsDownloaded:LinearLayout
    lateinit var setSourceAsFolder:LinearLayout
    lateinit var setSourceAsGallery:LinearLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.auto_source_settings, container, false)

        nextBtnInSourceSettings = view.findViewById(R.id.nextBtnInSourceSettings)
        setSourceAsDownloaded = view.findViewById(R.id.setSourceAsDownloaded)
        setSourceAsFolder = view.findViewById(R.id.setSourceAsFolder)
        setSourceAsGallery = view.findViewById(R.id.setSourceAsGallery)

        // ViewModel Setup
        viewModel = ViewModelProviders.of(activity!!).get(SettingsViewModel::class.java)
        viewModel.getSourceType().observe(this,androidx.lifecycle.Observer {
            val sourceType = viewModel.getSourceType().value
            if(sourceType != null){
                Log.d("SourceType", sourceType.toString())
                nextBtnInSourceSettings.isEnabled = true

                when(sourceType){
                    SourceType.DOWNLOADED -> {
                        setSourceAsDownloaded.setBackgroundColor(PresentColor.DOWNLOADED_ENABLED.color)
                        setSourceAsFolder.setBackgroundColor(PresentColor.MY_FOLDER.color)
                        setSourceAsGallery.setBackgroundColor(PresentColor.GALLERY_PICK.color)
                    }

                    SourceType.MY_FOLDER -> {
                        setSourceAsDownloaded.setBackgroundColor(PresentColor.DOWNLOADED.color)
                        setSourceAsFolder.setBackgroundColor(PresentColor.MY_FOLDER_ENABLED.color)
                        setSourceAsGallery.setBackgroundColor(PresentColor.GALLERY_PICK.color)
                    }

                    SourceType.GALLERY_PICK -> {
                        setSourceAsDownloaded.setBackgroundColor(PresentColor.DOWNLOADED.color)
                        setSourceAsFolder.setBackgroundColor(PresentColor.MY_FOLDER.color)
                        setSourceAsGallery.setBackgroundColor(PresentColor.GALLERY_PICK_ENABLED.color)
                    }
                }
            }
        })


        //viewModel.setSourceType(SourceType.DOWNLOADED)

        setSourceAsDownloaded.setOnClickListener(this)
        setSourceAsFolder.setOnClickListener(this)
        setSourceAsGallery.setOnClickListener(this)



        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.setSourceAsDownloaded -> {
                viewModel.setSourceType(SourceType.DOWNLOADED)
            }

            R.id.setSourceAsFolder -> {
                viewModel.setSourceType(SourceType.MY_FOLDER)
            }

            R.id.setSourceAsGallery -> {
                viewModel.setSourceType(SourceType.GALLERY_PICK)
            }
            R.id.nextBtnInSourceSettings -> {
                if(!nextBtnInSourceSettings.isEnabled){
                    Toast.makeText(activity,"하나 이상의 방법을 선택하세요",Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.setCurrentPosition(3)
                }
            }
        }
    }



}