package com.ppandroid.readenglish;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ppandroid.readenglish.base.AC_Base;


public class MainActivity extends AC_Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEnablePullToBack(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
}
