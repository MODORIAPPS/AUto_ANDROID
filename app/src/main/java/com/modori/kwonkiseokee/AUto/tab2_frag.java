package com.modori.kwonkiseokee.AUto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.modori.kwonkiseokee.AUto.RecyclerViewAdapter.AdapterTab2_tagLists;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class tab2_frag extends Fragment {

    Context context;


    public static final String PREFS_FILE = "PrefsFile";

    //TagLists
    ArrayList<String> tagLists;
    AdapterTab2_tagLists adapterOfTagLists;

    public static String tag1 = null;
    public static String tag2 = null;
    public static String tag3 = null;
    public static String tag4 = null;
    public static String tag5 = null;
    public static String tag6 = null;


    //Widgets
    RecyclerView viewTagLists;

    //GridViews of tags
    ImageView tag1Gridview;
    ImageView tag2Gridview;
    ImageView tag3Gridview;
    ImageView tag4Gridview;
    ImageView tag5Gridview;
    ImageView tag6Gridview;


    Toolbar toolbar;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_frag, container, false);
        tagLists = new ArrayList<>();

        initWork();


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
        tag2 = settings.getString("tag2", "Office Life");
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


    }

    private void getPhotosAsEachTag() {

//        unsplash.searchPhotos(tag1, new Unsplash.OnSearchCompleteListener() {
//            @Override
//            public void onComplete(SearchResults results) {
//                Log.d("Photos", "Total Results Found " + results.getTotal());
//                List<Photo> photos = results.getResults();
//                Log.d("불러온 값의 상태", String.valueOf(photos));
//                Glide.with(context).load(photos.get(0)).into(tag1Gridview);
//            }
//
//
//            @Override
//            public void onError(String error) {
//                Log.d("Unsplash", error);
//            }
//        });

    }

    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    };


}
