package com.batman.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.batman.baselibrary.RouterConstants;
import com.batman.baselibrary.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityUtils.openActivity(RouterConstants.LOGIN_COMPONENT_LOGIN_PATH);
    }
}
