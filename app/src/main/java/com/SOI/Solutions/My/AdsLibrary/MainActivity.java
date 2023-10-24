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
                MyAdsHelper.showAdmobIntersiitaAd(MainActivity.this, getString(R.string.admob_intersitial),
                        new AdmobInterface() {
                            @Override
                            public void onAdresponse() {
                                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                            }
                        });
            }
        });

        MyAdsHelper.iniSDkAdmobWithFacebook(this, getResources().getString(R.string.admob_intersitial));

        MyAdsHelper.loadAdmobAdaptiveBannerAd(this, getResources().getString(R.string.admob_banner));
    }
}