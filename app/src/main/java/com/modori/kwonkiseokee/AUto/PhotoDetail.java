package com.modori.kwonkiseokee.AUto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.modori.kwonkiseokee.AUto.Service.SetWallpaperJob;
import com.modori.kwonkiseokee.AUto.Util.FileManager;
import com.modori.kwonkiseokee.AUto.Util.MakePreferences;
import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearchID;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.IllegalChannelGroupException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetail extends AppCompatActivity implements View.OnClickListener {

    ImageView detailImageView, goBackBtn, goInfo, goSettings;
    Context context;
    String photoID, downloadUrl, regularUrl;
    String authorProfileUrl, authorName;
    int heartCnt, downloadCnt;
    String TAG = "포토 디테일 액티비티";

    TextView heartCntV, downloadCntV, authorNameV;
    TextView uploadedDateV, photoColorV, photoDescriptionV, photoSizeV;
    View imagePColorV, infoLayout;
    CircleImageView authorProfile;

    Animation fab_open, fab_close;
    Boolean isFabOpen = false;
    FloatingActionButton fab1, fab2, fab3;

    String filename;
    PhotoSearchID results;

    Toolbar toolbar;

    Button copyColorBtn;

    boolean action = false;

    int CHANGE_TYPE;
    int DOWNLOAD_TYPE = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail);

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

        copyColorBtn.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        goInfo.setOnClickListener(this);
        goSettings.setOnClickListener(this);

        DOWNLOAD_TYPE = MakePreferences.getInstance().getSettings().getInt("DOWNLOAD_TYPE", 1);

        fab1.setClickable(false);


        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(PhotoDetail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) { // asks primission to use the devices camera
            Log.d(TAG, "쓰기 권한 확인");

        } else {
            requestWritePermission(PhotoDetail.this);
        }


        ApiClient.getPhotoById().getPhotoByID(photoID).enqueue(new Callback<PhotoSearchID>() {

            @Override
            public void onResponse(Call<PhotoSearchID> call, Response<PhotoSearchID> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body();
                    //downloadUrl = results.getUrls().getFull();
                    regularUrl = results.getUrls().getRegular();

                    heartCntV.setText(results.getLikes() + "");
                    downloadCntV.setText(results.getDownloads() + "");
                    Glide.with(context).load(results.getUser().getProfile_image().getMedium()).into(authorProfile);
                    authorNameV.setText(results.getUser().getUsername());

                    photoDescriptionV.setText(results.getDescription());
                    uploadedDateV.setText(results.getCreate_at());
                    photoColorV.setText(results.getColor());

                    int color  = Color.parseColor(results.getColor());

                    imagePColorV.setBackgroundColor(color);
                    photoSizeV.setText(results.getWidth() + " * " + results.getHeight());


                    Log.d("포토 디테일", "잘 가져옴");
                    Log.d("포토 디테일 id ", results.getId());
                    Glide.with(getApplicationContext()).load(results.getUrls().getRegular()).into(detailImageView);

                    getDownloadUrl(results);
                    fab1.setClickable(true);


                }
            }

            @Override
            public void onFailure(Call<PhotoSearchID> call, Throwable t) {
                Log.d("사진 검색 오류", t.getMessage());

            }
        });

        goBackBtn.setOnClickListener(v -> PhotoDetail.this.finish());

        detailImageView.setOnClickListener(v -> {
            // 사진 만 보는 곳으로 이동
            Intent goPhotoOnly = new Intent(PhotoDetail.this, showPhotoOnly.class);
            goPhotoOnly.putExtra("photoUrl", regularUrl);
            PhotoDetail.this.startActivity(goPhotoOnly);
        });


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionFab1:
                Log.d("fab1", "눌림");
                if (!fab1.isClickable()) {
                    Toast.makeText(PhotoDetail.this, "사진을 다 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    anim();

                }

                break;

            case R.id.fab2:
                anim();
                downloadUrl = getDownloadUrl(results);
                action = false;
                if (FileManager.alreadyDownloaded(filename)) {
                    //이미 있는 경우
                    Toast.makeText(PhotoDetail.this, "이미 파일이 존재합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    new downloadImage().execute(downloadUrl);

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

    public class downloadImage extends AsyncTask<String, String, Bitmap> {

        ProgressDialog pDialog;
        Bitmap mBitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PhotoDetail.this);
            pDialog.setMessage(getString(R.string.PhotoDeatil_Downloading));
            pDialog.show();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                mBitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return mBitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if (image != null) {
                saveImage(image, filename);
                if (action) {
                    SetWallpaperJob.setWallPaper(context, image, CHANGE_TYPE);
                }
                pDialog.dismiss();


            } else {
                pDialog.dismiss();
                Toast.makeText(PhotoDetail.this, "이미지가 존재하지 않습니다.",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void settingAction() {
        List<String> ListItems = new ArrayList<>();
        ListItems.add("RAW");
        ListItems.add("FULL");
        ListItems.add("REGULAR");
        CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사진 다운로드 품질을 선택하세요.");
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

            getDownloadUrl(results);
            MakePreferences.getInstance().getSettings().edit().putInt("DOWNLOAD_TYPE", DOWNLOAD_TYPE).apply();


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
                SetWallpaperJob.setWallPaper(context, FileManager.getBitmapFromPath(filename), CHANGE_TYPE);
            } else {
                downloadUrl = getDownloadUrl(results);
                new downloadImage().execute(downloadUrl);

            }


        });
        builder.show();


    }

    private String getDownloadUrl(PhotoSearchID results) {
        int DOWNLOAD_TYPE = MakePreferences.getInstance().getSettings().getInt("DOWNLOAD_TYPE", 1);

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

        return downloadUrl;

    }

    private static void requestWritePermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context)
                    .setMessage("This app needs permission to use The phone Camera in order to activate the Scanner")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    }).show();

        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public static void setClipBoardLink(Context context , String link){

        ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("label", link);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(context, "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show();

    }




}
