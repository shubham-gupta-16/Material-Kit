package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

class FlexToolbar extends RelativeLayout {
    public FlexToolbar(Context context) {
        this(context, null);
    }

    public FlexToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    final TextView toolbarTitle, toolbarTitleCenter;
    final Toolbar toolbar;

    public FlexToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        float oneDP = UtilsKit.dpToPx(1, getContext());

        toolbar = new Toolbar(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        toolbar.setLayoutParams(params);
        addView(toolbar);

        toolbarTitle = new TextView(getContext());
        toolbarTitleCenter = new TextView(getContext());
        toolbarTitle.setLines(1);
        toolbarTitleCenter.setLines(1);
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        toolbarTitleCenter.setLayoutParams(titleParams);
        toolbarTitleCenter.setGravity(Gravity.CENTER);
        toolbarTitle.setLayoutParams(titleParams);
        toolbarTitle.setTypeface(Typeface.DEFAULT_BOLD);
        toolbarTitleCenter.setTypeface(Typeface.DEFAULT_BOLD);
        toolbar.addView(toolbarTitle);
        addView(toolbarTitleCenter);
    }
}
