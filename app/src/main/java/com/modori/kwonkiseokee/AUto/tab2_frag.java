package com.modori.kwonkiseokee.AUto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.modori.kwonkiseokee.AUto.RA.Tab2_tagListsRA;
import com.modori.kwonkiseokee.AUto.Util.TagTools;
import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;
import com.modori.kwonkiseokee.AUto.data.data.Results;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tab2_frag extends Fragment implements View.OnClickListener {

    private List<Results> results = new ArrayList<>();
    //Results[] photoUrl = new Results[5];

    public Context context;


    public static final String PREFS_FILE = "PrefsFile";


    //TagLists
    private List<String> tagLists;
    Tab2_tagListsRA adapterOfTagLists;

    private static String tag1 = null;
    private static String tag2 = null;
    private static String tag3 = null;
    private static String tag4 = null;
    private static String tag5 = null;
    private static String tag6 = null;


    //Widgets
    RecyclerView viewTagLists;
    View goInfo;

    // GridView
    View grid1;
    View grid2;
    View grid3;
    View grid4;
    View grid5;
    View grid6;

    //GridViews of tags
    private ImageView tag1Gridview;
    private ImageView tag2Gridview;
    private ImageView tag3Gridview;
    private ImageView tag4Gridview;
    private ImageView tag5Gridview;
    private ImageView tag6Gridview;

    // TextView of GridViews, tags..
    @BindView(R.id.view_tag1Grid)
    TextView view_tag1Grid;

    @BindView(R.id.view_tag2Grid)
    TextView view_tag2Grid;

    @BindView(R.id.view_tag3Grid)
    TextView view_tag3Grid;

    @BindView(R.id.view_tag4Grid)
    TextView view_tag4Grid;

    @BindView(R.id.view_tag5Grid)
    TextView view_tag5Grid;

    @BindView(R.id.view_tag6Grid)
    TextView view_tag6Grid;


    private Toolbar toolbar;

    View view;

    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_frag, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);

        tagLists = new ArrayList<>();
        initWork();
        getPhotosAsEachTag(tag1, tag2, tag3, tag4, tag5, tag6);
        return view;
    }

    private void initWork() {

        intent = new Intent(getActivity(), ListsOfPhotos.class);

        goInfo = view.findViewById(R.id.goInfo);

        tag1Gridview = view.findViewById(R.id.tag1Gridview);
        tag2Gridview = view.findViewById(R.id.tag2Gridview);
        tag3Gridview = view.findViewById(R.id.tag3Gridview);
        tag4Gridview = view.findViewById(R.id.tag4Gridview);
        tag5Gridview = view.findViewById(R.id.tag5Gridview);
        tag6Gridview = view.findViewById(R.id.tag6Gridview);

        YoYo.with(Techniques.FadeIn).playOn(tag1Gridview);
        YoYo.with(Techniques.FadeIn).playOn(tag2Gridview);
        YoYo.with(Techniques.FadeIn).playOn(tag3Gridview);
        YoYo.with(Techniques.FadeIn).playOn(tag4Gridview);
        YoYo.with(Techniques.FadeIn).playOn(tag5Gridview);
        YoYo.with(Techniques.FadeIn).playOn(tag6Gridview);

        grid1 = view.findViewById(R.id.grid1);
        grid2 = view.findViewById(R.id.grid2);
        grid3 = view.findViewById(R.id.grid3);
        grid4 = view.findViewById(R.id.grid4);
        grid5 = view.findViewById(R.id.grid5);
        grid6 = view.findViewById(R.id.grid6);


        toolbar = view.findViewById(R.id.toolbar2);
        viewTagLists = view.findViewById(R.id.viewTagLists);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        setTagListsView(context);


        grid1.setOnClickListener(this);
        grid2.setOnClickListener(this);
        grid3.setOnClickListener(this);
        grid4.setOnClickListener(this);
        grid5.setOnClickListener(this);
        grid6.setOnClickListener(this);
        goInfo.setOnClickListener(this);
    }

    public void setTagListsView(Context context) {
        getTagLists(context);
        addTagLists();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        viewTagLists.setLayoutManager(layoutManager);

        adapterOfTagLists = new Tab2_tagListsRA(context, tagLists);
        viewTagLists.setAdapter(adapterOfTagLists);
    }

    //TEST

    private void getTagLists(Context context) {
        tagLists = TagTools.getTagLists(context);

        tag1 = tagLists.get(0);
        tag2 = tagLists.get(1);
        tag3 = tagLists.get(2);
        tag4 = tagLists.get(3);
        tag5 = tagLists.get(4);
        tag6 = tagLists.get(5);

        Log.d("태그1 정보", tag1 + "");


    }

    private void addTagLists() {
//        tagLists.add(tag1);
//        tagLists.add(tag2);
//        tagLists.add(tag3);
//        tagLists.add(tag4);
//        tagLists.add(tag5);
//        tagLists.add(tag6);

        view_tag1Grid.setText(tag1);
        view_tag2Grid.setText(tag2);
        view_tag3Grid.setText(tag3);
        view_tag4Grid.setText(tag4);
        view_tag5Grid.setText(tag5);
        view_tag6Grid.setText(tag6);


    }


    private void getPhotosAsEachTag(String tag1, String tag2, String tag3, String tag4, String tag5, final String tag6) {

        Log.d("가져오는 중 ", "태그별 가져오는 중");

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag1, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    YoYo.with(Techniques.FadeIn).playOn(tag1Gridview);

                        Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag1Gridview);
                        Log.d("tag1", "잘 가져옴");


                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag1 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag2, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();
                    YoYo.with(Techniques.FadeIn).playOn(tag2Gridview);

                    Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag2Gridview);
                    Log.d("tag2", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag2 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag3, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    YoYo.with(Techniques.FadeIn).playOn(tag3Gridview);

                    Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag3Gridview);
                    Log.d("tag3", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag3 에서 사진 검색 오류", t.getMessage());

            }
        });
        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag4, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    YoYo.with(Techniques.FadeIn).playOn(tag4Gridview);

                    Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag4Gridview);
                    Log.d("tag4", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag4 에서 사진 검색 오류", t.getMessage());

            }
        });
        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag5, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    YoYo.with(Techniques.FadeIn).playOn(tag5Gridview);

                    Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag5Gridview);
                    Log.d("tag5", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag5 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag6, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    YoYo.with(Techniques.FadeIn).playOn(tag6Gridview);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.grid1:
                intent.putExtra("photoID", tag1);
                startActivity(intent);
                break;

            case R.id.grid2:
                intent.putExtra("photoID", tag2);
                startActivity(intent);
                break;

            case R.id.grid3:
                intent.putExtra("photoID", tag3);
                startActivity(intent);
                break;

            case R.id.grid4:
                intent.putExtra("photoID", tag4);
                startActivity(intent);
                break;

            case R.id.grid5:
                intent.putExtra("photoID", tag5);
                startActivity(intent);
                break;

            case R.id.grid6:
                intent.putExtra("photoID", tag6);
                startActivity(intent);
                break;

            case R.id.goInfo:
                //show Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("이 페이지 설명");
                builder.setMessage("키워드를 눌러 원하는 키워드를 입력하여 변경할 수 있으며 변경된 키워드를 통해 불러온 사진이 아래 표시됩니다. 최대 6개의 키워드를 동시에 불러올 수 있습니다.");
                builder.setPositiveButton("알겠습니다.",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                builder.show();

                break;
        }
    }
}
