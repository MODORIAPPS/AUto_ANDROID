package com.modori.kwonkiseokee.AUto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearchID;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetail extends AppCompatActivity implements View.OnClickListener {

    ImageView detailImageView, goBackBtn;
    Context context;
    String photoID, downloadUrl, regularUrl;
    String authorProfileUrl, authorName;
    int heartCnt, downloadCnt;
    String TAG = "포토 디테일 액티비티";

    TextView heartCntV, downloadCntV, authorNameV;
    TextView uploadedDateV, photoColorV, photoDescriptionV, photoSizeV;
    ImageView imagePColorV;
    CircleImageView authorProfile;

    Animation fab_open, fab_close;
    Boolean isFabOpen = false;
    FloatingActionButton fab1, fab2, fab3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail);
        Intent intent = getIntent();
        photoID = intent.getExtras().getString("id");

        Log.d("받은 id", photoID);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);

        context = this;
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


        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);


        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(PhotoDetail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) { // asks primission to use the devices camera

            //what ever you want to do ..
            Log.d("권한 확인됨", "권한 확인됬더ㅏ고1");

        } else {
            requestWritePermission(PhotoDetail.this);
        }


        ApiClient.getPhotoById().getPhotoByID(photoID).enqueue(new Callback<PhotoSearchID>() {

            @Override
            public void onResponse(Call<PhotoSearchID> call, Response<PhotoSearchID> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    PhotoSearchID results = response.body();
                    downloadUrl = results.getUrls().getFull();
                    regularUrl = results.getUrls().getRegular();

                    heartCntV.setText(results.getLikes() + "");
                    downloadCntV.setText(results.getDownloads() + "");
                    Glide.with(context).load(results.getUser().getProfile_image().getMedium()).into(authorProfile);
                    authorNameV.setText(results.getUser().getUsername());

                    photoDescriptionV.setText(results.getDescription());
                    uploadedDateV.setText(results.getCreate_at());
                    photoColorV.setText(results.getColor());
                    imagePColorV.setBackgroundColor(Color.parseColor(results.getColor()));
                    photoSizeV.setText(results.getWidth() + " * " + results.getHeight());


                    Log.d("포토 디테일", "잘 가져옴");
                    Log.d("포토 디테일 id ", results.getId());
                    Glide.with(getApplicationContext()).load(results.getUrls().getRegular()).into(detailImageView);


                }
            }

            @Override
            public void onFailure(Call<PhotoSearchID> call, Throwable t) {
                Log.d("사진 검색 오류", t.getMessage());

            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoDetail.this.finish();
            }
        });

        detailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사진 만 보는 곳으로 이동
                Intent goPhotoOnly = new Intent(PhotoDetail.this, showPhotoOnly.class);
                goPhotoOnly.putExtra("photoUrl", regularUrl);
                PhotoDetail.this.startActivity(goPhotoOnly);
            }
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
            case R.id.fab1:
                Log.d("fab1", "눌림");
                anim();
                break;

            case R.id.fab2:
                anim();
                new downloadImage().execute(downloadUrl);

                break;

            case R.id.fab3:
                anim();
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
            pDialog.setMessage("이미지를 다운로드 하는 중..");
            pDialog.show();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                mBitmap = BitmapFactory
                        .decodeStream((InputStream) new URL(args[0])
                                .getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return mBitmap;
        }

        protected void onPostExecute(Bitmap image) {
            String filename = photoID + "_" + "Full_" + ".jpeg";

            if (image != null) {
                saveImage(image, filename);
                pDialog.dismiss();

            } else {
                pDialog.dismiss();
                Toast.makeText(PhotoDetail.this, "이미지가 존재하지 않습니다.",
                        Toast.LENGTH_SHORT).show();

            }
        }
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

}
