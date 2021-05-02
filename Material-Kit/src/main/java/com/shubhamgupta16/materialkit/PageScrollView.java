package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;

public class PageScrollView extends PageView {

    public LinearLayout linearLayout;
    public ScrollView layout;

    public PageScrollView(Context context) {
        this(context, null);
    }

    public PageScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PageScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        _setupScrollView(null, null);
    }

    private void _setupScrollView(View child, ViewGroup.LayoutParams params) {
        if (layout == null) {
            layout = new ScrollView(getContext());
            layout.setId(android.R.id.candidatesArea);
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            layout.addView(linearLayout);
            addView(layout);

            if (child != null)
                linearLayout.addView(child, params);
        }
    }

    @Override
    public void addView(View child) {
        super.addView(child, child.getLayoutParams());
        Log.d("tagtag", "here");
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0)
            linearLayout.addView(child, params);
        else
        super.addView(child, params);
    }
}
