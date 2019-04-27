package com.modori.kwonkiseokee.AUto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearchID;
import com.modori.kwonkiseokee.AUto.data.data.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetail extends AppCompatActivity {

    ImageView detailImageView;

    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_detail);

        Intent intent = getIntent();
        tag = intent.getExtras().getString("id");

        Log.d("받은 id", tag);

        detailImageView = findViewById(R.id.detailImageView);

        ApiClient.getPhotoById().getPhotoByID(tag).enqueue(new Callback<PhotoSearchID>() {

            @Override
            public void onResponse(Call<PhotoSearchID> call, Response<PhotoSearchID> response) {
                if (response.isSuccessful()) {
                    //results[0] = (Results) response.body().getResults();
                    PhotoSearchID results = response.body();

                    Log.d("포토 디테일", "잘 가져옴");
                    Log.d("포토 디테일 id ", results.getId());
                    Glide.with(getApplicationContext()).load(results.getUrls().getRegular()).into(detailImageView);


                }
            }

            @Override
            public void onFailure(Call<PhotoSearchID> call, Throwable t) {
                Log.d("사진 검색 오류", t.getMessage());

            }
        });


    }
}
