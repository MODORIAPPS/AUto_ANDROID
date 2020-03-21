package com.modori.kwonkiseokee.AUto.Tab2_frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.modori.kwonkiseokee.AUto.ListOfPhotos.ListOfPhotoView;
import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.RetrofitService.RetrofitService;
import com.modori.kwonkiseokee.AUto.RetrofitService.api.ApiClient;
import com.modori.kwonkiseokee.AUto.RetrofitService.api.SearchApi;
import com.modori.kwonkiseokee.AUto.Util.NETWORKS;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;
import com.modori.kwonkiseokee.AUto.data.data.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tab2_frag extends Fragment implements View.OnClickListener {

    private List<Results> results = new ArrayList<>();
    public Context context;

    //Widgets
    private RecyclerView viewTagLists;
    private View inputKeyword;

    //GridViews of tags
    private ImageView tag1Gridview;
    private ImageView tag2Gridview;
    private ImageView tag3Gridview;
    private ImageView tag4Gridview;
    private ImageView tag5Gridview;
    private ImageView tag6Gridview;

    // TextView of GridViews, tags..
    private TextView view_tag1Grid;
    private TextView view_tag2Grid;
    private TextView view_tag3Grid;
    private TextView view_tag4Grid;
    private TextView view_tag5Grid;
    private TextView view_tag6Grid;

    private View view;

    private Intent intent;
    private CircularProgressDrawable circularProgressDrawable;

    private TagViewModel tagViewModel;
    private TagListsAdapter adapter;

    private int counter = 0;
    private ImageView[] gridImages = new ImageView[]{tag1Gridview, tag2Gridview, tag3Gridview, tag4Gridview, tag5Gridview, tag6Gridview};
    private TextView[] gridTexts = new TextView[]{view_tag1Grid, view_tag2Grid, view_tag3Grid, view_tag4Grid, view_tag5Grid, view_tag6Grid};
    //TagLists
    private List<Tag> tagLists;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_frag, container, false);
        context = getActivity();

        //adapter = new TagListsAdapter(context);
        int check = 0;
        try {
            check = new TagRoomDatabase.CheckAsyncTask(TagRoomDatabase.getDatabase(context)).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tagViewModel = ViewModelProviders.of(tab2_frag.this).get(TagViewModel.class);
        if (check == 0) {
            new TagRoomDatabase.CheckDbAsync(TagRoomDatabase.getDatabase(context)).execute();

        }
        tagViewModel.getTagLists().observe(this, words -> {


            if (words.size() == 6) {
                System.out.println(words.size());
                adapter = new TagListsAdapter(context, words, tagViewModel);
                tagLists = words;
                addToTextTagLists(words);
                adapter.setTagLists(words);
                viewTagLists.setAdapter(adapter);
                getPhotosAsEachTag(words);

            }


        });


        initWork();

        circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        String ads_app = getResources().getString(R.string.ads_app);
        MobileAds.initialize(context, ads_app);
        AdView adView = view.findViewById(R.id.adView_frag1);
        // 잠시 막아놓음
        //adView.loadAd(adRequest);

        //tagLists = new ArrayList<>();
        netWorkCheck();
        //getPhotosAsEachTag(tag1, tag2, tag3, tag4, tag5, tag6);

        //getPhotoAsEachTag(tagLists);


        return view;
    }

    private boolean netWorkCheck() {
        if (NETWORKS.getNetWorkType(context) == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.noNetworkErrorTitle));
            builder.setMessage(getString(R.string.noNetworkErrorContent));
            builder.setPositiveButton(R.string.tab2_DialogOk,
                    (dialog, which) -> {

                    });

            builder.show();
            return false;

        }

        return true;
    }

    private void initWork() {

        intent = new Intent(getActivity(), ListOfPhotoView.class);

        View goReset = view.findViewById(R.id.goReset);

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

        inputKeyword = view.findViewById(R.id.inputKeyword);
        // GridView
        View grid1 = view.findViewById(R.id.grid1);
        View grid2 = view.findViewById(R.id.grid2);
        View grid3 = view.findViewById(R.id.grid3);
        View grid4 = view.findViewById(R.id.grid4);
        View grid5 = view.findViewById(R.id.grid5);
        View grid6 = view.findViewById(R.id.grid6);


        Toolbar toolbar = view.findViewById(R.id.toolbar2);
        viewTagLists = view.findViewById(R.id.viewTagLists);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        grid1.setOnClickListener(this);
        grid2.setOnClickListener(this);
        grid3.setOnClickListener(this);
        grid4.setOnClickListener(this);
        grid5.setOnClickListener(this);
        grid6.setOnClickListener(this);
        goReset.setOnClickListener(this);
        inputKeyword.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        viewTagLists.setLayoutManager(layoutManager);

    }


    private void addToTextTagLists(List<Tag> tagList) {

        view_tag1Grid.setText(tagList.get(0).getTag());
        view_tag2Grid.setText(tagList.get(1).getTag());
        view_tag3Grid.setText(tagList.get(2).getTag());
        view_tag4Grid.setText(tagList.get(3).getTag());
        view_tag5Grid.setText(tagList.get(4).getTag());
        view_tag6Grid.setText(tagList.get(5).getTag());
    }


    private void getPhotosAsEachTag(List<Tag> tagList) {

        Log.d("가져오는 중 ", "태그별 가져오는 중");

        RetrofitService.createService(SearchApi.class).getPhotobyKeyward(tagList.get(0).getTag(), 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();
                    setImageView(results.get(0).getUrls().getSmall(), tag1Gridview);

                    Glide.with(context).load(results.get(0).getUrls().getSmall())
                            .placeholder(circularProgressDrawable).into(tag1Gridview);

                    Log.d("tag1", "잘 가져옴");


                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag1 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tagList.get(1).getTag(), 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    //setImageView(results.get(0).getUrls().getSmall(), tag2Gridview);
                    Glide.with(context).load(results.get(0).getUrls().getSmall())
                            .placeholder(circularProgressDrawable)
                            .into(tag2Gridview);
                    Log.d("tag2", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag2 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tagList.get(2).getTag(), 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    //setImageView(results.get(0).getUrls().getSmall(), tag3Gridview);
                    Glide.with(context).load(results.get(0).getUrls().getSmall())
                            .placeholder(circularProgressDrawable).into(tag3Gridview);
                    Log.d("tag3", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag3 에서 사진 검색 오류", t.getMessage());

            }
        });
        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tagList.get(3).getTag(), 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    //setImageView(results.get(0).getUrls().getSmall(), tag4Gridview);
                    Glide.with(context).load(results.get(0).getUrls().getSmall())
                            .placeholder(circularProgressDrawable).into(tag4Gridview);
                    Log.d("tag4", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag4 에서 사진 검색 오류", t.getMessage());

            }
        });
        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tagList.get(4).getTag(), 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    //setImageView(results.get(0).getUrls().getSmall(), tag5Gridview);
                    Glide.with(context).load(results.get(0).getUrls().getSmall())
                            .placeholder(circularProgressDrawable).into(tag5Gridview);
                    Log.d("tag5", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag5 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tagList.get(5).getTag(), 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    //setImageView(results.get(0).getUrls().getSmall(), tag6Gridview);
                    Glide.with(context).load(results.get(0).getUrls().getSmall())
                            .placeholder(circularProgressDrawable).into(tag6Gridview);
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


        if (v.getId() == R.id.goReset) {
            switch (v.getId()) {
                case R.id.goReset:
                    AlertDialog.Builder resetDialog = new AlertDialog.Builder(context);
                    resetDialog.setTitle(getString(R.string.tab2_resetTitle));
                    resetDialog.setMessage(getString(R.string.tab2_resetContent));
                    resetDialog.setPositiveButton(R.string.tab2_DialogOk,
                            (dialog, which) -> {
                                new TagRoomDatabase.CheckDbAsync(TagRoomDatabase.getDatabase(context)).execute();

                                //setTagListsView(context);
                            }).setNegativeButton(R.string.saveDialogNega,
                            ((dialog, which) -> {

                            }));

                    resetDialog.show();


                    break;
            }
        } else {
            if (netWorkCheck()) {
                switch (v.getId()) {
                    case R.id.inputKeyword:
                        intent = new Intent(getActivity(), ListOfPhotoView.class);
                        intent.putExtra("mode", "search");
                        startActivity(intent);

                        break;
                    case R.id.grid1:
                        intent.putExtra("photoID", tagLists.get(0).getTag());
                        intent.putExtra("mode", "none");

                        startActivity(intent);
                        break;

                    case R.id.grid2:
                        intent.putExtra("photoID", tagLists.get(1).getTag());
                        intent.putExtra("mode", "none");

                        startActivity(intent);
                        break;

                    case R.id.grid3:
                        intent.putExtra("photoID", tagLists.get(2).getTag());
                        intent.putExtra("mode", "none");

                        startActivity(intent);
                        break;

                    case R.id.grid4:
                        intent.putExtra("photoID", tagLists.get(3).getTag());
                        intent.putExtra("mode", "none");

                        startActivity(intent);
                        break;

                    case R.id.grid5:
                        intent.putExtra("photoID", tagLists.get(4).getTag());
                        intent.putExtra("mode", "none");

                        startActivity(intent);
                        break;

                    case R.id.grid6:
                        intent.putExtra("photoID", tagLists.get(5).getTag());
                        intent.putExtra("mode", "none");

                        startActivity(intent);
                        break;


                }
            }


        }

    }


    private void setImageView(String photoUrl, ImageView target) {

        if (getActivity() != null) {
            if (!getActivity().isFinishing()) {
                Glide.with(context).load(photoUrl).into(target);
            }
        }


    }


}
