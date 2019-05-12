package com.modori.kwonkiseokee.AUto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.modori.kwonkiseokee.AUto.RA.ListPhotoRA;
import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;
import com.modori.kwonkiseokee.AUto.data.data.Results;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListsOfPhotos extends AppCompatActivity {

    View goBack;
    RecyclerView recyclerView;
    String tag;
    int photoCnt = 1;
    ListPhotoRA adapter;

    List<Results> results = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_of_photos);

        goBack = findViewById(R.id.goBack);
        recyclerView = findViewById(R.id.recyclerView);


        Intent intent = getIntent();
        tag = intent.getExtras().getString("photoID");

        //GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

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
        }else{
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition();

            if(pastVisibleItems+visibleItemCount >= totalItemCount){
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

    public void getPhotoByKeyword(){
        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag,photoCnt, 10).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //results[0] = (Results) response.body().getResults();


                    //results.add(response.body().getResults(),response.body().getResults().size());

                    if(photoCnt == 1){
                        results = response.body().getResults();
                        adapter = new ListPhotoRA(getApplicationContext(), results);
                        recyclerView.setAdapter(adapter);


                    }else{
                        int count = adapter.getItemCount();
                        //results.add()
//                        Log.d("results의 사이즈", results.size() +"");
//                        for (int i = 0; i < response.body().getResults().size(); i++) {
//                            results.add(results.size()+i,response.body().getResults().get(i));
//                        }
                        //List<Result>
                        adapter.notifyDataSetChanged();
                    }

                    ++photoCnt;

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
        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){


            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

        }else{
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

        }


    }
}
