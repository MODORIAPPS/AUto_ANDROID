package com.modori.kwonkiseokee.AUto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearchID;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.modori.kwonkiseokee.AUto.tab3_frag.PREFS_FILE;

public class PhotoDetail extends AppCompatActivity {

    ImageView detailImageView;
    ImageView downloadedImage;

    String photoID;
    String TAG = "포토 디테일 액티비티";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail);

        detailImageView = findViewById(R.id.detailImageView);
        downloadedImage = findViewById(R.id.downloadedImage);


        if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(PhotoDetail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) { // asks primission to use the devices camera

            //what ever you want to do ..
            Log.d("권한 확인됨", "권한 확인됬더ㅏ고1");

        } else {
            requestWritePermission(PhotoDetail.this);
        }

        Intent intent = getIntent();
        photoID = intent.getExtras().getString("id");

        Log.d("받은 id", photoID);


        ApiClient.getPhotoById().getPhotoByID(photoID).enqueue(new Callback<PhotoSearchID>() {

            @Override
            public void onResponse(Call<PhotoSearchID> call, Response<PhotoSearchID> response) {
                if (response.isSuccessful()) {
                    //results[0] = (Results) response.body().getResults();
                    PhotoSearchID results = response.body();

                    Log.d("포토 디테일", "잘 가져옴");
                    Log.d("포토 디테일 id ", results.getId());
                    Glide.with(getApplicationContext()).load(results.getUrls().getFull()).into(detailImageView);


                }
            }

            @Override
            public void onFailure(Call<PhotoSearchID> call, Throwable t) {
                Log.d("사진 검색 오류", t.getMessage());

            }
        });

        detailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //String filename = photoID + "_" + sharedPreferences.getString("download_quality", "Full") + Resplash.DOWNLOAD_PHOTO_FORMAT;
                String filename = photoID + "_" + "Unsplash.jpg";
                //이미지 다운로드!@!!
                ApiClient.downloadClient().downloadFile(photoID).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        writeResponseBodyToDisk(response.body());
                        //Log.d("다운로드 성공?", "" + writtenToDisk);
                        Log.d(TAG, photoID + " 로 다운로드 받습니다.");
                        Log.d(TAG, response.message());

                        Log.d(TAG, String.valueOf(response.body() + "|" + response.headers()));
                        Log.d(TAG, String.valueOf(response.body().contentLength()));
                        //Log.d(TAG, String.valueOf(response.raw()));


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("다운로드 실패", t.getMessage());
                    }
                });

//
//                이미지뷰에서 이미지 다운로드 받는거야
//                Drawable d = detailImageView.getDrawable();
//                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
//
//                FileOutputStream out = null;
//
//                try{
//
//                    //String filename = photoID + "_Unsplash.jpg";
//                    out=new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/Pictures/" + filename);
//
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
//
//                }
//
//                catch(FileNotFoundException e)
//
//                {
//
//                    e.printStackTrace();
//
//                }

            }
        });


    }

    private void writeResponseBodyToDisk(ResponseBody body) {


        String filename = photoID + "samplePicture.jpg";
        // todo change the file location/name according to your needs
        File file = new File(Environment.getExternalStorageDirectory() + "/Pictures/" + filename);
        //File file = new File(SelectedPath);
        SharedPreferences settings = this.getSharedPreferences(PREFS_FILE, 0);
        String SelectedPath = settings.getString("SelectedPath", "sdcard/");

        try {
            InputStream is = body.byteStream();
            FileOutputStream fos = new FileOutputStream(
                    new File(Environment.getExternalStorageDirectory(), "image.jpg")
            );
            int read = 0;
            byte[] buffer = new byte[32768];
            while ((read = is.read(buffer)) > 0) {
                fos.write(buffer, 0, read);
            }

            fos.close();
            is.close();
        } catch (Exception e) {
            //Toast.makeText(MainActivity.this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
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
