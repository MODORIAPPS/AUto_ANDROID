package com.modori.kwonkiseokee.AUto.Service;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.modori.kwonkiseokee.AUto.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

public class SetWallpaperJob extends BroadcastReceiver {

    private final String[] okFileExtensions = new String[]{"jpg", "jpeg", "png", "gif"};


    public static final String PREFS_FILE = "PrefsFile";
    String SelectedPath;
    Boolean ShuffleMode;
    static int FileNumber = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Log.d("SetWallpaperJob", "신호 받음");

        try {
            //load preferences
            SharedPreferences settings = context.getSharedPreferences(PREFS_FILE, 0);
            SelectedPath = settings.getString("SelectedPath", "/system");
            ShuffleMode = settings.getBoolean("ShuffleMode", false);

            String FileName;

            File file = new File(SelectedPath);

            File[] imageFiles = file.listFiles();

            for (int i = 0; i < imageFiles.length; i++) {

                file = imageFiles[i];

                Log.d("찾는 for 문 진입 ", String.valueOf(i));
                //File file = files[i];

                if (file.canRead()) {
                    for (int k = 0; k <= 3; k++) {
                        String checkFile = okFileExtensions[k];
                        if (file.getName().toLowerCase().endsWith(checkFile)) {
                            imageFiles[i] = new File(String.valueOf(file.getName()));
                            Log.d("찾은 파일", String.valueOf(imageFiles[i]));
                        }
                    }
                }
            }

            //
            if (imageFiles.length > 0) {

                if (ShuffleMode) {
                    final Random myRandom = new Random();
                    FileNumber = myRandom.nextInt(imageFiles.length);
                    FileName = imageFiles[FileNumber].getName();
                } else {
                    FileName = imageFiles[FileNumber++].getName();
                }
                if (FileNumber == imageFiles.length)
                    FileNumber = 0;

                final WallpaperManager wallpaperManager =
                        WallpaperManager.getInstance(context);


                Bitmap myBitmap =
                        BitmapFactory.decodeFile(SelectedPath + "/" + FileName);

                wallpaperManager.setBitmap(myBitmap);

            } else {
                String alert = context.getResources().getString(R.string.noImageAlert);
                throw new Exception(alert);
            }

        } catch (Exception ae) {
            Toast.makeText(context, ae.getMessage(), Toast.LENGTH_LONG).show();
        }

    }//end onReceive


    private class OnlyExt implements FilenameFilter {
        String ext;

        public OnlyExt(String ext) {
            this.ext = "." + ext;
        }

        public boolean accept(File dir, String name) {
            return name.endsWith(ext);
        }
    }

}//end main class