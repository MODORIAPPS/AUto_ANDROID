package com.modori.kwonkiseokee.AUto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.modori.kwonkiseokee.AUto.RA.GetFromGalleryRA;

import java.util.ArrayList;

public class getFromGallery extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "갤러리에서 사진 가져오기";

    final int PICTURE_REQUEST_CODE = 123;

    ArrayList<String> pickedLists = new ArrayList<>();
    GetFromGalleryRA adapter;

    RecyclerView recyclerView;
    Button resetBtn;
    Button copyToDeDirBtn;
    Button openGalleryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_from_gallery_layout);

        initWork();
        resetBtn.setOnClickListener(this);
        copyToDeDirBtn.setOnClickListener(this);
        openGalleryBtn.setOnClickListener(this);
    }

    public void initWork() {
        resetBtn = findViewById(R.id.resetBtn);
        copyToDeDirBtn = findViewById(R.id.copyToDeDirBtn);
        openGalleryBtn = findViewById(R.id.openGalleryBtn);
        recyclerView = findViewById(R.id.recyclerview);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);


    }

    public void setRecyclerView() {
        adapter = new GetFromGalleryRA(this, pickedLists, onClickItem);
        recyclerView.setAdapter(adapter);

    }
    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(getFromGallery.this, str, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetBtn:
                makeResetDialog();
                break;

            case R.id.copyToDeDirBtn:
                makeCopyDialog();
                break;

            case R.id.openGalleryBtn:
                Intent getFromGalllery = new Intent(Intent.ACTION_PICK);
                getFromGalllery.setType("image/*");
                getFromGalllery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                getFromGalllery.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(Intent.createChooser(getFromGalllery, "Select Picture"), PICTURE_REQUEST_CODE);
                break;

        }
    }

    public void makeResetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("가져온 사진 초기화")        // 제목 설정
                .setMessage("갤러리에서 가져온 사진을 초기화 합니까>")        // 메세지 설정
                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //삭제
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기


    }

    public void makeCopyDialog() {
        AlertDialog.Builder copyBuilder = new AlertDialog.Builder(this);

        copyBuilder.setTitle("가져온 사진을 복사")        // 제목 설정
                .setMessage("갤러리에서 가져온 사진을 앱 폴더로 복사합니다. 앱폴더는 " +
                        "sdcard/AUtoGallery로 저장됩니다.")        // 메세지 설정
                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //삭제
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        AlertDialog copyDialog = copyBuilder.create();    // 알림창 객체 생성
        copyDialog.show();    // 알림창 띄우기


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Log.d("선택한 사진 처리부 진입", "진입");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Uri uri = data.getData();

                //멀티 선택을 지원하지 않는 기기에서는 getClipdata() 가 없음 getData() 로 접근해야함
                if (data.getClipData() == null) {
                    Log.d("1개 선택됨", data.getDataString());
                    pickedLists.add(String.valueOf(data.getData()));

                    Log.d(TAG,data.getDataString());
                    setRecyclerView();


                } else {
                    ClipData clipData = data.getClipData();
                    Log.i("선택한 사진의 개수", String.valueOf(clipData.getItemCount()));

                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        //Uri urione = clipData.getItemAt(i).getUri();
                        String str = String.valueOf(clipData.getItemAt(i).getUri());
                        pickedLists.add(String.valueOf(str));
                        setRecyclerView();
                        Log.d("선택된 사진들", str);


                    }

                }


            } else {
                Log.d("통과 상태", String.valueOf(data));
            }
        }
    }


}
