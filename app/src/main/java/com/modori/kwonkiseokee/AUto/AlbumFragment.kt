package com.modori.kwonkiseokee.AUto

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import io.realm.Realm
import kotlinx.android.synthetic.main.tab1_frag.*
import kotlinx.android.synthetic.main.tab1_frag.view.*

class AlbumFragment : Fragment() {

    private val MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = -1
    lateinit var viewModel:PhotoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tab1_frag, container, false)
        Realm.init(view.context)

        permissionCheck()

        // RecyclerView init
        val gridLayoutManager = GridLayoutManager(view.context, 3)
        val gridLayoutManager2 = GridLayoutManager(view.context, 3)
        view.pickedRV.layoutManager = gridLayoutManager
        view.downloadRV.layoutManager = gridLayoutManager2

        // Ads
//        // 광고
////        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
////        String ads_app = getResources().getString(R.string.ads_app);
////        MobileAds.initialize(mContext, ads_app);
////        adView = view.findViewById(R.id.adView_frag1);
////        adView.loadAd(adRequest);

        // Setup ViewModel
        // SetUp ViewModel
        val dao = AppDatabase.getInstance(activity!!.applicationContext).devicePhotoDao
        val repo = PhotoRepository(dao)
        val factory = PhotoViewModelFactory(repo)
        viewModel = ViewModelProvider(viewModelStore, factory).get(PhotoViewModel::class.java)


        // Gallery Picked Photos
        viewModel.devicePhotos.observe(this, androidx.lifecycle.Observer {
            if (viewModel.devicePhotos.value != null) {
                setPickedRV(viewModel.devicePhotos.value!!)

                //noPickedImagesWarning = true
                noPickedImagesWarning.visibility = View.GONE

            } else {
                // No images
                noPickedImagesWarning.visibility = View.VISIBLE
            }
        })

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

        return view

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

    fun permissionCheck() {
        val check = ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (check == PackageManager.PERMISSION_DENIED) requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE -> {

            }
        }
    }
}