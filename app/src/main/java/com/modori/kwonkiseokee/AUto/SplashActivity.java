package com.modori.kwonkiseokee.AUto;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.modori.kwonkiseokee.AUto.ViewPager.SplashViewpagerAdapter;

import androidx.viewpager.widget.ViewPager;


public class SplashActivity extends AppCompatActivity {

    public static final String PREFS_FILE = "PrefsFile";


    ViewPager splashViewpager;

    ImageView splashLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splah_screen);


        splashLogo = findViewById(R.id.splashImage);
        splashViewpager = findViewById(R.id.splashViewPager);


        SharedPreferences settings = this.getSharedPreferences(PREFS_FILE, 0);

        boolean firstLaunch = settings.getBoolean("FirstLaunch", false);
        SharedPreferences.Editor editor = settings.edit();



        Log.d("LaunchStats", String.valueOf(firstLaunch));

        //초기 실행일 경우 false를 받아옴

        if (firstLaunch){


            splashViewpager.setVisibility(View.GONE);
            splashLogo.setVisibility(View.VISIBLE);
            timeHandler();
        }else{
            //초기 실행시의 행동

            splashLogo.setVisibility(View.GONE);
            splashViewpager.setVisibility(View.VISIBLE);
            SplashViewpagerAdapter adapter = new SplashViewpagerAdapter(getSupportFragmentManager());
            splashViewpager.setAdapter(adapter);

            editor.putBoolean("FirstLaunch", true);
            editor.apply();




        }


        //timeHandler();

        /*
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("state", "launch");
        startActivity(intent);
        finish();

        */

    }

    public void timeHandler() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);

    }

}
