package com.modori.kwonkiseokee.AUto

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.modori.kwonkiseokee.AUto.adapters.PickGalleryAdapter
import com.modori.kwonkiseokee.AUto.data.AppDatabase
import com.modori.kwonkiseokee.AUto.data.DevicePhoto
import com.modori.kwonkiseokee.AUto.data.DevicePhotoOLD
import com.modori.kwonkiseokee.AUto.data.PhotoRepository
import com.modori.kwonkiseokee.AUto.viewmodels.PhotoViewModel
import com.modori.kwonkiseokee.AUto.viewmodels.PhotoViewModelFactory
import kotlinx.android.synthetic.main.tab1_frag.*
import kotlinx.android.synthetic.main.tab1_frag.view.*
import kotlin.properties.Delegates

class AlbumFragment : Fragment() {

    private val MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 8980

    lateinit var photoViewModel: PhotoViewModel
    //private val autoSettingsViewModel:AutoSettingsViewModel by activityViewModels()

    lateinit var mView:View

    var permissionGranted: Boolean by Delegates.observable(false) { property, oldValue, newValue ->

        if (newValue) {
            startObserve()
            mView.noPickedImagesWarning.text = "이미지가 없습니다."
            mView.noDownloadImagesWarning.text = "이미지가 없습니다."
        } else {
            mView.noPickedImagesWarning.visibility = View.VISIBLE
            mView.noDownloadImagesWarning.visibility = View.VISIBLE

            mView.noPickedImagesWarning.text = "현재 사용할 수 없습니다."
            mView.noDownloadImagesWarning.text = "현재 사용할 수 없습니다."
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.tab1_frag, container, false)
        // Ads
//        // 광고
////        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
////        String ads_app = getResources().getString(R.string.ads_app);
////        MobileAds.initialize(mContext, ads_app);
////        adView = view.findViewById(R.id.adView_frag1);
////        adView.loadAd(adRequest);

        // SetUp ViewModel
        val dao = AppDatabase.getInstance(requireContext()).devicePhotoDao
        val repo = PhotoRepository(dao)
        val factory = PhotoViewModelFactory(repo)
        photoViewModel = ViewModelProvider(viewModelStore, factory).get(PhotoViewModel::class.java)

        // RecyclerView init
        val gridLayoutManager = GridLayoutManager(mView.context, 3)
        val gridLayoutManager2 = GridLayoutManager(mView.context, 3)
        mView.pickedRV.layoutManager = gridLayoutManager
        mView.downloadRV.layoutManager = gridLayoutManager2

        isPermissionGranted()

        return mView

    }

//    private fun setRecyclerView(set: Set<Int>) {
//        val adapter = ColorAdapter(set.toList(), this)
//        colorList = set.toIntArray()
//        colorSet = set as MutableSet<Int>
//        Log.d("색류", set.toString())
//        colorsRV.layoutManager = LinearLayoutManager(this)
//        colorsRV.adapter = adapter
//
//        utilToolBar.setBackgroundColor(colorList!![0])
//
//        YoYo.with(Techniques.FadeIn)
//                .duration(500)
//                .repeat(0)
//                .playOn(colorsRV)
//
//        adapter.notifyDataSetChanged()
//
//    }

    private fun startObserve() {

        // Observe photoList which user picked in gallery
        try {
            photoViewModel.devicePhotos.observe(this, androidx.lifecycle.Observer {
                if (photoViewModel.devicePhotos.value != null) {
                    setPickedRV(photoViewModel.devicePhotos.value!!)

                    //noPickedImagesWarning = true
                    noPickedImagesWarning.visibility = View.GONE

                } else {
                    // No images
                    noPickedImagesWarning.visibility = View.VISIBLE
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }


        // Observe photoList which user downloaded in app
        //        try {
//            viewModel.getDownloadedPhotos().observe(this, androidx.lifecycle.Observer {
//                Toast.makeText(context,"변경 감지!",Toast.LENGTH_SHORT).show()
//                if (viewModel.getDownloadedPhotos().value != null) {
//                    setDownloadRV(viewModel.getDownloadedPhotos().value!!)
//                } else {
//                    // No images
//                }
//
//
//            })
//        } catch (e: Exception) {
//            if (e.message == "fileLists is Null") {
//                Toast.makeText(context, "리스트가 널입니다", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun setDownloadRV(array: List<DevicePhotoOLD>) {
        val photoUriLists = arrayListOf<String>()
        for (item in array) {
            photoUriLists.add(item.photoUri_d)
        }

        val adapter = PickGalleryAdapter(context, photoUriLists, 2)
        downloadRV.adapter = adapter

        adapter.notifyDataSetChanged()
    }

    private fun setPickedRV(array: List<DevicePhoto>) {
        val photoUriLists = arrayListOf<String>()
        for (item in array) {
            photoUriLists.add(item.photoUri_d)
        }
        val adapter = PickGalleryAdapter(context, photoUriLists, 0)
        pickedRV.adapter = adapter

        adapter.notifyDataSetChanged()


    }


    override fun onDestroy() {
        super.onDestroy()
    }

    private fun isPermissionGranted() {
        val check = ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionGranted = if (check == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE -> {

            }
        }
    }
}