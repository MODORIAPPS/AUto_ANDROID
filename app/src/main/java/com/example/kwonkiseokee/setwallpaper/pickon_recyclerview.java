package com.example.kwonkiseokee.setwallpaper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class pickon_recyclerview extends AppCompatActivity {

    RecyclerView recyclerView;
    View mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickon_recyclerview);

        recyclerView = findViewById(R.id.recyclerview);
        mainlayout = findViewById(R.id.mainlayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Integer> listResId = Arrays.asList(
                R.drawable.sample_1,
                R.drawable.sample_2,
                R.drawable.sample_3,
                R.drawable.sample_4,
                R.drawable.sample_5,
                R.drawable.sample_6
        );

        List<dataModel> items = new ArrayList<>();

        for(int i=0; i<6; i++){
            items.add(new dataModel("sample_"+ i, ""+ i, listResId.get(i)));
        }

        RecyclerView.Adapter adapter = new RecyclerviewAdapter(items);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
