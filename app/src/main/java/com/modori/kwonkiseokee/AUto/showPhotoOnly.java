package com.modori.kwonkiseokee.AUto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class showPhotoOnly extends AppCompatActivity {

    PhotoView photoView;
    String regularUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_photoonly);

        Intent intent = getIntent();
        regularUrl = intent.getStringExtra("photoUrl");
        photoView = findViewById(R.id.photoView);

        Glide.with(this).load(regularUrl).into(photoView);





    }
}
