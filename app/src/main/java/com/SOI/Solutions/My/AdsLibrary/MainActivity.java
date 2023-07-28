package com.SOI.Solutions.My.AdsLibrary;

import androidx.appcompat.app.AppCompatActivity;

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
                AdmobHelper.showAdmobIntersiitaAd(MainActivity.this,intent);
            }
        });

        AdmobHelper.iniSDkAdmob(this);

        AdmobHelper.loadAdmobAdaptiveBannerAd(this);
    }
}