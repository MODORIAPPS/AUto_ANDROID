package com.modori.kwonkiseokee.AUto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.modori.kwonkiseokee.AUto.RA.ListPhotoRA;
import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListsOfPhotos extends Activity {

    View goBack, goInfo;
    RecyclerView recyclerView;
    String tag;
    int photoCnt = 1;
    ListPhotoRA adapter;

    //List<PhotoSearch> results = new ArrayList<>();
    List<String> photoUrl = new ArrayList<>();
    List<String> photoID = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_of_photos);

        goBack = findViewById(R.id.goBack);
        recyclerView = findViewById(R.id.recyclerView);
        goInfo = findViewById(R.id.goInfo_list);


        Intent intent = getIntent();
        tag = intent.getExtras().getString("photoID");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        goInfo.setOnClickListener(v -> {
           makeDialog();
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (!recyclerView.canScrollVertically(-1)) {
                        //Log.i(TAG, "Top of list");
                    } else if (!recyclerView.canScrollVertically(1)) {
                        //Log.i(TAG, "End of list");
                        getPhotoByKeyword();
                    } else {
                        //Log.i(TAG, "idle");
                    }
                }
            });
        } else {
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition();

            if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                // End of the list is here.
                //Log.i(TAG, "End of list");
                getPhotoByKeyword();
            }
        }


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getPhotoByKeyword();


    }

    public void getPhotoByKeyword() {
        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag, photoCnt, 10).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        photoUrl.add(response.body().getResults().get(i).getUrls().getRegular());
                        photoID.add(response.body().getResults().get(i).getId());
                    }

                    if (photoCnt == 1) {
                        adapter = new ListPhotoRA(getApplicationContext(), photoUrl, photoID);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.notifyItemInserted(photoCnt * 10);
                    }

                    photoCnt++;


//
//                    if(photoCnt == 1){
//                        photoUrl = response.body().getResults();
//
//
//                    }else{
//                        int count = adapter.getItemCount();
//                        //photoUrl.add()
////                        Log.d("results의 사이즈", photoUrl.size() +"");
////                        for (int i = 0; i < response.body().getResults().size(); i++) {
////                            photoUrl.add(photoUrl.size()+i,response.body().getResults().get(i));
////                        }
//                        //List<Result>
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    ++photoCnt;

                    Log.d("tag1", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("사진 검색 오류", t.getMessage());

            }
        });
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {


            GridLayoutManager layoutManager = new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(layoutManager);
            adapter.notifyDataSetChanged();

        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

        }


    }

    private void makeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ListsPfPhotos_DialogTitle);
        builder.setMessage(R.string.ListsPfPhotos_DialogMessage);
        builder.setPositiveButton(R.string.ListsPfPhotos_DialogOk,
                (dialog, which) -> {
                });

        builder.show();
    }
}
