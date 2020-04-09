package com.modori.kwonkiseokee.AUto.worker

import android.app.Notification
import android.content.Context
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.modori.kwonkiseokee.AUto.R
import com.modori.kwonkiseokee.AUto.utilities.CHANNEL_ID
import com.modori.kwonkiseokee.AUto.utilities.FileManager
import com.modori.kwonkiseokee.AUto.utilities.GROUP_KEY_WORK_AUTO
import java.net.URL

class DownloadWorker(
        private val context: Context,
        workerParameters: WorkerParameters,
        val photoID: String,
        val NOTIFICATION_ID: Int,
        val downloadUrl: String,
        val displayWidth:Int,
        val displayHeight:Int
) : Worker(context, workerParameters) {

    val notificationManager = NotificationManagerCompat.from(context)
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)

    val publishProgress = { value: Int ->
        builder.setProgress(100, value, false)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    override fun doWork(): Result {
        // Pre Settings
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
        Toast.makeText(context, "다운로드를 시작합니다.", Toast.LENGTH_SHORT).show()

        // startDownload
        var count = 0
        val url = URL(downloadUrl)
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

        Toast.makeText(context, "다운로드가 완료되었습니다.", Toast.LENGTH_SHORT).show()

        builder.setContentText("다운로드가 완료됨.").setProgress(100, 100, false).setOngoing(false)
        notificationManager.notify(NOTIFICATION_ID, builder.build())

        return Result.success()
    }

}