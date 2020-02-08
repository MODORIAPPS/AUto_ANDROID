package com.modori.kwonkiseokee.AUto.PhotoDetail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.Service.SetWallpaperJob;
import com.modori.kwonkiseokee.AUto.Util.FileManager;
import com.modori.kwonkiseokee.AUto.Util.MakePreferences;
import com.modori.kwonkiseokee.AUto.Util.NETWORKS;
import com.modori.kwonkiseokee.AUto.RetrofitService.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearchID;
import com.modori.kwonkiseokee.AUto.showPhotoOnly;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetailView extends AppCompatActivity implements View.OnClickListener {

    ImageView detailImageView, goBackBtn, goInfo, goSettings;
    Context context;
    String photoID, downloadUrl, regularUrl, authorProfileUrl, authorName;
    int heartCnt, downloadCnt;
    String TAG = "포토 디테일 액티비티";
    TextView uploadedDateV, photoColorV, photoDescriptionV, photoSizeV, downloadTypeView, heartCntV, downloadCntV, authorNameV;
    View imagePColorV, infoLayout;
    CircleImageView authorProfile;

    Animation fab_open, fab_close;
    Boolean isFabOpen = false;
    FloatingActionButton fab1, fab2, fab3;

    String filename;
    PhotoSearchID results;

    Toolbar toolbar;
    ProgressBar progress_circular;

    Button copyColorBtn;

    private ProgressDialog pDialog;

    boolean action = false;

    int DOWNLOAD_TYPE = 1, CHANGE_TYPE, ADS_COUNTER;

    int displayWidth, displayHeight;

    private InterstitialAd interstitialAd;

    String channelId = "AUTO_SLIDE";
    int notificationId = 8980;

    String fileLength;
    int fileLengthAsLong;

    boolean okayGo = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel("AUTO_SLIDE", "SLIDE_CHANNEL", NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setDescription("CHANNEL FOR OREO");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationChannel.setSound(null,null);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
        }


        Intent intent = getIntent();
        photoID = intent.getExtras().getString("id");
        context = this;
        MakePreferences.getInstance().setSettings(context);
        Log.d("받은 id", photoID);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        copyColorBtn = findViewById(R.id.copyColorBtn);
        infoLayout = findViewById(R.id.linearLayout);
        toolbar = findViewById(R.id.toolbar);
        fab1 = findViewById(R.id.actionFab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        goInfo = findViewById(R.id.goInfo);
        goBackBtn = findViewById(R.id.goBackBtn);
        detailImageView = findViewById(R.id.detailImageView);
        heartCntV = findViewById(R.id.heartCnt);
        downloadCntV = findViewById(R.id.downloadsCnt);
        authorNameV = findViewById(R.id.authorName);
        authorProfile = findViewById(R.id.authorProfile);
        uploadedDateV = findViewById(R.id.uploadedDateV);
        photoColorV = findViewById(R.id.photoColorV);
        imagePColorV = findViewById(R.id.imagePColorV);
        photoDescriptionV = findViewById(R.id.photoDescription);
        photoSizeV = findViewById(R.id.photoSizeV);
        goSettings = findViewById(R.id.goSetting);
        downloadTypeView = findViewById(R.id.downloadTypeView);
        progress_circular = findViewById(R.id.progress_circular);

        copyColorBtn.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        goInfo.setOnClickListener(this);
        goSettings.setOnClickListener(this);


//        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
//        String ads_app = getResources().getString(R.string.ads_app);
//        MobileAds.initialize(getApplicationContext(), ads_app);
//        AdView adView = findViewById(R.id.adView_frag1);
//        //adView.loadAd(adRequest);
//        interstitialAd = new InterstitialAd(this);
//
//        //테스트 코드
//        interstitialAd.setAdUnitId(getString(R.string.InterstitialAd));
//        //interstitialAd.loadAd(new AdRequest.Builder().build());


        ADS_COUNTER = MakePreferences.getInstance().getSettings().getInt("ADS_COUNTER", 1);


        if (NETWORKS.getNetWorkType(context) == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.noNetworkErrorTitle);
            builder.setMessage(getString(R.string.noNetworkErrorContent));
            builder.setPositiveButton(R.string.tab2_DialogOk,
                    (dialog, which) -> {

                    });

            builder.show();

        } else if (NETWORKS.getNetWorkType(context) == 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.mobileNetworkWarningTitle));
            builder.setMessage(getString(R.string.mobileNetworkWarningContent));
            builder.setPositiveButton(R.string.tab2_DialogOk,
                    (dialog, which) -> {

                    });

            builder.show();
        }

        DOWNLOAD_TYPE = MakePreferences.getInstance().getSettings().getInt("DOWNLOAD_TYPE", 1);

        fab1.setClickable(false);
        setUpDialog();


        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(PhotoDetailView.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) { // asks primission to use the devices camera
            Log.d(TAG, "쓰기 권한 확인");

        } else {
            requestWritePermission(PhotoDetailView.this);
        }

        Display display = getWindowManager().getDefaultDisplay();
        displayWidth = display.getWidth();
        displayHeight = display.getHeight();

        ApiClient.getPhotoById().getPhotoByID(photoID).enqueue(new Callback<PhotoSearchID>() {

            @Override
            public void onResponse(Call<PhotoSearchID> call, Response<PhotoSearchID> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    System.out.println(results);
                    results = response.body();
                    //downloadUrl = results.getUrls().getFull();
                    regularUrl = results.getUrls().getRegular();

                    heartCntV.setText(results.getLikes() + "");
                    downloadCntV.setText(results.getDownloads() + "");

                    if (context != null) {
                        Glide.with(getApplicationContext()).load(results.getUser().getProfile_image().getMedium()).into(authorProfile);
                    }

                    authorNameV.setText(results.getUser().getUsername());

                    photoDescriptionV.setText(results.getDescription());
                    uploadedDateV.setText(results.getCreated_at());
                    photoColorV.setText(results.getColor());

                    int color = Color.parseColor(results.getColor());

                    imagePColorV.setBackgroundColor(color);
                    photoSizeV.setText(results.getWidth() + " * " + results.getHeight());


                    Log.d("포토 디테일", "잘 가져옴");
                    Log.d("포토 디테일 id ", results.getId());
                    Glide.with(getApplicationContext()).load(results.getUrls().getRegular())
                            .into(detailImageView);


//                    if (getImageLength.getState() == Thread.State.NEW) {
//                        getImageLength.start();
//                    }

                    fab1.setClickable(true);


                }
            }

            @Override
            public void onFailure(Call<PhotoSearchID> call, Throwable t) {
                Log.d("사진 검색 오류", t.getMessage());

            }


        });

        goBackBtn.setOnClickListener(v -> PhotoDetailView.this.finish());

        detailImageView.setOnClickListener(v -> {
            // 사진 만 보는 곳으로 이동
            Intent goPhotoOnly = new Intent(PhotoDetailView.this, showPhotoOnly.class);
            goPhotoOnly.putExtra("photoUrl", regularUrl);
            PhotoDetailView.this.startActivity(goPhotoOnly);
        });
