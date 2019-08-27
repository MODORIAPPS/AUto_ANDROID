package com.modori.kwonkiseokee.AUto.ListOfPhotos;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        photosViewModel = ViewModelProviders.of(ListOfPhotosView.this).get(ListOfPhotosViewModel.class);
        photosViewModel.init();

        setUpRecyclerView();
        getPhotoByKeyword();

        goInfo.setOnClickListener(v -> makeDialog());
        goBack.setOnClickListener(v -> finish());

        getSupportActionBar().hide();

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

        photosViewModel.getListofPhotos(tag).observe(this, photosResponse -> {
            List<Results> photoResults = photosResponse.getResults();
            photoArrayList.addAll(photoResults);
            //adapter.notifyDataSetChanged();

            if (photoCnt == 1) {
                adapter = new ListOfPhotosAdapter(ListOfPhotosView.this, photoArrayList);
                recyclerView.setAdapter(adapter);

                String getStr = getResources().getString(R.string.tab3_showPictGetCnt);
                showGetPicCnt.setText(photosResponse.getTotal() + " " + getStr);

            } else {
                adapter.notifyItemInserted(photoCnt * 10);
            }

            photoCnt++;

            Log.d("tag1", "잘 가져옴");


        });

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
