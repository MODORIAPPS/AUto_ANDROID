package com.modori.kwonkiseokee.AUto

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.show_photoonly.*
import java.lang.Error

class ShowPhotoOnly : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_photoonly)

        try{
            val intent = intent
            val url = intent!!.getStringExtra("photoUrl")
            Glide.with(this).load(url).into(photoView)
        }catch (e:Error){
            Toast.makeText(this,"죄송합니다. 오류가 발생했습니다.\n ${e.message}",Toast.LENGTH_SHORT).show()
            Log.e("ShowPhotoOnly", e.message!!)
            finish()
        }

    }
}