package com.modori.kwonkiseokee.AUto.ListOfPhotos;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.Util.ColumnQty;
import com.modori.kwonkiseokee.AUto.Util.NETWORKS;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;
import com.modori.kwonkiseokee.AUto.data.data.Results;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class ListOfPhotosView extends AppCompatActivity {

    View goBack, goInfo, setToGridview, mainLayout;
    TextView showGetPicCnt;
    int photoCnt = 1;
    boolean viewMode = true;
    boolean isSearchMode = false;

    ArrayList<Results> photoArrayList = new ArrayList<>();
    ListOfPhotosAdapter adapter;
    RecyclerView recyclerView;
    ListOfPhotosViewModel photosViewModel;

    View searchMode, questMessage;
    SearchView searchView;
    SwipeRefreshLayout refreshLayout;

    String tag;


    // Mode Searching


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
        String mode = intent.getExtras().getString("mode");
        tag = intent.getExtras().getString("photoID");

        isSearchMode = mode.equals("search");
        System.out.println("Searchmode is : " + isSearchMode);

        refreshLayout = findViewById(R.id.refreshLayout);
        goBack = findViewById(R.id.goBack);
        recyclerView = findViewById(R.id.recyclerView);
        goInfo = findViewById(R.id.goInfo_list);
        showGetPicCnt = findViewById(R.id.showPictGetCnt);
        setToGridview = findViewById(R.id.setToGridView);
        searchView = findViewById(R.id.searchView);
        searchMode = findViewById(R.id.searchMode);
        questMessage = findViewById(R.id.questMessage);
        mainLayout = findViewById(R.id.mainListLayout);

        goInfo.setOnClickListener(v -> makeDialog());
        goBack.setOnClickListener(v -> finish());
        setToGridview.setOnClickListener(v -> setGridView());


        photosViewModel = ViewModelProviders.of(ListOfPhotosView.this).get(ListOfPhotosViewModel.class);
        photosViewModel.init();


        if (isSearchMode) {
            searchView.setVisibility(View.VISIBLE);
            questMessage.setVisibility(View.VISIBLE);


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    System.out.println("진입");
                    if (searchView.getQuery() != null) {
                        tag = searchView.getQuery().toString();
                        System.out.println("불러오는중...");

                        photoArrayList.clear();
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();

                        }

                        getPhotoByKeyword(tag);
                        setUpRecyclerView(tag);
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        } else {
            searchMode.setVisibility(View.GONE);

            setUpRecyclerView(tag);
            getPhotoByKeyword(tag);

        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPhotoByKeyword(tag);
                refreshLayout.setRefreshing(false);
            }
        });



        getSupportActionBar().hide();

    }


    private void setGridView() {

        int mNoOfColumns = ColumnQty.calculateNoOfColumns(getApplicationContext(), 150);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), mNoOfColumns);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        if (!viewMode) {
            recyclerView.setLayoutManager(layoutManager);
            viewMode = true;
        } else {
            recyclerView.setLayoutManager(gridLayoutManager);
            viewMode = false;
        }
    }

    private void setUpRecyclerView(String tag) {
        if (adapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            viewMode = true;

            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (!recyclerView.canScrollVertically(-1)) {
                        //Log.i(TAG, "Top of list");
                    } else if (!recyclerView.canScrollVertically(1)) {
                        //Log.i(TAG, "End of list");
                        getPhotoByKeyword(tag);
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
                    getPhotoByKeyword(tag);
                }
            }

        } else {
            adapter.notifyDataSetChanged();
        }
    }


    public void getPhotoByKeyword(String tag) {

        photosViewModel.getListofPhotos(tag).observe(this, photosResponse -> {

            if (isKeywordAvail(photosResponse)) {
                questMessage.setVisibility(View.GONE);

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
            } else {
                System.out.println("불존재 키워드 오류");
                Snackbar.make(mainLayout, "존재하지 않는 키워드입니다.", Snackbar.LENGTH_SHORT).show();
            }


        });

    }

    private boolean isKeywordAvail(PhotoSearch response) {
        return !response.getTotal().equals("0");
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
