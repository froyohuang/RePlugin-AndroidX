package com.qihoo360.replugin.sample.demo1.activity.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.qihoo360.replugin.sample.demo1.R;
import com.qihoo360.replugin.sample.demo1.fragment.DemoCodeFragment;

public class StandardFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_fragment);
        Fragment fragment = new DemoCodeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container2, fragment).commit();//添加Fragment到UI
    }
}
