package com.SOI.Solutions.My.AdsLibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView helloworldtxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloworldtxt=findViewById(R.id.helloworldtxt);

        helloworldtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,SecondActivity.class);
                MyAdsHelper.showAdmobIntersiitaAd(MainActivity.this,intent);
            }
        });

        MyAdsHelper.iniSDkAdmob(this, getResources().getString(R.string.admob_intersitial));

        MyAdsHelper.loadAdmobAdaptiveBannerAd(this, getResources().getString(R.string.admob_banner));
    }
}