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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.modori.kwonkiseokee.AUto.RA.GetFromGalleryRA;

import java.util.ArrayList;

import static com.modori.kwonkiseokee.AUto.tab3_frag.PREFS_FILE;

public class SetGetImagesDir_layout extends AppCompatActivity implements View.OnClickListener {

    public static final int SELECT_FOLDER = 6541;
    private static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 2;


    RecyclerView listOfPictures;


    //Buttons (ImageView)
    ImageView openFolderBtn;
    ImageView goPickGallery;

    SwitchCompat setToDir;
    SwitchCompat setToUserPick;

    TextView showCurrDir;
    TextView warningNoImagesText;
    TextView showDeviceResor;

    String SelectedPath;

    ArrayList<String> imageFilesDe = new ArrayList<>();


    public void initWork() {
        goPickGallery = findViewById(R.id.goPickGallery);
        listOfPictures = findViewById(R.id.listsOfPictures);
        openFolderBtn = findViewById(R.id.openFolderBtn);
        showDeviceResor = findViewById(R.id.showDeviceResor);

        setToDir = findViewById(R.id.setToDir);
        setToUserPick = findViewById(R.id.setToUserPick);

        showCurrDir = findViewById(R.id.showCurrDir);
        warningNoImagesText = findViewById(R.id.warningNoImagesText);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        listOfPictures.setLayoutManager(linearLayoutManager);

        goPickGallery.setOnClickListener(this);
        listOfPictures.setOnClickListener(this);
        openFolderBtn.setOnClickListener(this);

        setToDir.setOnClickListener(this);
        setToUserPick.setOnClickListener(this);

        FileManager.makeDir();
        String resorStr = getDeviceResor();
        showDeviceResor.setText(resorStr);

        int imagesCnt = FileManager.availableDefaultImages();
        for (int i = 0; i < imagesCnt; i++) {
            imageFilesDe.add(String.valueOf(FileManager.sendImagesUri(i)));
        }

        GetFromGalleryRA adapter = new GetFromGalleryRA(this, imageFilesDe);
        listOfPictures.setAdapter(adapter);

        checkAvailImages();
        // load preferences
        SharedPreferences settings = this.getSharedPreferences(PREFS_FILE, 0);
        SelectedPath = settings.getString("SelectedPath", "/system");
        showCurrDir.setText(SelectedPath);

        permissionCheck();


    }



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


    public String getDeviceResor() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return width + " * " + height;
    }

    public void checkAvailImages() {
        int imagesCnt = FileManager.availableDefaultImages();
        if (imagesCnt == 0) {
            Log.d("파일 탐색기 결과", "AutoGallery에 사진 없음");
            warningNoImagesText.setVisibility(View.VISIBLE);
        } else {
            warningNoImagesText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_get_images_dir_layout);

        initWork();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FOLDER) {
            if (resultCode != this.RESULT_CANCELED) {
                SelectedPath = data.getStringExtra("SelectedPath");
                showCurrDir.setText(SelectedPath);
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


    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = this.getSharedPreferences(PREFS_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();

        SelectedPath = showCurrDir.getText().toString();
        editor.putString("SelectedPath", SelectedPath);
        editor.apply();

    }
}
