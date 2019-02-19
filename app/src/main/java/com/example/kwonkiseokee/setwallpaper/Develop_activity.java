package com.example.kwonkiseokee.setwallpaper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.bluetooth.le.AdvertiseCallback;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class Develop_activity extends AppCompatActivity {

    private static final int PICKER = 3;
    //현재의 배경화면을 가져오는 버튼
    private Button getCurrentWallpaper;
    //이미지를 배경화면으로 지정해주는 버튼
    private Button setAsWallpaper;
    //자동 배경화면 설정하러가기
    private Button goAutoWallpaper;
    //현재 배경화면 보여줄 이미지뷰
    private ImageView ViewCurrentWallpaper;
    //배경화면으로 지정할 이미지를 보여주는 이미지뷰
    private ImageView ViewSetAsWallpaper;
    //리사이클러뷰에서 사진을 선택하는 액티비티로 넘어감.
    Button goPickRecyclerview;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.develop_activity);

        context = getApplicationContext();

        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);

        goPickRecyclerview = findViewById(R.id.goRecyclerPicks);
        ViewCurrentWallpaper = findViewById(R.id.ViewCurrentWallpaper);
        ViewSetAsWallpaper = findViewById(R.id.ViewSetAsWallpaper);
        goAutoWallpaper = findViewById(R.id.goAutoWallpaper);

        //현재 기기의 배경화면을 불러오는 버튼 불러오기 및 현재 배경화면 불러오는 버튼
        getCurrentWallpaper = findViewById(R.id.getCurrentWallpaper);
        getCurrentWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable wallpaperDrawable = wallpaperManager.getDrawable();

                ViewCurrentWallpaper.setImageDrawable(wallpaperDrawable);
            }
        });

        //배경화면으로 지정할 이미지뷰 지정 및 배경화면으로 지정하는 버튼
        setAsWallpaper = findViewById(R.id.setAsWallpaper);
        setAsWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWallpaper setter = new setWallpaper();
                setter.setter(ViewSetAsWallpaper, context);
            }
        });

        //이미지 뷰 클릭시 앨범에서 사진을 가지고 올 수 있도록
        ViewSetAsWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "사진 선택하기"), PICKER);

            }
        });

        goPickRecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Develop_activity.this, pickon_recyclerview.class));
            }
        });

        goAutoWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Develop_activity.this, autoWallpaper.class));
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Uri imageLocation = data.getData();


        try {
            if (requestCode == PICKER && resultCode == RESULT_OK) {
                //앨범에서 선택한 이미지 파일의 주소를 가지고 온다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageLocation);
                int imageSize = (int)bitmap.getHeight() * (1024 / bitmap.getWidth());
                if(imageSize >= 10000){
                    Toast.makeText(Develop_activity.this,"이미지가 너무 큽니다.", Toast.LENGTH_SHORT).show();
                }

            }
            //이미지 뷰에 띄운다
            ViewSetAsWallpaper.setImageURI(imageLocation);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
