package com.modori.kwonkiseokee.AUto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FrameLayout mainFrame;
    Toolbar toolbar;
    BottomNavigationView bottomNAV;


    private static final int MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goAboutThisApp:
                //이 앱에 관한 설명 액티비티로 이동
                //startActivity(new Intent(MainActivity.this, about_this_app.class));
                Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();

                //Intent goAboutThisApp = new Intent(getApplicationContext(), about_this_app.class);
                //startActivity(goAboutThisApp);
                return true;

            case R.id.goGithub:
                //깃허브 페이지로 이동
                //Toast.makeText(MainActivity.this, "준비중입니다.", Toast.LENGTH_SHORT).show();

                Intent openGithub = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MODORIAPPS/-Asset-SetWallPaperAPP_ANDROID_JAVA"));
                startActivity(openGithub);
                return true;

            default:
                return false;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        permissionCheck();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");


    }

    public void permissionCheck() {
        //읽기 권한 체크
        int ReadpermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        //기기에 권한이 있는지 없는지 체크하는 조건문
        if (ReadpermissionCheck == PackageManager.PERMISSION_DENIED) {
            //권한이 없는 경우, 없으므로 권한 요청 메세지 띄운다.
            //요청 메세지가 띄워지면 '허용'과 '거절'이 보이는데 둘중 어느 것을 누르냐에 대한 처리는 onRequestPermissionsResult에서 처리한다.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);

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
