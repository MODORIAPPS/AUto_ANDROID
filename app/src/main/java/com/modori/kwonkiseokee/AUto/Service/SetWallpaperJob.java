package com.modori.kwonkiseokee.AUto.Service;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.utilities.MakePreferences;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import io.realm.Realm;

import static com.modori.kwonkiseokee.AUto.utilities.FileManager.makeBitmapSmall;

public class SetWallpaperJob extends BroadcastReceiver {

    private final String[] okFileExtensions = new String[]{"jpg", "jpeg", "png", "gif"};
    public static final String PREFS_FILE = "PrefsFile";
    String SelectedPath;
    String TAG = "SetWallpaperJob";
    Boolean ShuffleMode;
    int GET_SETTING;
    static int FileNumber = 0;
    Realm realm;

    private GestureDetector gestureDetector;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Context mContext = context.getApplicationContext();
        MakePreferences.getInstance().setSettings(mContext);
        Realm.init(mContext);
        realm = Realm.getDefaultInstance();
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.fade_in);


//        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                Log.e("onDoubleTap", e.toString());
//                //handle double tap
//                return true;
//            }
//        });

        //load preferences
        GET_SETTING = MakePreferences.getInstance().getSettings().getInt("GetSetting", 0);
        SelectedPath = MakePreferences.getInstance().getSettings().getString("SelectedPath", "/system");
        ShuffleMode = MakePreferences.getInstance().getSettings().getBoolean("ShuffleMode", false);
        //int getIndex = MakePreferences.getInstance().getSettings().getInt("GET_INDEX", 0);
        int getIndex = 0;

        Log.d("SetWallpaperJob", "신호 받음");

        if (GET_SETTING == 0) {
            // 사용자가 선택한 폴더에서 불러온다.
            try {

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
                            WallpaperManager.getInstance(mContext);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    Bitmap myBitmap = BitmapFactory.decodeFile(SelectedPath + "/" + FileName, options);

                    Log.d("이미지의 폭", options.outWidth + "");
                    Log.d("이미지의 높이", options.outHeight + "");

                    options.inSampleSize = makeBitmapSmall(options.outWidth, options.outHeight, 1080, 1920);
                    options.inJustDecodeBounds = false;
                    myBitmap = BitmapFactory.decodeFile(SelectedPath + "/" + FileName, options);


                    wallpaperManager.setBitmap(myBitmap);

                } else {
                    String alert = mContext.getResources().getString(R.string.noImageAlert);
                    throw new Exception(alert);
                }

            } catch (Exception ae) {
                Toast.makeText(mContext, ae.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else if (GET_SETTING == 1) {
            // 사용자가 갤러리에서 선택한 사진을 불러온다.
//
//            List<String> onlyPhotoUri = new ArrayList<>();
//            RealmResults<DevicePhotoDTO_OLD> realmResults = realm.where(DevicePhotoDTO_OLD.class).findAll();
//            for (int i = 0; i < realmResults.size(); i++) {
//                onlyPhotoUri.add(realmResults.get(i).getPhotoUri_d());
//            }
//
//            Log.d("OnlyPhotoUri", String.valueOf(onlyPhotoUri));
//
//
//            if (ShuffleMode) {
//                Random random = new Random();
//                getIndex = random.nextInt(onlyPhotoUri.size() + 1);
//            } else {
//                if (getIndex > onlyPhotoUri.size() - 1) {
//                    getIndex = 0;
//                    MakePreferences.getInstance().getSettings().edit().putInt("GET_INDEX", getIndex).apply();
//                } else {
//                    getIndex = getIndex + 1;
//                    MakePreferences.getInstance().getSettings().edit().putInt("GET_INDEX", getIndex).apply();
//                }
//
//            }
//
//            Log.d("getIndex", "Indexis" + getIndex);
//
//
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(onlyPhotoUri.get(getIndex)));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.d(TAG, e.getMessage());
//            }
//
//            WallpaperManager wallpaperManager =
//                    WallpaperManager.getInstance(mContext);
//
//            try {
//                wallpaperManager.setBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.d(TAG, e.getMessage());
//            }
        }


        //End

    }//end onReceive

//    private class OnlyExt implements FilenameFilter {
//        String ext;
//
//        public OnlyExt(String ext) {
//            this.ext = "." + ext;
//        }
//
//        public boolean accept(File dir, String name) {
//            return name.endsWith(ext);
//        }
//    }

    public static void setWallPaper(Context context, Bitmap image, int flag) throws IOException {

        MakePreferences.getInstance().setSettings(context);
        int ADS_COUNTER = MakePreferences.getInstance().getSettings().getInt("ADS_COUNTER", 1);
        ++ADS_COUNTER;
        MakePreferences.getInstance().getSettings().edit().putInt("ADS_COUNTER", ADS_COUNTER).apply();
        // ADS_COUNTER를 증가하고, 3이 되면 PhotoDetailView 에서 전면광고표시

        Context mContext = context.getApplicationContext();

        WallpaperManager wallpaperManager =
                WallpaperManager.getInstance(mContext);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            if (flag == WallpaperManager.FLAG_SYSTEM) {
                wallpaperManager.setBitmap(image, null, true, WallpaperManager.FLAG_SYSTEM);
            }else if(flag == WallpaperManager.FLAG_LOCK){
                wallpaperManager.setBitmap(image, null, true, WallpaperManager.FLAG_LOCK);

            }else{
                wallpaperManager.setBitmap(image,null,true);
            }
        }else{
            wallpaperManager.setBitmap(image);
            String errString = context.getString(R.string.SetWallpaperJob);
            Toast.makeText(context,errString, Toast.LENGTH_LONG).show();
        }



    }


}