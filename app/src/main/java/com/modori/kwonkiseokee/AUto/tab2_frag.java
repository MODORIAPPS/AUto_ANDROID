package com.modori.kwonkiseokee.AUto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.modori.kwonkiseokee.AUto.RecyclerViewAdapter.AdapterTab2_tagLists;
import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;
import com.modori.kwonkiseokee.AUto.data.data.Results;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tab2_frag extends Fragment {

    List<Results> results = new ArrayList<>();
    //Results[] results = new Results[5];

    Context context;


    public static final String PREFS_FILE = "PrefsFile";

    //TagLists
    ArrayList<String> tagLists;
    AdapterTab2_tagLists adapterOfTagLists;

    private static String tag1 = null;
    private static String tag2 = null;
    private static String tag3 = null;
    private static String tag4 = null;
    private static String tag5 = null;
    private static String tag6 = null;


    //Widgets
    RecyclerView viewTagLists;

    //GridViews of tags
    ImageView tag1Gridview;
    ImageView tag2Gridview;
    ImageView tag3Gridview;
    ImageView tag4Gridview;
    ImageView tag5Gridview;
    ImageView tag6Gridview;

    // TextView of GridViews, tags..
    TextView view_tag1Grid;
    TextView view_tag2Grid;
    TextView view_tag3Grid;
    TextView view_tag4Grid;
    TextView view_tag5Grid;
    TextView view_tag6Grid;


    Toolbar toolbar;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_frag, container, false);
        tagLists = new ArrayList<>();

        initWork();

        getPhotosAsEachTag(tag1, tag2, tag3, tag4, tag5, tag6);

        return view;
    }

    public void initWork() {
        context = getActivity();


        tag1Gridview = view.findViewById(R.id.tag1Gridview);
        tag2Gridview = view.findViewById(R.id.tag2Gridview);
        tag3Gridview = view.findViewById(R.id.tag3Gridview);
        tag4Gridview = view.findViewById(R.id.tag4Gridview);
        tag5Gridview = view.findViewById(R.id.tag5Gridview);
        tag6Gridview = view.findViewById(R.id.tag6Gridview);

        view_tag1Grid = view.findViewById(R.id.view_tag1Grid);
        view_tag2Grid = view.findViewById(R.id.view_tag2Grid);
        view_tag3Grid = view.findViewById(R.id.view_tag3Grid);
        view_tag4Grid = view.findViewById(R.id.view_tag4Grid);
        view_tag5Grid = view.findViewById(R.id.view_tag5Grid);
        view_tag6Grid = view.findViewById(R.id.view_tag6Grid);


        toolbar = view.findViewById(R.id.toolbar2);
        viewTagLists = view.findViewById(R.id.viewTagLists);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        getTagLists();
        addTagLists();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        viewTagLists.setLayoutManager(layoutManager);

        adapterOfTagLists = new AdapterTab2_tagLists(context, tagLists, onClickItem);
        viewTagLists.setAdapter(adapterOfTagLists);

//        MyListDecoration decoration = new MyListDecoration();
//        viewTagLists.addItemDecoration(decoration);
    }

    private void getTagLists() {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_FILE, 0);

        tag1 = settings.getString("tag1", "Landscape");
        tag2 = settings.getString("tag2", "Office");
        tag3 = settings.getString("tag3", "Milkyway");
        tag4 = settings.getString("tag4", "Yosemite");
        tag5 = settings.getString("tag5", "Roads");
        tag6 = settings.getString("tag6", "home");

    }

    private void addTagLists() {
        tagLists.add(tag1);
        tagLists.add(tag2);
        tagLists.add(tag3);
        tagLists.add(tag4);
        tagLists.add(tag5);
        tagLists.add(tag6);

        view_tag1Grid.setText(tag1);
        view_tag2Grid.setText(tag2);
        view_tag3Grid.setText(tag3);
        view_tag4Grid.setText(tag4);
        view_tag5Grid.setText(tag5);
        view_tag6Grid.setText(tag6);


    }

    private void getPhotosAsEachTag(String tag1, String tag2, String tag3, String tag4, String tag5, final String tag6) {

        Log.d("가져오는 중 ", "태그별 가져오는 중");

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //results[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag1Gridview);
                    Log.d("tag1", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag1 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag2).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //results[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag2Gridview);
                    Log.d("tag2", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag2 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag3).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //results[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag3Gridview);
                    Log.d("tag3", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag3 에서 사진 검색 오류", t.getMessage());

            }
        });
        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag4).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //results[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag4Gridview);
                    Log.d("tag4", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag4 에서 사진 검색 오류", t.getMessage());

            }
        });
        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag5).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //results[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag5Gridview);
                    Log.d("tag5", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag5 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag6).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //results[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag6Gridview);
                    Log.d("tag6", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag6 에서 사진 검색 오류", t.getMessage());

            }
        });


    }

    private void setPhotosAsEachTag() {

    }

    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    };


}
