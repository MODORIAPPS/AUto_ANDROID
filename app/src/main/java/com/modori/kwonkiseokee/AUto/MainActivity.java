package com.modori.kwonkiseokee.AUto;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.modori.kwonkiseokee.AUto.AutoFragment.PREFS_FILE;

public class MainActivity extends AppCompatActivity {

    FrameLayout mainFrame;
    BottomNavigationView navBar;

    private static final int MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //임시 스플래쉬 띄우는 부분
        SharedPreferences settings = this.getSharedPreferences(PREFS_FILE, 0);
        boolean firstLaunch = settings.getBoolean("FirstLaunch", false);
        SharedPreferences.Editor editor = settings.edit();

        permissionCheck();

        mainFrame = findViewById(R.id.mainFrame);
        navBar = findViewById(R.id.navBar);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new AlbumFragment()).commit();
        navBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.goTranslate:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new AlbumFragment()).commit();
                    return true;

                case R.id.goPhotos:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new ExploreFragmentK()).commit();
                    return true;

                case R.id.goAutoSet:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, new AutoFragment()).commit();
                    return true;

                default:
                    return false;
            }
        });


    }

    public void permissionCheck() {
        //읽기 권한 체크
        int ReadpermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        //기기에 권한이 있는지 없는지 체크하는 조건문
        if (ReadpermissionCheck == PackageManager.PERMISSION_DENIED) {
            //권한이 없는 경우, 없으므로 권한 요청 메세지 띄운다.
            //요청 메세지가 띄워지면 '허용'과 '거절'이 보이는데 둘중 어느 것을 누르냐에 대한 처리는 onRequestPermissionsResult에서 처리한다.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);

        } else {
            //권한이 있는 경우
            //Toast.makeText(MainActivity.this, "읽기 권한이 있었구만?", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //사용자가 권한 요청을 허용했을 때
                } else {
                    //Toast.makeText(MainActivity.this, "읽기 허용하라우", Toast.LENGTH_SHORT).show();
                    //사용자가 권한 요청을 거부했을 때

                }
        }
    }


}
