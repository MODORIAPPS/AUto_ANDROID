package com.modori.kwonkiseokee.AUto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.modori.kwonkiseokee.AUto.Tab1_frag.AlbumDTO;

public class AlbumDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);

        AlbumDTO albumDTO = (AlbumDTO) getIntent().getSerializableExtra("AlbumDTO");


    }
}
