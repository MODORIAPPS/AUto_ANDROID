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
import android.widget.TextView;
import android.widget.Toast;

import com.modori.kwonkiseokee.AUto.RA.GetFromGalleryRA;
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class getFromGallery extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "갤러리에서 사진 가져오기";

    Realm realm;
    List<String> photoList;
    final int PICTURE_REQUEST_CODE = 123;

    List<String> pickedLists = new ArrayList<>();
    GetFromGalleryRA photoNewAdapter, photoOldAdapter;


    RecyclerView newPhotosList;
    RecyclerView oldPhotosList;
    Button resetBtn;
    Button savePhotoBtn;
    Button openGalleryBtn;

    TextView newPhotosCnt, oldPhotosCnt, oldPhotosText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_from_gallery_layout);
        Realm.init(this);
        initWork();
        resetBtn.setOnClickListener(this);
        savePhotoBtn.setOnClickListener(this);
        openGalleryBtn.setOnClickListener(this);
    }

    public void initWork() {
        resetBtn = findViewById(R.id.resetBtn);
        savePhotoBtn = findViewById(R.id.savePhotoBtn);
        openGalleryBtn = findViewById(R.id.openGalleryBtn);
        newPhotosList = findViewById(R.id.newPhotos);
        oldPhotosList = findViewById(R.id.oldPhotos);

        newPhotosCnt = findViewById(R.id.newPhotosCnt);
        oldPhotosCnt = findViewById(R.id.oldPhotosCnt);
        oldPhotosText = findViewById(R.id.oldPhotosText);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 3);

        newPhotosList.setLayoutManager(gridLayoutManager);
        oldPhotosList.setLayoutManager(gridLayoutManager1);

        realm = Realm.getDefaultInstance();
        photoList = getPhotoList();

        photoOldAdapter = new GetFromGalleryRA(this, photoList, true);
        oldPhotosList.setAdapter(photoOldAdapter);

        if (photoList.size() == 0) {
            //oldPhotosText.setText("사진이 없습니다. 새 사진을 추가하세요.");
            //oldPhotosList.setVisibility(View.GONE);
        }
        oldPhotosCnt.setText(photoList.size() + "");
        newPhotosCnt.setText("0");


        Log.d(TAG, "저장되어 있는 사진 수" + photoList.size());


    }

    public void setRecyclerView() {
        photoNewAdapter = new GetFromGalleryRA(this, pickedLists, false);
        newPhotosList.setAdapter(photoNewAdapter);
        newPhotosCnt.setText(pickedLists.size() + "");

        photoList = getPhotoList();

        photoOldAdapter = new GetFromGalleryRA(this, photoList, true);
        oldPhotosList.setAdapter(photoOldAdapter);
        oldPhotosCnt.setText(photoList.size() + "");


    }

//    private View.OnClickListener newPhotosClicked = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //String str = (String) v.getTag();
//            //Toast.makeText(getFromGallery.this, str, Toast.LENGTH_SHORT).show();
//            getFromGallery.this.makeDeleteDialog(false, photoNewAdapter.getPhotoUri());
//        }
//    };
//
//    private View.OnClickListener oldPhotosClicked = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //String str = (String) v.getTag();
//            //Toast.makeText(getFromGallery.this, str, Toast.LENGTH_SHORT).show();
//            getFromGallery.this.makeDeleteDialog(true, photoOldAdapter.;);
//        }
//    };

    public void makeDeleteDialog(final boolean dataType, String photoUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("사진 삭제")        // 제목 설정
                .setMessage("목록에서 사진을 삭제할까요?")        // 메세지 설정
                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (dataType) {

                            realm.beginTransaction();
                            RealmResults<DevicePhotoDTO> photoDTOS = realm.where(DevicePhotoDTO.class).equalTo("photoUri_d", photoOldAdapter.getPhotoUri()).findAll();
                            Log.d(TAG, photoDTOS.toString());
                            Log.d(TAG, photoOldAdapter.getPhotoUri());
                            photoDTOS.deleteAllFromRealm();
                            realm.commitTransaction();


                            getFromGallery.this.setRecyclerView();


                        } else {
                            // new Photos
//                            pickedLists.remove(photoNewAdapter.getPhotoUri());
//                            photoNewAdapter.notifyDataSetChanged();
//                            newPhotosCnt.setText(pickedLists.size() + "");

                        }
                        //삭제
                    }
                }).setNegativeButton("취소", (dialog, whichButton) -> dialog.cancel());

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetBtn:
                makeResetDialog();
                break;

            case R.id.savePhotoBtn:
                makeSaveDialog();
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
                .setMessage("갤러리에서 가져온 사진을 초기화 합니까?")        // 메세지 설정
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

    public void makeSaveDialog() {
        AlertDialog.Builder copyBuilder = new AlertDialog.Builder(this);

        copyBuilder.setTitle("가져온 사진을 저장")        // 제목 설정
                .setMessage("갤러리에서 가져온 사진을 모두 저장합니다.")       // 메세지 설정
                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        savePhotos(pickedLists);
                        newPhotosCnt.setText("0");

                        pickedLists = new ArrayList<>();
                        photoList = new ArrayList<>();

                        setRecyclerView();
                    }
                }).setNegativeButton("취소", (dialog, whichButton) -> dialog.cancel());

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

                    Log.d(TAG, data.getDataString());
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


    private void savePhotos(List<String> photoList) {

        for (int i = 0; i < photoList.size(); i++) {
            realm.beginTransaction();

            DevicePhotoDTO photoInfo = realm.createObject(DevicePhotoDTO.class);
            photoInfo.setPhotoID_d("testID");

            photoInfo.setPhotoUri_d(photoList.get(i));
            Log.d(TAG, photoList.get(0));

            realm.commitTransaction();

        }

    }

    //사진 정보 리스트 반환
    private List<String> getPhotoList() {
        List<String> onlyPhotoUri = new ArrayList<>();
        RealmResults<DevicePhotoDTO> realmResults = realm.where(DevicePhotoDTO.class).findAll();
        for (int i = 0; i < realmResults.size(); i++) {
            onlyPhotoUri.add(realmResults.get(i).getPhotoUri_d());
        }
        //return realm.where(DevicePhotoDTO.class).findAll();
        return onlyPhotoUri;
    }


}