//        if(getImageLength.getState() == Thread.State.NEW){
//            getImageLength.start();
//        }
        setDownloadTypeView();


    }

    private void setDownloadTypeView() {
        String mdownloadType;
        int downloadtype = MakePreferences.getInstance().getSettings().getInt("DOWNLOAD_TYPE", 1);
        switch (downloadtype) {
            case 0:
                mdownloadType = "RAW";

                break;
            case 1:
                mdownloadType = "FULL";

                break;
            case 2:
                mdownloadType = "REGULAR";

                break;

            default:
                mdownloadType = "NOT";

                break;

        }


        downloadTypeView.setText(mdownloadType);
    }

    private void saveImage(Bitmap imageToSave, String fileName) {
        // get the path to sdcard
        File sdcard = Environment.getExternalStorageDirectory();
        // to this path add a new directory path
        File dir = new File(sdcard.getAbsolutePath() + "/AUtoImages/");
        // create this directory if not already created
        dir.mkdir();
        // create the file in which we will write the contents
        File file = new File(dir, fileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    Thread getImageLength = new Thread() {
//        @Override
//        public void run() {
//            try {
//
//                URL url = new URL(getDownloadUrl(results, DOWNLOAD_TYPE));
//                System.out.println(downloadUrl);
//                URLConnection connection = url.openConnection();
//                connection.setRequestProperty("Accept-Encoding", "identity");
//                fileLengthAsLong = connection.getContentLength();
//                fileLength = humanReadableByteCount(fileLengthAsLong, true);
//                System.out.println(fileLength);
//
//
//            } catch (IOException io) {
//                io.printStackTrace();
//            }
//
//        }
//    };

    public class getImageSize extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress_circular.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... voids) {
            //super.doInBackground();

            try {

                URL url = new URL(getDownloadUrl(results, DOWNLOAD_TYPE));
                System.out.println(downloadUrl);
                URLConnection connection = url.openConnection();
                connection.setRequestProperty("Accept-Encoding", "identity");
                fileLengthAsLong = connection.getContentLength();
                fileLength = humanReadableByteCount(fileLengthAsLong, true);
                System.out.println("AsyncTask : " + fileLength);


            } catch (IOException io) {
                io.printStackTrace();
            }
            return fileLength;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress_circular.setVisibility(View.GONE);
            okayGo = true;

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionFab1:
                Log.d("fab1", "눌림");
                if (!fab1.isClickable()) {
                    Toast.makeText(PhotoDetailView.this, "사진을 다 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    anim();

                }

                break;

            case R.id.fab2:
                anim();

                progress_circular.setVisibility(View.VISIBLE);
                new getImageSize().execute();

                downloadUrl = getDownloadUrl(results, DOWNLOAD_TYPE);
                action = false;
                if (FileManager.alreadyDownloaded(filename)) {
                    //이미 있는 경우
                    Toast.makeText(PhotoDetailView.this, "이미 파일이 존재합니다.", Toast.LENGTH_SHORT).show();
                } else {

                    if (okayGo) {

                        System.out.println("다운로드 받을 파일 크기 : " + fileLength);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("사진 다운로드");
                        builder.setMessage("약 " + fileLength
                                + "의 이미지를 다운로드 받습니다.(저장될 때는 용량이 자동 압축됩니다.)");
                        builder.setPositiveButton("확인", (dialog, which) -> new downloadImage().execute(downloadUrl));
                        builder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());


                        builder.show();
                    }else{
                        Toast.makeText(context,"다운로드를 준비하고 있습니다.", Toast.LENGTH_SHORT).show();
                    }


                }

                break;

            case R.id.fab3:
                anim();
                action = true;
                fab3Action();
                break;

            case R.id.goInfo:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.PhotoDetail_DialogTitle);
                builder.setMessage(R.string.PhotoDetail_DialogMessage);
                builder.setPositiveButton(R.string.PhotoDetail_DialogOk,
                        (dialog, which) -> {

                        });

                builder.show();
                break;

            case R.id.goSetting:
                settingAction();
                break;

            case R.id.copyColorBtn:
                setClipBoardLink(context, results.getColor());
                break;
        }
    }

    public void anim() {
        if (isFabOpen) {
            fab1.setImageResource(R.drawable.up_arrow_icon);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.setImageResource(R.drawable.down_arrow_icon);

            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
        }
    }

    private void setUpDialog() {
        pDialog = new ProgressDialog(PhotoDetailView.this);
        pDialog.setCancelable(false);
        pDialog.setOnCancelListener(dialog -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PhotoDetailView.this);
            builder.setTitle("다운로드를 취소하시겠습니까?");
            builder.setMessage("다운로드를 취소합니다.");
            builder.setPositiveButton("확인", (dialog1, which) -> {
                dialog1.dismiss();
                pDialog.dismiss();
            });
        });
        pDialog.setMessage(getString(R.string.PhotoDeatil_Downloading));
    }

    public class downloadImage extends AsyncTask<String, String, Bitmap> {

        Bitmap mBitmap;
        NotificationManagerCompat notificationManager;
        NotificationCompat.Builder builder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (pDialog == null) {
                setUpDialog();
            }

            notificationManager = NotificationManagerCompat.from(context);
            builder = new NotificationCompat.Builder(context, channelId);
            builder.setContentTitle("사진 다운로드")
                    .setContentText("사진을 다운로드 하는 중..")
                    .setSmallIcon(R.mipmap.app_icon_round)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setOngoing(true);

            // Issue the initial notification with zero progress
            int PROGRESS_MAX = 100;
            int PROGRESS_CURRENT = 0;

            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);

            notificationManager.notify(notificationId, builder.build());
            Toast.makeText(context, "다운로드를 시작합니다.", Toast.LENGTH_SHORT).show();

            //pDialog.show();
        }

        protected Bitmap doInBackground(String... args) {
            BitmapFactory.Options options;
            int count;

            try {

                // xin

                URL url = new URL(args[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                int lenghtOfFile = connection.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = url.openStream();

                mBitmap = BitmapFactory.decodeStream(input, null, options);

                options.inSampleSize = FileManager.makeBitmapSmall(options.outWidth, options.outHeight, displayWidth, displayHeight);
                options.inJustDecodeBounds = false;
                Log.d("inSampleSize", String.valueOf(options.inSampleSize));

                InputStream inputStream = url.openStream();


                byte data[] = new byte[1024];

                long total = 0;
                int pregress = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    if (pregress + 10 <= (int) ((total * 100) / lenghtOfFile)) {
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                        pregress = (int) ((total * 100) / lenghtOfFile);

                    }
                }

                mBitmap = BitmapFactory.decodeStream(inputStream, null, options);


//                Log.d("DEVICE_TOTAL_RAM", String.valueOf(DEVICE_INFO.getDeviceTotalRam(context)));
//                Log.d("DEVICE_FREE_RAM", String.valueOf(DEVICE_INFO.getDeviceFreeRam(context)));
//
//                options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//
//
//                InputStream testStream = (InputStream) new URL(args[0]).getContent();
//
//                mBitmap = BitmapFactory.decodeStream(testStream, null, options);
//
//                options.inSampleSize = FileManager.makeBitmapSmall(options.outWidth, options.outHeight, displayWidth, displayHeight);
//                options.inJustDecodeBounds = false;
//                Log.d("inSampleSize", String.valueOf(options.inSampleSize));
//
//                InputStream inputStream = (InputStream) new URL(args[0]).getContent();
//
//                mBitmap = BitmapFactory.decodeStream(inputStream, null, options);
//
//                long total = 0;
//                int count;
//                while ((count = inputStream.read()) != -1) {
//                    System.out.println("whilewhile");
//                    total += count;
//                    // publishing the progress....
//                    int value = (int)(total * 100 / fileLengthAsLong);
//                    publishProgress(String.valueOf(value));
//                }
//
//                System.out.println("파일 크기 Long 환산 값 : " + fileLengthAsLong);
                input.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return mBitmap;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            System.out.println(values[0]);
            builder.setProgress(100, Integer.parseInt(values[0]), false);
            notificationManager.notify(notificationId, builder.build());

        }

        protected void onPostExecute(Bitmap image) {

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
                pDialog = null;
            }

            Toast.makeText(context, "다운로드가 완료되었습니다.", Toast.LENGTH_SHORT).show();

            builder.setContentText("다운로드가 완료됨.").setProgress(100, 100, false).setOngoing(false);
            notificationManager.notify(notificationId, builder.build());

            if (image != null) {
                saveImage(image, filename);
                if (action) {
                    try {
                        if (ADS_COUNTER == 3) {
                            if (interstitialAd.isLoaded()) {
                                interstitialAd.show();
                                MakePreferences.getInstance().setSettings(context);
                                MakePreferences.getInstance().getSettings().edit().putInt("ADS_COUNTER", 1).apply();

                            }
                        }
                        SetWallpaperJob.setWallPaper(context, image, CHANGE_TYPE);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("배경화면 적용 실패", e.getMessage());
                        Toast.makeText(PhotoDetailView.this, "Failed to set image", Toast.LENGTH_SHORT).show();

                    }
                }


            } else {
                Toast.makeText(PhotoDetailView.this, "이미지가 존재하지 않습니다.",
                        Toast.LENGTH_SHORT).show();


            }
        }
    }

    public String humanReadableByteCount(long bytes, boolean si) {
        System.out.println(bytes);
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    private void settingAction() {
        List<String> ListItems = new ArrayList<>();
        ListItems.add("RAW");
        ListItems.add("FULL");
        ListItems.add("REGULAR");
        CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.PhotoDetail_SelectQua));
        //builder.setMessage("이 설정은 유지됩니다. 언제든지 변경할 수 있습니다.");
        builder.setItems(items, (dialog, pos) -> {
            //String selectedText = items[pos].toString();
            switch (pos) {
                case 0:
                    // RAW
                    DOWNLOAD_TYPE = 0;
                    break;


                case 1:
                    // FULL
                    DOWNLOAD_TYPE = 1;
                    break;

                case 2:
                    // REGULAR
                    DOWNLOAD_TYPE = 2;
                    break;

                default:
                    break;

            }

            Log.d("DOWNLOAD_TYPE상태", DOWNLOAD_TYPE + "");

            getDownloadUrl(results, DOWNLOAD_TYPE);
            MakePreferences.getInstance().getSettings().edit().putInt("DOWNLOAD_TYPE", DOWNLOAD_TYPE).apply();
            setDownloadTypeView();

            okayGo = false;
            new getImageSize().execute();


        });
        builder.show();
    }

    private void fab3Action() {

        List<String> ListItems = new ArrayList<>();
        ListItems.add(getString(R.string.PhotoDetail_DialogItem1));
        ListItems.add(getString(R.string.PhotoDetail_DialogItem2));
        ListItems.add(getString(R.string.PhotoDetail_DialogItem3));
        CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.PhotoDetail_DialogTitle2);
        builder.setItems(items, (dialog, pos) -> {
            //String selectedText = items[pos].toString();

            switch (pos) {
                case 0:
                    CHANGE_TYPE = WallpaperManager.FLAG_LOCK;
                    break;


                case 1:
                    CHANGE_TYPE = WallpaperManager.FLAG_SYSTEM;
                    break;

                case 2:
                    CHANGE_TYPE = 999;
                    break;

            }

            if (FileManager.alreadyDownloaded(filename)) {
                try {
                    if (ADS_COUNTER == 3) {
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                            MakePreferences.getInstance().setSettings(context);
                            MakePreferences.getInstance().getSettings().edit().putInt("ADS_COUNTER", 1).apply();

                        }
                    }
                    SetWallpaperJob.setWallPaper(context, FileManager.getBitmapFromPath(filename, displayWidth, displayHeight), CHANGE_TYPE);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("배경화면 적용 실패", e.getMessage());

                }
            } else {
                downloadUrl = getDownloadUrl(results, DOWNLOAD_TYPE);
                new downloadImage().execute(downloadUrl);

            }


        });
        builder.show();


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

    }

    private String getDownloadUrl(PhotoSearchID results, int DOWNLOAD_TYPE) {

        if (DOWNLOAD_TYPE == 0) {
            // FULL
            downloadUrl = results.getUrls().getRaw();
            filename = photoID + "_" + "Raw_" + ".jpeg";

        } else if (DOWNLOAD_TYPE == 1) {
            filename = photoID + "_" + "Full_" + ".jpeg";
            downloadUrl = results.getUrls().getFull();
        } else if (DOWNLOAD_TYPE == 2) {
            filename = photoID + "_" + "Regular_" + ".jpeg";

            downloadUrl = results.getUrls().getRegular();
        }

        Log.d("DOWNLOAD_TYPE", DOWNLOAD_TYPE + "");
        Log.d("DOWNLOAD_URL", downloadUrl);

//        if (getImageLength.getState() == Thread.State.NEW) {
//            getImageLength.start();
//        }


        return downloadUrl;

    }

    private void makeNotification() {


        // Do the job here that tracks the progress.
        // Usually, this should be in a
        // worker thread
        // To show progress, update PROGRESS_CURRENT and update the notification with:
        // builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
        // notificationManager.notify(notificationId, builder.build());

        // When done, update the notification one more time to remove the progress bar


    }

    private static void requestWritePermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context)
                    .setMessage("쓰기 권한이 필요합니다.")
                    .setPositiveButton("네", (dialog, which) -> ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1)).show();

        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public static void setClipBoardLink(Context context, String link) {

        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("label", link);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(context, "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show();

    }


}
