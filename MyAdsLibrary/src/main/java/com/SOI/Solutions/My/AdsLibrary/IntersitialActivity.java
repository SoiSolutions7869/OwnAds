package com.SOI.Solutions.My.AdsLibrary;



import static com.SOI.Solutions.My.AdsLibrary.MyAdsHelper.interstitialAd;
import static com.SOI.Solutions.My.AdsLibrary.MyAdsHelper.interstitialAd1;
import static com.SOI.Solutions.My.AdsLibrary.MyAdsHelper.retryAttempt;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;

import java.util.concurrent.TimeUnit;

public class IntersitialActivity extends AppCompatActivity {
    String TAG="Intersital Showing";
    String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intersitial);
        // Retrieve the intent passed from Activity1
        from=getIntent().getStringExtra("from");
       /* if (receivedIntent != null) {
            // Retrieve the Intent for Activity2
            Intent intentForActivity2 = receivedIntent.getParcelableExtra("intent_data");

//            if (intentForActivity2 != null) {
                // Start Activity2 using the intent obtained from Activity1
              //  startActivity(intentForActivity2);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (from.equals("admob")){
                            showIntersiitalAd(intentForActivity2);
                        }
                        else {
                            showApplovinIntersitalAd(intentForActivity2);
                        }
                        finish();
                    }
                },1000);
            }*/
        AdmobInterface admobInterface = MyAdsHelper.callbackHelper.getAdmobInterface();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (from.equals("admob")){
                    showIntersiitalAd(admobInterface);
                }
                else {
                    showApplovinIntersitalAd(admobInterface);
                }
            }
        },1000);


    }


    void showIntersiitalAd(AdmobInterface intent){
        interstitialAd1.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.");
                interstitialAd1 = null;

//                if (intent!=null) {
//                    startActivity(intent);
//                }
                finish();
                intent.onAdresponse();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.");
                interstitialAd1 = null;
                finish();

                intent.onAdresponse();

//                if (intent!=null) {
//                    startActivity(intent);
//                }
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.");
            }
        });

        interstitialAd1.show(this);

    }

    void showApplovinIntersitalAd(AdmobInterface intent){
        interstitialAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd maxAd) {
                // Reset retry attempt
                retryAttempt = 0;
                Log.e(TAG,"Intersitail ad load succesffuly");
            }

            @Override
            public void onAdDisplayed(MaxAd maxAd) {
                Log.e(TAG,"Intersitail ad displayed");
            }

            @Override
            public void onAdHidden(MaxAd maxAd) {
                Log.e(TAG,"Intersitail ad on hidden");
//                if (intent!=null) {
//                    startActivity(intent);
//                }
                //loadintersitalad(activity);
                finish();
                intent.onAdresponse();
            }

            @Override
            public void onAdClicked(MaxAd maxAd) {
                Log.e(TAG,"Intersitail ad clicked");
            }

            @Override
            public void onAdLoadFailed(String s, MaxError maxError) {
                // Interstitial ad failed to load. We recommend retrying with exponentially higher delays up to a maximum delay (in this case 64 seconds).
//                if (intent!=null) {
//                    startActivity(intent);
//                }
                intent.onAdresponse();
                finish();

                retryAttempt++;
                long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        interstitialAd.loadAd();
                    }
                }, delayMillis );
                Log.e(TAG,"Intersitail ad load failed");
                Log.e(TAG,"Intersitail ad message"+maxError.getMessage());
                Log.e(TAG,"Intersitail ad code: "+maxError.getCode());
            }

            @Override
            public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                Log.e(TAG,"Intersitail ad display failed");
                //loadintersitalad(activity);
//                if (intent!=null) {
//                    startActivity(intent);
//                }
                finish();
                intent.onAdresponse();

            }

        });
        interstitialAd.showAd();
    }
}