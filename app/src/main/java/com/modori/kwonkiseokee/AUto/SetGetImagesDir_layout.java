package com.modori.kwonkiseokee.AUto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SetGetImagesDir_layout extends AppCompatActivity implements View.OnClickListener {

    public static final String PREFS_FILE = "PrefsFile";

    public static final int SELECT_FOLDER = 6541;
    private static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 2;


    RecyclerView listOfPictures;
    SharedPreferences settings;

    //Buttons (ImageView)
    ImageView openFolderBtn;
    ImageView goPickGallery;

    SwitchCompat setToDir;
    SwitchCompat setToUserPick;
    /*
    GET_SETTING 0 = 사용자가 선택한 폴더에서 불러온다.
    GET_SETTING 1 = 사용자가 갤러리에서 선택한 사진을 불러온다.
    GET_SETTING 2 = 사용자가 Unsplash 를 통해 다운로드 받은 사진을 불러온다.
     */
    int GET_SETTING;

    TextView showCurrDir;
    TextView warningNoImagesText;
    TextView showDeviceResor;
    TextView warningNoImagesPath;
    TextView getFromWhatText;

    String SelectedPath;


    ArrayList<String> imageFilesDe = new ArrayList<>();


    public void initWork() {
        goPickGallery = findViewById(R.id.goPickGallery);
        listOfPictures = findViewById(R.id.listsOfPictures);
        openFolderBtn = findViewById(R.id.openFolderBtn);
        showDeviceResor = findViewById(R.id.showDeviceResor);
        warningNoImagesPath = findViewById(R.id.warningNoImagesPath);
        getFromWhatText = findViewById(R.id.getFromWhatText);


        setToDir = findViewById(R.id.setToDir);
        setToUserPick = findViewById(R.id.setToUserPick);

        showCurrDir = findViewById(R.id.showCurrDir);
        warningNoImagesText = findViewById(R.id.warningNoImagesText);

        goPickGallery.setOnClickListener(this);
        listOfPictures.setOnClickListener(this);
        openFolderBtn.setOnClickListener(this);

        setToDir.setOnClickListener(this);
        setToUserPick.setOnClickListener(this);

        permissionCheck();

    }


    public String getDeviceResor() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        String returnStr;
        int width = size.x;
        int height = size.y;

        int resor = width * height;
        if (resor <= 921600) {
            // HD급 보다 작은 경우
            returnStr = width + " * " + height + " [HD] ";
        } else if (resor <= 2073600) {
            returnStr = width + " * " + height + " [FHD] ";

        } else if (resor <= 3686400) {
            returnStr = width + " * " + height + " [QHD] ";

        } else {
            returnStr = "해상도가 너무 높거나 낮으면 힘듭니다.";
        }

        return returnStr;
    }

    public void checkAvailImages(boolean option) {
        if (option) {
            // true 사용자 지정 탐색
            int checkAvailImages = FileManager.availableImages(this);

            if (checkAvailImages == 0) {
                warningNoImagesPath.setVisibility(View.VISIBLE);
            } else {
                warningNoImagesPath.setVisibility(View.INVISIBLE);
            }
        } else {
            int imagesCnt = FileManager.availableDefaultImages();
            if (imagesCnt == 0) {
                Log.d("파일 탐색기 결과", "AutoGallery에 사진 없음");
                warningNoImagesText.setVisibility(View.VISIBLE);
            } else {
                warningNoImagesText.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setimages_dir);
        initWork();

        settings = this.getSharedPreferences(PREFS_FILE, 0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        listOfPictures.setLayoutManager(linearLayoutManager);

        String resorStr = getDeviceResor();
        showDeviceResor.setText(resorStr);

//        GetFromGalleryRA adapter = new GetFromGalleryRA(this, imageFilesDe);
//        listOfPictures.setAdapter(adapter);

        checkAvailImages(true);

        // load prefernces

        SelectedPath = settings.getString("SelectedPath", "/system");
        showCurrDir.setText(SelectedPath);

        GET_SETTING = settings.getInt("GetSetting", 0);
        switch (GET_SETTING){
            case 0:
                setToDir.setChecked(true);
                getFromWhatText.setText("사용자가 지정한 폴더에서 불러오고 있습니다.");
                break;
            case 1:
                setToUserPick.setChecked(true);
                getFromWhatText.setText("사용자가 선택한 사진을 불러오고 있습니다.");
                break;

            case 2:
                getFromWhatText.setText("사용자가 다운로드 받은 사진을 불러오고 있습니다.");
        }

        setToDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setToDir.isChecked()){
                    //눌려있지 않았던 경우
                    setToUserPick.setChecked(false);
                    GET_SETTING = 0;
                }else{
                    setToUserPick.setChecked(true);
                    GET_SETTING = 1;
                }
            }
        });

        setToUserPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setToUserPick.isChecked()){
                    //눌려있지 않았던 경우
                    setToDir.setChecked(false);
                    GET_SETTING = 1;
                }else{
                    setToDir.setChecked(true);
                    GET_SETTING = 0;
                }
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FOLDER) {
            if (resultCode != this.RESULT_CANCELED) {
                SelectedPath = data.getStringExtra("SelectedPath");
                showCurrDir.setText(SelectedPath);

                saveSetting();
                checkAvailImages(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openFolderBtn:
                try {
                    Intent intent = new Intent(SetGetImagesDir_layout.this, SelectFolder.class);
                    startActivityForResult(intent, SELECT_FOLDER);
                } catch (Exception e) {
                    Toast.makeText(SetGetImagesDir_layout.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }

                break;

            case R.id.goPickGallery:
                startActivity(new Intent(SetGetImagesDir_layout.this, getFromGallery.class));
                break;
        }

    }

    public void saveSetting() {
        SelectedPath = showCurrDir.getText().toString();
        Log.d("SavedPath", showCurrDir.getText().toString());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("SelectedPath", SelectedPath);
        editor.apply();

        editor.putInt("GetSetting", GET_SETTING);
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveSetting();
    }

    @Override
    protected void onResume() {
        super.onResume();
        saveSetting();
    }

    // 권한 체크
    public void permissionCheck() {
        //읽기 권한 체크
        int ReadpermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //기기에 권한이 있는지 없는지 체크하는 조건문
        if (ReadpermissionCheck == PackageManager.PERMISSION_DENIED) {
            //권한이 없는 경우, 없으므로 권한 요청 메세지 띄운다.
            //요청 메세지가 띄워지면 '허용'과 '거절'이 보이는데 둘중 어느 것을 누르냐에 대한 처리는 onRequestPermissionsResult에서 처리한다.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
            //권한이 있는 경우
            //Toast.makeText(MainActivity.this, "읽기 권한이 있었구만?", Toast.LENGTH_SHORT).show();

        }

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //사용자가 권한 요청을 허용했을 때
                } else {
                    //Toast.makeText(MainActivity.this, "읽기 허용하라우", Toast.LENGTH_SHORT).show();
                    //사용자가 권한 요청을 거부했을 때

                }
        }
    }

}
