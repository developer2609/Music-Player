package com.example.music

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.music.models.MyObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





    }

//    @SuppressLint("MissingSuperCall")
//    override fun onStop() {
//        if (MyObject.mediaPlayer!!.isPlaying){
//            MyObject.mediaPlayer?.stop()
//
//        }
//    }
}