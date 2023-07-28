package com.SOI.Solutions.My.AdsLibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class OwnBannerAdsView extends FrameLayout {
    public OwnBannerAdsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public OwnBannerAdsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OwnBannerAdsView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.own_banner_layout, this);
    }
}
