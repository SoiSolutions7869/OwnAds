package com.SOI.Solutions.My.AdsLibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class OwnaNativeAdsView extends FrameLayout {
    public OwnaNativeAdsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public OwnaNativeAdsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OwnaNativeAdsView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.own_native_layout, this);
    }
}
