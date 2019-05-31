package com.modori.kwonkiseokee.AUto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class LicensePage extends AppCompatActivity implements View.OnClickListener {

    ImageView backBtn;
    ImageView githubBtn;

    TextView credit2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.license_page);


        backBtn = findViewById(R.id.backBtn);
        githubBtn = findViewById(R.id.githubBtn);
        credit2 = findViewById(R.id.credit2);

        githubBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        credit2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.backBtn:
                finish();
                break;

            case R.id.credit2:
                Intent credit2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Omie/walli-e"));
                startActivity(credit2);
                break;

            case R.id.githubBtn:
                Intent openGithub = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MODORIAPPS/AUto_ANDROID"));
                startActivity(openGithub);
                break;

            default:
                break;


        }
    }
}
