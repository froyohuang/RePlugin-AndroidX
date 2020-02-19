package com.demo.testrpandroidx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


import android.os.Bundle;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(this instanceof AppCompatActivity){
//            ((TextView)findViewById(R.id.text)).setText("i am androidx.appcompat.app.AppCompatActivity v3");
//        }
    }
}
