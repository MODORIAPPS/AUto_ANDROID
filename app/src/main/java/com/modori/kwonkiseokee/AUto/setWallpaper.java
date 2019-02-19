package com.modori.kwonkiseokee.AUto;

import android.app.WallpaperManager;
import android.content.Context;
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
