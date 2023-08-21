package com.SOI.Solutions.My.AdsLibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxNetworkResponseInfo;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkUtils;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.List;

public class MyAdsHelper {
    public static ShimmerFrameLayout shimmerlayout,nativeshimmer;
    public static String TAG="Ads Helper";
    public static NativeAd nativeAd1;
    public static InterstitialAd interstitialAd1;
    public static MaxInterstitialAd interstitialAd;
    public static int retryAttempt;
    public static MaxNativeAdView nativeAdView;
    public static MaxAdView adView;
    public static FrameLayout       nativeAdLayout;

    public static MaxNativeAdLoader nativeAdLoader;

    public static MaxAd nativeAd;
    public static void iniSDkAdmob(Activity activity,String intersitai_id) {

        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(
                    @NonNull InitializationStatus initializationStatus) {
                Log.e(TAG, "admob sdk initialized success");
                loadAdmobInterstitialAd(activity,intersitai_id);
            }
        });

    }

    public static void iniSDkAdmobWithFacebook(Activity activity,String intersitai_id) {
        AudienceNetworkAds.initialize(activity);

        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(
                    @NonNull InitializationStatus initializationStatus) {
                Log.e(TAG, "admob sdk initialized success");
                loadAdmobInterstitialAd(activity,intersitai_id);
            }
        });

    }


    public static void loadAdmobAdaptiveBannerAd(Activity activity,String banner_id) {

        shimmerlayout=activity.findViewById(R.id.shimmerlayout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AdView adView = new AdView(activity);
                adView.setAdUnitId(banner_id);
                FrameLayout bannerLayout = activity.findViewById(R.id.framelayout);
                bannerLayout.addView(adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Log.e(TAG, "banner ad: loading failed " + loadAdError.getMessage());
                        Log.e(TAG, "banner ad: loading failed " + loadAdError.toString());
                        Log.e(TAG, "banner ad: loading failed " + loadAdError.getDomain());
                        Log.e(TAG, "banner ad: loading failed " + loadAdError.getResponseInfo());
                        Log.e(TAG, "banner ad: loading failed " + loadAdError.getCause());

                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        Log.e(TAG, "banner ad: laoded success");
                        shimmerlayout.stopShimmer();

                    }
                });
                AdSize adSize = getAdSize(activity);
                adView.setAdSize(adSize);
                adView.loadAd(adRequest);
            }
        }, 1000);
    }
    private static AdSize getAdSize(Activity activity) {
        // Step 2 - Determine the screen width (less decorations)
        // to use for the ad width.
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize
                (activity, adWidth);
    }
    private static void populateNativeAdView(NativeAd nativeAd,
                                             NativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline is guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null || nativeAd.getStarRating() < 3) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }

    //... load native ad
    public static void loadAndShowAdmobNativeAd(Activity activity,String native_id) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nativeshimmer=activity.findViewById(R.id.nativeshimmer);
                AdLoader adLoader = new AdLoader.Builder(activity,
                        native_id)
                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(@NonNull NativeAd
                                                                 nativeAd) {
                                nativeAd1 = nativeAd;
                                Log.e(TAG, "native ad: laoded success");
                                showAdmobNativeAd(activity);
                                nativeshimmer.stopShimmer();
                                nativeshimmer.setVisibility(View.GONE);
                            }
                        })
                        .withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError
                                                                 loadAdError) {
                                Log.e(TAG, "native ad : loading failed " +
                                        "" + loadAdError.toString());
                                Log.e(TAG, "native ad: loading failed " + loadAdError.toString());
                                Log.e(TAG, "native ad: loading failed " + loadAdError.getDomain());
                                Log.e(TAG, "native ad: loading failed " + loadAdError.getResponseInfo());
                                Log.e(TAG, "native ad: loading failed " + loadAdError.getCause());

                            }
                        }).build();
                adLoader.loadAd(new AdRequest.Builder().build());
            }
        }, 1000);
    }

    //... show native ad
    public static void showAdmobNativeAd(Activity activity) {
        if (nativeAd1 != null){
            FrameLayout nativeContainer = activity.findViewById(R.id.nativecontainer);
            NativeAdView nativeAdView = (NativeAdView) activity.getLayoutInflater()
                    .inflate(R.layout.mediation_native_ad, null);

            populateNativeAdView(nativeAd1, nativeAdView);
            nativeContainer.removeAllViews();
            nativeContainer.addView(nativeAdView);
            Log.e(TAG,"native ad: already loaded ,ad shown successfully "
                    +activity.getLocalClassName());
        }
        else  {
            // loadNativeAdAfterInternetConnected(activity, view);
            Log.e(TAG, "native ad: connected to internet, loading native..., "
                    +activity.getLocalClassName());
        }

    }

    public static void loadAdmobInterstitialAd(Activity activity,String intersitial_id) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                AdRequest adRequest = new AdRequest.Builder().build();

                InterstitialAd.load(activity,intersitial_id, adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                // The mInterstitialAd reference will be null until
                                // an ad is loaded.
                                interstitialAd1 = interstitialAd;
                                Log.e(TAG, "onAdLoaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error
                                Log.e(TAG, loadAdError.toString());
                                interstitialAd1 = null;
                            }
                        });
            }
        }, 1000);
    }

    public static void showAdmobIntersiitaAd(Activity activity, Intent intent){
        if (interstitialAd1!=null){

            activity.startActivity(new Intent(activity,IntersitialActivity.class)
                    .putExtra("intent_data", intent)
                    .putExtra("from", "admob"));
        }
        else {
            activity.startActivity(intent);
            Log.e("Intersiital Ad showing","intersital ad null");
        }

    }

    public static void inilizeApplovinSdk(Activity activity,String intersital_id,Boolean mediationdebugger){
        if (mediationdebugger) {
            AppLovinSdk.getInstance(activity).showMediationDebugger();
        }
        AppLovinSdk.getInstance(activity).setMediationProvider("max");

        AppLovinSdk.initializeSdk( activity, new AppLovinSdk.SdkInitializationListener()
        {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                Log.e(TAG,"AppLovin Sdk Intilized Successfully");
                loadApplovinintersitalad(activity,intersital_id);
            }
        } );

    }
    public static void inilizeApplovinSdkwithFacebook(Activity activity,String intersital_id,Boolean mediationdebugger){
        if (mediationdebugger) {
            AppLovinSdk.getInstance(activity).showMediationDebugger();
        }
        AppLovinSdk.getInstance(activity).setMediationProvider("max");
        AudienceNetworkAds.initialize(activity);
        AppLovinSdk.initializeSdk( activity, new AppLovinSdk.SdkInitializationListener()
        {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                Log.e(TAG,"AppLovin Sdk Intilized Successfully");
                loadApplovinintersitalad(activity,intersital_id);
            }
        } );

    }



    public static void loadApplovinintersitalad(Activity activity,String applovin_intersiital){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                interstitialAd = new MaxInterstitialAd( applovin_intersiital, activity );
                interstitialAd.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd maxAd) {
                        Log.e(TAG,"Intersiital ad: On laoded successfully");
                    }

                    @Override
                    public void onAdDisplayed(MaxAd maxAd) {
                        Log.e(TAG,"Intersiital ad: On Displayed successfully");
                    }

                    @Override
                    public void onAdHidden(MaxAd maxAd) {
                        Log.e(TAG,"Intersiital ad: On Hidden successfully");
                    }

                    @Override
                    public void onAdClicked(MaxAd maxAd) {
                        Log.e(TAG,"Intersiital ad: On Ad Clicked");
                    }

                    @Override
                    public void onAdLoadFailed(String s, MaxError maxError) {
                        Log.e(TAG,"Intersiital ad: On loaded Failed");
                        Log.e(TAG,"Intersiital ad: error code: "+maxError.getCode());
                        Log.e(TAG,"Intersiital ad: error message: "+maxError.getMessage());
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                        Log.e(TAG,"Intersiital ad: On Dispalayer Failed");
                    }
                });
                interstitialAd.loadAd();
            }
        },1000);

    }

    public static void loadandshowapplovinbanner(Activity activity,String banner_id){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adView = new MaxAdView( banner_id, activity );
                FrameLayout    frameLayout=activity.findViewById(R.id.framelayout);
                shimmerlayout=activity.findViewById(R.id.shimmerlayout);
                final boolean isTablet = AppLovinSdkUtils.isTablet( activity );
                final int heightPx = AppLovinSdkUtils.dpToPx( activity, isTablet ? 90 : 50 );


                adView.setListener(new MaxAdViewAdListener() {
                    @Override
                    public void onAdExpanded(MaxAd maxAd) {
                        Log.e(TAG,"Banner ad expended");
                    }

                    @Override
                    public void onAdCollapsed(MaxAd maxAd) {
                        Log.e(TAG,"Banner ad onAdCollapsed");
                    }

                    @Override
                    public void onAdLoaded(MaxAd maxAd) {
                        Log.e(TAG,"Banner ad onAdLoaded");
                        shimmerlayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdDisplayed(MaxAd maxAd) {
                        Log.e(TAG,"Banner ad onAdDisplayed");
                    }

                    @Override
                    public void onAdHidden(MaxAd maxAd) {
                        Log.e(TAG,"Banner ad onAdHidden");
                    }

                    @Override
                    public void onAdClicked(MaxAd maxAd) {
                        Log.e(TAG,"Banner ad onAdClicked");
                    }

                    @Override
                    public void onAdLoadFailed(String s, MaxError maxError) {
                        Log.e(TAG,"Banner ad onAdLoadFailed");
                        Log.e(TAG,"Banner maxError Message"+maxError.getMessage());
                        Log.e(TAG,"Banner maxError Code"+maxError.getCode());
                        List<MaxNetworkResponseInfo> networkinfos=maxError.getWaterfall().getNetworkResponses();
                        Log.e(TAG,"list size: "+networkinfos.size());

                        for (int i=0;i<=networkinfos.size()-1;i++) {
                            Log.e(TAG, "Waterfall info error" + networkinfos.get(i).getError());
                        }
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                        Log.e(TAG,"Banner ad onAdDisplayFailed");
                    }
                });


                // Banner width must match the screen to be fully functional.
                adView.setLayoutParams( new FrameLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, heightPx ) );

                frameLayout.addView(adView);

                // Load the first ad.
                adView.loadAd();
            }
        },1000);

    }

    public static void loadandshowapplovinnative(Activity activity,String applovin_native){
        nativeAdLayout = activity.findViewById(R.id.nativecontainer);
        nativeshimmer=activity.findViewById(R.id.nativeshimmer);
        MaxNativeAdViewBinder binder = new MaxNativeAdViewBinder.Builder( R.layout.native_custom_ad_view )
                .setTitleTextViewId( R.id.title_text_view )
                .setBodyTextViewId( R.id.body_text_view )
                .setAdvertiserTextViewId( R.id.advertiser_text_view )
                .setIconImageViewId( R.id.icon_image_view )
                .setMediaContentViewGroupId( R.id.media_view_container )
                .setOptionsContentViewGroupId( R.id.options_view )
                .setStarRatingContentViewGroupId( R.id.star_rating_view )
                .setCallToActionButtonId( R.id.cta_button )
                .build();
        nativeAdView = new MaxNativeAdView( binder, activity );

        nativeAdLoader = new MaxNativeAdLoader(applovin_native , activity );

        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener()
        {
            @Override
            public void onNativeAdLoaded(@Nullable final MaxNativeAdView nativeAdView, final MaxAd ad)
            {
                nativeshimmer.setVisibility(View.GONE);
                Log.e(TAG,"Native Ad Load successfully");
                // Cleanup any pre-existing native ad to prevent memory leaks.
                if ( nativeAd != null )
                {
                    nativeAdLoader.destroy( nativeAd );
                }

                // Save ad for cleanup.
                nativeAd = ad;

                // Add ad view to view.
                nativeAdLayout.removeAllViews();
                nativeAdLayout.addView( nativeAdView );
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error)
            {
                Log.e(TAG,"Native ad load failed");
                Log.e(TAG,"error code: "+error.getCode());
                Log.e(TAG,"error message: "+error.getMessage());
                nativeAdLoader.loadAd( nativeAdView );

            }

            @Override
            public void onNativeAdClicked(final MaxAd ad)
            {
                Log.e(TAG,"Native ad clicked");
            }

            @Override
            public void onNativeAdExpired(final MaxAd ad)
            {
                Log.e(TAG,"Native ad expired");
            }
        } );

        nativeAdLoader.loadAd( nativeAdView );

    }

    public static void showApplovinIntersiitaAd(Activity activity,String adunit, Intent intent){
        if (interstitialAd!=null){

            if (interstitialAd.isReady()) {
                activity.startActivity(new Intent(activity, IntersitialActivity.class)
                        .putExtra("intent_data", intent)
                        .putExtra("from", "applovin"));
            }
            else {
                Log.e(TAG,"intersital ad not ready");
                activity.startActivity(intent);
            }
        }
        else {
            loadApplovinintersitalad(activity,adunit);
            activity.startActivity(intent);
            Log.e(TAG,"intersital ad null");
        }

    }


}
