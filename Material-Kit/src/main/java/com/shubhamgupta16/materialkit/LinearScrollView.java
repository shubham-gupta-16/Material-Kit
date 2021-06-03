package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.widget.NestedScrollView;

public class LinearScrollView extends LinearLayout {

    public LinearLayout linearLayout;
    public NestedScrollView layout;

    public LinearScrollView(Context context) {
        this(context, null);
    }

    public LinearScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LinearScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        _setupScrollView();
    }

    private void _setupScrollView() {
        if (layout == null) {
            layout = new NestedScrollView(getContext());
            layout.setId(android.R.id.candidatesArea);
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            layout.addView(linearLayout);
            addView(layout);
        }
    }

    @Override
    public void addView(View child) {
        super.addView(child, child.getLayoutParams());
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0)
            linearLayout.addView(child, params);
        else
        super.addView(child, params);
    }
}
