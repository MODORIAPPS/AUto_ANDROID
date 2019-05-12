package com.modori.kwonkiseokee.AUto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.modori.kwonkiseokee.AUto.tab3_frag.PREFS_FILE;

public class FileManager {

    private static final int WRITE_REQUEST_CODE = 43;


    static File appDir;

    public static void makeDir() {
//        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
//
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//        intent.setType()

        final String strSDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        appDir = new File(strSDPath + "/AUtoGallery/");

        appDir.mkdirs();
        try {
            appDir.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("폴더가 만들어지지 않은 이유", e.getMessage());
        }
    }


    static int availableImages(Context context) {
        int imagesCnt = 0;

        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE, 0);
        String SelectedPath = settings.getString("SelectedPath", "sdcard/");

        File file = new File(SelectedPath);

        File[] imageFiles = file.listFiles();

        for (int i = 0; i < imageFiles.length; i++) {

            Log.d("찾는 for 문 진입 ", String.valueOf(i));
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

    static int availableDefaultImages() {
        //MakePreferences settings = context.getSharedPreferences(PREFS_FILE, 0);
        //String SelectedPath = settings.getString("SelectedPath", "sdcard/");
        //Log.d("파일의 형식", SelectedPath);

        final String strSDPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        File file = new File(strSDPath + "/AUtoGallery");


        File[] imageFiles = file.listFiles();

//        for (int i = 0; i < imageFiles.length; i++) {
//
//            Log.d("찾는 for 문 진입 ", String.valueOf(i));
//            file = imageFiles[i];
//            String[] okFileExtensions = new String[]{"jpg", "jpeg", "png", "gif"};
//
//            if (file.canRead()) {
//                for (int k = 0; k <= 3; k++) {
//                    String checkFile = okFileExtensions[k];
//                    if (file.getName().toLowerCase().endsWith(checkFile)) {
//                        imagesCnt++;
//                    }
//                }
//            }
//
//
//        }
        //Log.d("찾은 이미지 파일", String.valueOf(imagesCnt));
        return 8;
    }

    public static Uri sendImagesUri(int position) {
        Uri imageUri = null;

        final String strSDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(strSDPath + "/AUtoGallery");


        File[] imageFiles = file.listFiles();

        for (int i = 0; i < imageFiles.length; i++) {

            Log.d("찾는 for 문 진입 ", String.valueOf(i));
            file = imageFiles[i];
            String[] okFileExtensions = new String[]{"jpg", "jpeg", "png", "gif"};

            if (file.canRead()) {
                for (int k = 0; k <= 3; k++) {
                    String checkFile = okFileExtensions[k];
                    if (file.getName().toLowerCase().endsWith(checkFile)) {
                        //imagesCnt++;
                    }
                }
            }


        }

        imageUri = Uri.fromFile(imageFiles[position]);

        return imageUri;
    }
}