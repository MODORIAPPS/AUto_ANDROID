package com.modori.kwonkiseokee.AUto

import android.Manifest
import android.app.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.modori.kwonkiseokee.AUto.RetrofitService.api.ApiClient
import com.modori.kwonkiseokee.AUto.Service.SetWallpaperJob
import com.modori.kwonkiseokee.AUto.data.api.PhotoSearchID
import com.modori.kwonkiseokee.AUto.utilities.*
import com.modori.kwonkiseokee.AUto.viewmodels.PhotoDetailViewModel
import kotlinx.android.synthetic.main.photo_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlin.math.ln
import kotlin.math.pow


class PhotoDetailActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var viewModel: PhotoDetailViewModel

    lateinit var photoID: String
    lateinit var fabOpen: Animation
    lateinit var fabClose: Animation
    lateinit var DOWNLOAD_TYPE: DownloadType
    lateinit var results: PhotoSearchID
    lateinit var mContext: Context

    var displayWidth: Int = 0
    var displayHeight: Int = 0
    var isFabOpen: Boolean = false
    var action: Boolean = false

    var ADS_COUNTER: Int = 1
    var CHANGE_TYPE: Int = 0
    var NOTIFICATION_ID = 0

    // Group Notification Support for 7.0+
    var notCnt = 1

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.detailImageView -> {
                if (results != null) {
                    val goPhotoOnly = Intent(this, PhotoOnlyActivity::class.java)
                    goPhotoOnly.putExtra("photoUrl", results.urls.regular)
                    startActivity(goPhotoOnly)
                }

            }

            R.id.actionFab1 -> {
                if (actionFab1.isClickable) anim()
            }

            // Download
            R.id.fab2 -> {
                fab2Action()
            }

            // Set As Wallpaper Directly
            R.id.fab3 -> {
                fab3Action()
            }

            // Set Clipboard
            R.id.copyColorBtn -> {
                setClipBoardLink(mContext, results.color)
            }

            R.id.goInfo -> {
                val builder = androidx.appcompat.app.AlertDialog.Builder(this)
                builder.setTitle(R.string.PhotoDetail_DialogTitle)
                builder.setMessage(R.string.PhotoDetail_DialogMessage)
                builder.setPositiveButton(R.string.PhotoDetail_DialogOk
                ) { dialog, which -> }

                builder.show()
            }

            R.id.goSetting -> {
                openDownloadTypeSettingsDialog()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_detail)

        mContext = this

        // Make Random Notification_ID for Stack Notification
        val random = java.util.Random()
        NOTIFICATION_ID = random.nextInt(100 - 1) + 1

        // Setup ViewModel
        viewModel = ViewModelProviders.of(this).get(PhotoDetailViewModel::class.java)
        viewModel.getResults().observe(this, androidx.lifecycle.Observer {
            if (viewModel.getResults().value != null) {
                //setRecyclerView(PaletteTool.getColorSet(viewModel.getBitmaps().value!!))
                //setImageView(viewModel.getBitmaps().value!!)
                bindingToView(results)
            }
        })
        // Set SkeletonLayout
        photoDetailMask.showSkeleton()

        // Setup SharedPreferences
        MakePreferences.getInstance().setSettings(this)

        // Setup NotificationManger And Create Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel("AUTO_SLIDE", "SLIDE_CHANNEL", NotificationManager.IMPORTANCE_LOW)
            notificationChannel.description = "CHANNEL FOR OREO SUPPORT"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(false)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            manager.createNotificationChannel(notificationChannel)
            notificationChannel.setSound(null, null)
            notificationChannel.enableLights(false)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(false)
        }

        // Get Display Info For Prevent OutOfMemoryException
        val display = windowManager.defaultDisplay
        displayWidth = display.width
        displayHeight = display.height

        // getId from intent
        try {
            val intent = intent
            photoID = intent!!.extras!!.getString("id")!!
            Log.d("PhotoDetailView", photoID)
        } catch (e: Error) {
            Toast.makeText(this, "죄송합니다. 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        // setUp FabBtn Animation
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)


        // Attach OnClickListeners
        copyColorBtn.setOnClickListener(this)
        actionFab1.setOnClickListener(this)
        fab2.setOnClickListener(this)
        fab3.setOnClickListener(this)
        goInfo.setOnClickListener(this)
        goSetting.setOnClickListener(this)
        detailImageView.setOnClickListener(this)
        goBackBtn.setOnClickListener { finish() }

        // Check NetworkType
        val networkType = NetWorkTool.getNetWorkType(this)
        if (networkType == 0) {
            // No Network
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.noNetworkErrorTitle)
            builder.setMessage(R.string.noNetworkErrorContent)
            builder.setPositiveButton(R.string.tab2_DialogOk) { _, _ ->
                finish()
            }

            builder.show()
        } else if (networkType == 2) {
            // Mobile Network
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.mobileNetworkWarningTitle)
            builder.setMessage(R.string.mobileNetworkWarningTitle)
            builder.setPositiveButton(R.string.tab2_DialogOk) { _, _ ->
            }
        }

        // Load DownloadType
        when (MakePreferences.getInstance().settings.getInt("DOWNLOAD_TYPE", 1)) {
            0 -> DOWNLOAD_TYPE = DownloadType.RAW
            1 -> DOWNLOAD_TYPE = DownloadType.FULL
            2 -> DOWNLOAD_TYPE = DownloadType.REGULAR
        }
        downloadTypeView.text = DOWNLOAD_TYPE.text

        // Load AdsCounter
        ADS_COUNTER = MakePreferences.getInstance().settings.getInt("ADS_COUNTER", 1)

        actionFab1.isClickable = false
        // setUpDialog()

        // Check if Write Permission has granted
        checkWritePermission()

        // Setup DownloadType SelectorView
        // 데이터 바인딩 해야함
        downloadTypeView.text = DOWNLOAD_TYPE.text

        // Request PhotoById From Unsplash API
        ApiClient.getPhotoById().getPhotoByID(photoID).enqueue(object : retrofit2.Callback<PhotoSearchID> {
            override fun onFailure(call: Call<PhotoSearchID>, t: Throwable) {
                Toast.makeText(applicationContext, "죄송합니다. 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                Log.e("PhotoDetailView", t.message.toString())
            }

            override fun onResponse(call: Call<PhotoSearchID>, response: Response<PhotoSearchID>) {
                if (response.isSuccessful) {

                    try {
                        results = response.body()!!

                        bindingToView(results)

                        actionFab1.isClickable = true

                        Log.d("PhotoDetailView", "SuccessFully Loaded")
                        Log.d("PhotoDetailView", "PhotoId : $photoID")

                        photoDetailMask.showOriginal()

                    } catch (e: Error) {
                        Toast.makeText(applicationContext, "오류가 발생했습니다.\n ${e.message.toString()}", Toast.LENGTH_SHORT).show()
                        Log.e("PhotoDetailView", e.message.toString())
                        finish()
                    }

                }
            }

        })


    }

    private fun openDownloadTypeSettingsDialog() {
        val items = arrayOf(DownloadType.RAW.text, DownloadType.FULL.text, DownloadType.REGULAR.text)

        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(getString(R.string.PhotoDetail_SelectQua))
        builder.setItems(items) { dialog, pos ->
            when (pos) {
                0 -> DOWNLOAD_TYPE = DownloadType.RAW
                1 -> DOWNLOAD_TYPE = DownloadType.FULL
                2 -> DOWNLOAD_TYPE = DownloadType.REGULAR
            }

            MakePreferences.getInstance().settings.edit().putInt("DOWNLOAD_TYPE", DOWNLOAD_TYPE.type).apply()
            downloadTypeView.text = DOWNLOAD_TYPE.text
        }

        builder.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    fun bindingToView(results: PhotoSearchID) {
        // Binding Into View
        heartCnt.text = results.likes.toString()
        downloadsCnt.text = results.downloads.toString()
        Glide.with(applicationContext).load(results.user.profile_image.medium).into(authorProfile)
        authorName.text = results.user.username

        photoDescription.text = results.description
        uploadedDateV.text = results.created_at
        photoColorV.text = results.color

        val color: Int = Color.parseColor(results.color)
        imagePColorV.setBackgroundColor(color)

        photoSizeV.text = "RAW : ${results.width} * ${results.height}"

        Glide.with(applicationContext).load(results.urls.regular).into(detailImageView)
    }


    private fun fab2Action() {
        anim()
        action = false

        // Check If The File Already Exist
        if (FileManager.alreadyDownloaded(getFilename())) {
            // Already Exist
            Toast.makeText(mContext, "이미 파일이 존재합니다.", Toast.LENGTH_SHORT).show()
        } else {

            progress_circular.visibility = View.VISIBLE

            // Calculate ImageSize And Show AlertDialog
            CoroutineScope(Dispatchers.IO).launch {
                val imageSize = getImageSize(true)

                withContext(Dispatchers.Main) {

                    progress_circular.visibility = View.GONE

                    val builder = AlertDialog.Builder(mContext)
                    builder.setTitle("사진 다운로드")
                    builder.setMessage("약 $imageSize 의 이미지를 다운로드 받습니다.")
                    builder.setPositiveButton("확인") { dialog, which -> downloadImage(false) }
                    builder.setNegativeButton("취소") { dialog, which -> dialog.dismiss() }
                    builder.show()
                }
            }
        }
    }

    private fun fab3Action() {

        anim()
        action = true

        val items = arrayOf(getString(R.string.PhotoDetail_DialogItem1), getString(R.string.PhotoDetail_DialogItem2), getString(R.string.PhotoDetail_DialogItem3))
        var CHANGE_TYPE = 0

        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(getString(R.string.PhotoDetail_DialogTitle2))
        builder.setItems(items) { dialog, pos ->
            when (pos) {
                0 -> {
                    CHANGE_TYPE = WallpaperManager.FLAG_LOCK
                }

                1 -> {
                    CHANGE_TYPE = WallpaperManager.FLAG_SYSTEM
                }

                2 -> {
                    CHANGE_TYPE = 3
                }
            }

            if (FileManager.alreadyDownloaded(getFilename())) {
                changeWallpaper(FileManager.getBitmapFromPath(getFilename(), displayWidth, displayHeight), CHANGE_TYPE)
            } else {
                progress_circular.visibility = View.VISIBLE

                // Calculate ImageSize And Show AlertDialog
                CoroutineScope(Dispatchers.IO).launch {
                    val imageSize = getImageSize(true)

                    withContext(Dispatchers.Main) {

                        progress_circular.visibility = View.GONE

                        val builder = AlertDialog.Builder(mContext)
                        builder.setTitle("사진 다운로드 필요")
                        builder.setMessage("약 $imageSize 의 이미지를 다운로드 받습니다.")
                        builder.setPositiveButton("확인") { dialog, which -> downloadImage(false) }
                        builder.setNegativeButton("취소") { dialog, which -> dialog.dismiss() }
                        builder.show()
                    }
                }
            }


        }

        builder.show()

    }

    private fun changeWallpaper(bitmap: Bitmap, type: Int) {

        try {
            // Load Full-Screen ScreenAds
            if (ADS_COUNTER == 3) {
                // Check If Ads loaded
                if (true) {
                    // Show Full-Screen Ads
                    MakePreferences.getInstance().settings.edit().putInt("ADS_COUNTER", 1).apply()
                }
            }

            // Set Background as CHANGE_TYPE
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    progress_circular.visibility = View.VISIBLE

                }
                SetWallpaperJob.setWallPaper(mContext, bitmap, type)
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "적용 완료!", Toast.LENGTH_SHORT).show()
                    progress_circular.visibility = View.GONE

                }
            }
        } catch (e: Error) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }


    }

    private fun downloadImage(action: Boolean) {

        // Pre Settings
        val notificationManager = NotificationManagerCompat.from(mContext)
        val builder = NotificationCompat.Builder(mContext, CHANNEL_ID)
        builder.setContentTitle("사진 다운로드 $photoID")
                .setContentText("사진을 다운로드 하는 중..")
                .setSmallIcon(R.drawable.ic_landscape_icon)
                .setGroup(GROUP_KEY_WORK_AUTO)
                .setPriority(Notification.PRIORITY_LOW)
                .setGroupSummary(true)
                .setOngoing(true)

        // Issue the initial notification from zero
        val PROGRESS_MAX = 100
        val PROGRESS_CURRENT = 0

        builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
        Toast.makeText(mContext, "다운로드를 시작합니다.", Toast.LENGTH_SHORT).show()

        val publishProgress = { value: Int ->
            builder.setProgress(100, value, false)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }

        // Start Download
        CoroutineScope(Dispatchers.IO).launch {
            var count = 0
            val url = URL(getDownloadUrl())
            val connection = url.openConnection()
            connection.connect()

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true

            val lengthOfFile = connection.contentLength

            val input = url.openStream()
            var bitmap = BitmapFactory.decodeStream(input, null, options)

            options.inSampleSize = FileManager.makeBitmapSmall(options.outWidth, options.outHeight, displayWidth, displayHeight)
            options.inJustDecodeBounds = false

            val inputStream = url.openStream()

            val data = ByteArray(1024)

            var total: Long = 0
            var pregress = 0
            while (input.read(data).also { count = it } != -1) {
                total += count.toLong()
                if (pregress + 10 <= (total * 100 / lengthOfFile).toInt()) {
                    publishProgress((total * 100 / lengthOfFile).toInt())
                    pregress = (total * 100 / lengthOfFile).toInt()
                }
            }

            bitmap = BitmapFactory.decodeStream(inputStream, null, options)

            input.close()

            // Download Finished

            withContext(Dispatchers.Main) {
                Toast.makeText(mContext, "다운로드가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }

            builder.setContentText("다운로드가 완료됨.").setProgress(100, 100, false).setOngoing(false)
            notificationManager.notify(NOTIFICATION_ID, builder.build())

            if (bitmap != null) {
                saveImage(bitmap)
                if (action) {
                    changeWallpaper(bitmap, CHANGE_TYPE
                    )
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(mContext, "죄송합니다. 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()

                }
            }

        }


    }


    private fun getAppSpecificAlbumStorageDir(context: Context, albumName: String): File? {

        MediaStore.Images()

        val file = File(
                context.getExternalFilesDir(
                        Environment.DIRECTORY_PICTURES
                ), albumName
        )

        if (!file?.mkdirs()) {
            Log.d("make", "Directory not created");
        }

        return file
    }

    private fun saveImage(imageToSave: Bitmap) {

        // android 10 supportment
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//            val file = getAppSpecificAlbumStorageDir(applicationContext,"auto")
//        }

        // get the path to sdcard
        val sdcard = Environment.getExternalStorageDirectory()
        // to this path add a new directory path
        val dir = File(sdcard.absolutePath + "/AUtoImages/")
        // create this directory if not already created
        dir.mkdir()
        // create the file in which we will write the contents
        val file = File(dir, getFilename())
        try {
            val out = FileOutputStream(file)
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun anim() {
        if (isFabOpen) {
            actionFab1.setImageResource(R.drawable.up_arrow_icon)
            fab2.startAnimation(fabClose)
            fab3.startAnimation(fabClose)
            fab2.isClickable = false
            fab3.isClickable = false
            isFabOpen = false
        } else {
            actionFab1.setImageResource(R.drawable.down_arrow_icon)
            fab2.startAnimation(fabOpen)
            fab3.startAnimation(fabOpen)
            fab2.isClickable = true
            fab3.isClickable = true
            isFabOpen = true
        }
    }

    private fun getImageSize(si: Boolean): String {

        Log.d("PhotoDetailView", "CurrentThread : ${Thread.currentThread().name}")

        val url = URL(getDownloadUrl())
        val connection = url.openConnection()
        connection.setRequestProperty("Accept-Encoding", "identify")
        val fileLengthAsLong = connection.contentLength


        // Calculate ImageSize into HumanReadable Size
        val unit = if (si) 1000 else 1024
        if (fileLengthAsLong < unit) return "$fileLengthAsLong B"
        val exp = (ln(fileLengthAsLong.toDouble()) / ln(unit.toDouble())).toInt()
        val pre = (if (si) "kMGTPE" else "KMGTPE")[exp - 1].toString() + if (si) "" else "i"


        return String.format("%.1f %sB", fileLengthAsLong / unit.toDouble().pow(exp.toDouble()), pre)
    }

    private fun getFilename(): String {
        return when (DOWNLOAD_TYPE) {
            DownloadType.RAW -> "${photoID}_Raw.jpeg"
            DownloadType.FULL -> "${photoID}_Full.jpeg"
            DownloadType.REGULAR -> "${photoID}_Regular.jpeg"
        }
    }

    private fun getDownloadUrl(): String {
        return when (DOWNLOAD_TYPE) {
            DownloadType.RAW -> results.urls.raw
            DownloadType.FULL -> results.urls.full
            DownloadType.REGULAR -> results.urls.regular
        }
    }

    private fun checkWritePermission() {
        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Write Permission Has Already Granted
            Log.d("PhotoDetailView", "Write Permission granted")
        } else {
            // Request Write Permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }

    private fun setClipBoardLink(context: Context, link: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("label", link)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
    }
}