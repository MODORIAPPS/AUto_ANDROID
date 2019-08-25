package com.modori.kwonkiseokee.AUto.ListOfPhotos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.Util.NETWORKS;
import com.modori.kwonkiseokee.AUto.data.data.Results;

import java.util.ArrayList;
import java.util.List;

public class ListOfPhotosView extends AppCompatActivity {

    View goBack, goInfo;
    TextView showGetPicCnt;
    String tag;
    int photoCnt = 1;

    //List<PhotoSearch> results = new ArrayList<>();
    List<String> photoUrl = new ArrayList<>();
    List<String> photoID = new ArrayList<>();

    private ListOfPhotosView listsOfPhotosView;
    ArrayList<Results> photoArrayList = new ArrayList<>();
    ListOfPhotosAdapter adapter;
    RecyclerView recyclerView;
    ListOfPhotosViewModel photosViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_of_photos);

        if (NETWORKS.getNetWorkType(this) == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.noNetworkErrorTitle));
            builder.setMessage(getString(R.string.noNetworkErrorContent));
            builder.setPositiveButton(R.string.tab2_DialogOk,
                    (dialog, which) -> {

                    });

            builder.show();

        }


        Intent intent = getIntent();
        tag = intent.getExtras().getString("photoID");




        goBack = findViewById(R.id.goBack);
        recyclerView = findViewById(R.id.recyclerView);
        goInfo = findViewById(R.id.goInfo_list);
        showGetPicCnt = findViewById(R.id.showPictGetCnt);

        photosViewModel = ViewModelProviders.of(this).get(ListOfPhotosViewModel.class);
        photosViewModel.init();

        setUpRecyclerView();
        getPhotoByKeyword();

        goInfo.setOnClickListener(v -> makeDialog());
        goBack.setOnClickListener(v -> finish());

    }

    private void setUpRecyclerView() {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (!recyclerView.canScrollVertically(-1)) {
                        //Log.i(TAG, "Top of list");
                    } else if (!recyclerView.canScrollVertically(1)) {
                        //Log.i(TAG, "End of list");
                        getPhotoByKeyword();
                    } else {
                        //Log.i(TAG, "idle");
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

        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void getPhotoByKeyword() {

        photosViewModel.getListofPhotos(tag).observe(this,photosResponse -> {
            List<Results> photoResults = photosResponse.getResults();
            photoArrayList.addAll(photoResults);
            //adapter.notifyDataSetChanged();

            if (photoCnt == 1) {
                adapter = new ListOfPhotosAdapter(ListOfPhotosView.this, photoArrayList);
                recyclerView.setAdapter(adapter);

                String getStr = getResources().getString(R.string.tab3_showPictGetCnt);
                showGetPicCnt.setText(photosResponse.getTotal()+ " " + getStr);

            } else {
                adapter.notifyItemInserted(photoCnt * 10);
            }

            photoCnt++;

            Log.d("tag1", "잘 가져옴");


        });
//
//        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag, photoCnt, 10).enqueue(new Callback<PhotoSearch>() {
//            @Override
//            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
//                if (response.isSuccessful()) {
//
////                    for (int i = 0; i < response.body().getResults().size(); i++) {
////                        photoUrl.add(response.body().getResults().get(i).getUrls().getRegular());
////                        photoID.add(response.body().getResults().get(i).getId());
////                    }
//
//                    if (photoCnt == 1) {
//                        adapter = new ListOfPhotosAdapter(getApplicationContext(), photoUrl, photoID);
//                        recyclerView.setAdapter(adapter);
//                    } else {
//                        adapter.notifyItemInserted(photoCnt * 10);
//                    }
//
//                    photoCnt++;
//
//                    String getStr = getResources().getString(R.string.tab3_showPictGetCnt);
//                    showGetPicCnt.setText(response.body().getTotal() + " " + getStr);
//
//
//                    Log.d("tag1", "잘 가져옴");
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PhotoSearch> call, Throwable t) {
//                Log.d("사진 검색 오류", t.getMessage());
//
//            }
//        });
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {


//            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//            recyclerView.setLayoutManager(layoutManager);
//            adapter.notifyDataSetChanged();

        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

        }


    }

    private void makeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ListsPfPhotos_DialogTitle);
        builder.setMessage(R.string.ListsPfPhotos_DialogMessage);
        builder.setPositiveButton(R.string.ListsPfPhotos_DialogOk,
                (dialog, which) -> {
                });

        builder.show();
    }
}
