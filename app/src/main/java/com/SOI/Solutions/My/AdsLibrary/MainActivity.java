package com.SOI.Solutions.My.AdsLibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdmobHelper.iniSDkAdmob(this);

        AdmobHelper.loadAdmobAdaptiveBannerAd(this);
    }
}