package com.modori.kwonkiseokee.AUto

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.modori.kwonkiseokee.AUto.utilities.InjectorUtils
import com.modori.kwonkiseokee.AUto.viewmodels.AutoSettingsViewModel
import com.modori.kwonkiseokee.AUto.viewmodels.PhotoViewModel
import kotlinx.android.synthetic.main.manage_image_resource_activity.*

class ManageImageSourceActivity : AppCompatActivity(), View.OnClickListener {

    private val SELECT_FOLDER = 6541
    private val MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 2

    private val devicePhotosViewModel: PhotoViewModel by viewModels {
        InjectorUtils.provideDevicePhotoViewModelFactory(application)
    }

    private val autoSettingsViewModel: AutoSettingsViewModel by viewModels {
        InjectorUtils.provideAutoSettingsViewModelFactory(application)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.openFolderBtn -> {
                if (checkWritePermission()) {
                    try {
                        startActivityForResult(Intent(this, SelectDirectoryActivity::class.java), SELECT_FOLDER)
                    } catch (e: Error) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }

            R.id.goPickGallery -> {
                startActivity(Intent(this, PickGalleryActivity::class.java))
            }

            R.id.openAutoSettings -> {
                startActivity(Intent(this,AutoSettingsActivity::class.java))
            }

            R.id.home -> finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_image_resource_activity)

        openAutoSettings.setOnClickListener(this)
        setToDir.setOnClickListener(this)
        openFolderBtn.setOnClickListener(this)
        setToUserPick.setOnClickListener(this)
        goPickGallery.setOnClickListener(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun checkWritePermission(): Boolean {
        return if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Write Permission Has Already Granted
            Log.d("PhotoDetailView", "Write Permission granted")
            true
        } else {
            // Request Write Permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted
                } else {
                    // not granted
                }
            }
        }
    }
}