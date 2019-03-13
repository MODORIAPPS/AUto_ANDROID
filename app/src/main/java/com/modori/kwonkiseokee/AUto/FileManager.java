package com.modori.kwonkiseokee.AUto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileReader;

import static com.modori.kwonkiseokee.AUto.tab3_frag.PREFS_FILE;

public class FileManager {


    private static int imagesCnt = 0;
    File appDir;

    public void makeDir() {
        appDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "AUtogallay");

        //폴더가 만들어졌는지 체크
        if (!appDir.mkdirs()) {
            Log.d("FILE", "폴더 생성 실패");
        } else {
            Log.d("FILE", "폴더 생성됨");
        }
    }

    public void copyToAutoGallary(String strCurPath) {
        if (!appDir.exists()) {
            makeDir();
        } else {

        }
    }


    public static int availableImages(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE, 0);
        String SelectedPath = settings.getString("SelectedPath", "sdcard/");

        File file = new File(SelectedPath);

        File[] imageFiles = file.listFiles();

        for (int i = 0; i < imageFiles.length; i++) {

            Log.d("찾는 for 문 진입 ", String.valueOf(i));
            //File file = files[i];


//            for (String extension : okFileExtensions) {
//                if (file.getName().toLowerCase().endsWith(extension)) {
//
//                    //들어가지 못하고 있는 문제가 있음.
//                    imageFiles[i] = new File(String.valueOf(file.getName()));
//                    imagesCnt++;
//                    Log.d("찾은 파일", String.valueOf(imageFiles[i]));
//                }
//            }

            file = imageFiles[i];
            String[] okFileExtensions = new String[]{"jpg", "jpeg", "png", "gif"};

            if (file.canRead()) {
                for (int k = 0; k <= 3; k++) {
                    String checkFile = okFileExtensions[k];
                    if (file.getName().toLowerCase().endsWith(checkFile)) {
                        imagesCnt++;
                    }
                }
            }


        }
        Log.d("찾은 이미지 파일", String.valueOf(imagesCnt));
        return imagesCnt;
    }
}