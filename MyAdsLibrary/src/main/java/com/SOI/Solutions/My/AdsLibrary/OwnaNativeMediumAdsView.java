package com.SOI.Solutions.My.AdsLibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class OwnaNativeMediumAdsView extends FrameLayout {
    public OwnaNativeMediumAdsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public OwnaNativeMediumAdsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OwnaNativeMediumAdsView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.own_medium_native_layout, this);
    }
}
