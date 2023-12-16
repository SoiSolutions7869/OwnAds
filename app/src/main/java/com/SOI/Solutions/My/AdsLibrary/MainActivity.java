package com.SOI.Solutions.My.AdsLibrary;

import static com.SOI.Solutions.My.AdsLibrary.MyAdsHelper.showAdIfAvailable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.SOI.Solutions.My.AdsLibrary.CDR.ConsentManager;
import com.google.android.ump.FormError;

public class MainActivity extends AppCompatActivity {
    TextView helloworldtxt;
    ConsentManager consentManager;
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


        consentManager=ConsentManager.getInstance(this);
        consentManager.gatherConsent(this, "071A55712EDF0B764C0BAE62164EF2C2", new ConsentManager.OnConsentGatheringCompleteListener() {
            @Override
            public void consentGatheringComplete(FormError error) {
                if (error!=null) {
                    Log.d("consent", "consentGatheringComplete: Consent null");
                    return;
                }
                MyAdsHelper.iniSDkAdmobWithFacebook(MainActivity.this, getResources().getString(R.string.admob_intersitial));

                MyAdsHelper.loadAdmobAdaptiveBannerAd(MainActivity.this, getResources().getString(R.string.admob_banner));
                MyAdsHelper.loadAndShowAdmobMediumNativeAd(MainActivity.this, getResources().getString(R.string.admob_native));

                MyAdsHelper.loadAppOpenAd(MainActivity.this,"ca-app-pub-3940256099942544/3419835294");

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                showAdIfAvailable(MainActivity.this, new AppOpenAdManager.OnShowAdCompleteListener() {
//                    @Override
//                    public void onShowAdComplete() {
//                    }
//                }, "ca-app-pub-3940256099942544/3419835294");
            }
        },2000);

    }
}