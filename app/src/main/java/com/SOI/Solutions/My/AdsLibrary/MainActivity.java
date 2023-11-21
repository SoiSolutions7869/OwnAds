package com.SOI.Solutions.My.AdsLibrary;

import static com.SOI.Solutions.My.AdsLibrary.MyAdsHelper.showAdIfAvailable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        MyAdsHelper.loadAndShowAdmobMediumNativeAd(this, getResources().getString(R.string.admob_native));

        MyAdsHelper.loadAppOpenAd(this,"ca-app-pub-3940256099942544/3419835294");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showAdIfAvailable(MainActivity.this, new AppOpenAdManager.OnShowAdCompleteListener() {
                    @Override
                    public void onShowAdComplete() {
                    }
                }, "ca-app-pub-3940256099942544/3419835294");
            }
        },2000);

    }
}