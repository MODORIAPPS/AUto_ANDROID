package com.modori.kwonkiseokee.AUto

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.modori.kwonkiseokee.AUto.data.AppDatabase
import com.modori.kwonkiseokee.AUto.data.AutoSettingsRepository
import com.modori.kwonkiseokee.AUto.data.PhotoRepository
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModel
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModelFactory
import com.modori.kwonkiseokee.AUto.viewmodels.PhotoViewModel
import com.modori.kwonkiseokee.AUto.viewmodels.PhotoViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 임시 스플래쉬 띄우는 부분

        // SetUp ViewModel
        Log.d("MainActivity 객체 : ", this.applicationContext.toString())
        val dao = AppDatabase.getInstance(this.applicationContext).devicePhotoDao
        val repo = PhotoRepository(dao)
        val factory = PhotoViewModelFactory(repo)
        val viewModel = ViewModelProvider(viewModelStore,factory).get(PhotoViewModel::class.java)

        val dao2 = AppDatabase.getInstance(this.applicationContext).autoSettingsDao
        val repo2 = AutoSettingsRepository(dao2)
        val factory2 = AutoSettingsViewModelFactory(repo2)
        val viewModel2 = ViewModelProvider(viewModelStore,factory2).get(AutoSettingsViewModel::class.java)

        isPermissionGranted()

        supportFragmentManager.beginTransaction().replace(R.id.mainFrame, AlbumFragment()).commit()
        navBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.goTranslate -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrame, AlbumFragment()).commit()
                    true
                }

                R.id.goPhotos -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrame, ExploreFragment()).commit()
                    true
                }

                R.id.goAutoSet -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrame, AutoFragmentK()).commit()
                    true
                }

                else -> false
            }
        }

    }

    private fun isPermissionGranted() {
        val check = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (check == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)
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