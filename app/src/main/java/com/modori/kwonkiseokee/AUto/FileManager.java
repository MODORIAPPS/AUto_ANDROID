package com.modori.kwonkiseokee.AUto;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileReader;

public class FileManager {

    File appDir;

    public void makeDir(){
        appDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "AUtogallay");

        //폴더가 만들어졌는지 체크
        if(!appDir.mkdirs()){
            Log.d("FILE", "폴더 생성 실패");
        }else{
            Log.d("FILE", "폴더 생성됨");
        }
    }

    public void copyToAutoGallary(String strCurPath){
        if(!appDir.exists()){
            makeDir();
        }else{

        }
    }

}
