package com.modori.kwonkiseokee.AUto.utilities;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.modori.kwonkiseokee.AUto.AutoFragment.PREFS_FILE;

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


    public static int availableImages(Context context) {
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

    public static boolean alreadyDownloaded(String filename) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AUtoImages/";

        File file = new File(path + filename);
        if (file.exists()) {
            return true;
        }
        return false;


    }

    public static void mkdirAt(String dirName) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AUtoImages/" + dirName + "/";

        File file = new File(path);
        file.mkdirs();

    }

    public static Bitmap getBitmapFromPath(String filename, int screenWidth, int screenHeight) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AUtoImages/" + filename;
        // File file = new File(path + filename);
        Log.d("FilePath", path);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap src = BitmapFactory.decodeFile(path, options);

        options.inSampleSize = makeBitmapSmall(options.outWidth, options.outHeight, screenWidth, screenHeight);

        src = BitmapFactory.decodeFile(path);


        //return Bitmap.createScaledBitmap(src, 2560, 1440, true);
        return src;

    }


    public static int availableDefaultImages() {
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

    @SuppressLint("NewApi")
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{split[1]};
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static int makeBitmapSmall(int outWidth, int outHeight, int screenWidth, int screenHeight) {

        // 원본 이미지 비율인 1로 초기화
        int size = 1;

        // 해상도가 깨지지 않을만한 요구되는 사이즈까지 2의 배수의 값으로 원본 이미지를 나눈다.
        if (outHeight > screenHeight || outWidth > screenWidth) {

            if (outWidth > outHeight) {
                size = Math.round((float) outHeight / (float) screenHeight);
            } else {
                size = Math.round((float) outWidth / (float) screenWidth);
            }
        }
        return size;
    }
}