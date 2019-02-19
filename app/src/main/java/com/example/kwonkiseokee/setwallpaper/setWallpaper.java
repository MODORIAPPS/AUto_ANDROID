package com.example.kwonkiseokee.setwallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;

public class setWallpaper{


    public void setter(ImageView ViewSetAsWallpaper, Context context){

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        ViewSetAsWallpaper.setDrawingCacheEnabled(true);

        try {
            wallpaperManager.setBitmap(ViewSetAsWallpaper.getDrawingCache());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
