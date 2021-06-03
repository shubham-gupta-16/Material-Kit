package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;


class _FlexToolbar extends LinearLayout {
    public _FlexToolbar(Context context) {
        this(context, null);
    }

    public _FlexToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    final TextView toolbarTitle;
    final ImageButton nav;
    final LinearLayout linearLayout;
    int actionBarSize;
    int menuTint, badgeBackgroundColor, badgeTextColor;
    Toolbar.OnMenuItemClickListener onMenuItemClickListener;

    public _FlexToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        actionBarSize = getContext().getResources().getDimensionPixelSize(R.dimen.actionBarHeight);
        menuTint = 9999;
        badgeBackgroundColor = Color.RED;
        badgeTextColor = Color.WHITE;

        setGravity(Gravity.CENTER);


        nav = new ImageButton(getContext());
        LinearLayout.LayoutParams navParams = new LinearLayout.LayoutParams(actionBarSize, actionBarSize);
        nav.setLayoutParams(navParams);
        nav.setBackgroundResource(R.drawable.kit_icon_ripple);
        nav.setVisibility(GONE);
        addView(nav);

        toolbarTitle = new TextView(getContext());
        toolbarTitle.setLines(1);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.weight = 1;
        toolbarTitle.setLayoutParams(titleParams);
        toolbarTitle.setTypeface(Typeface.DEFAULT_BOLD);
        addView(toolbarTitle);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, actionBarSize);

        linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        addView(linearLayout);
    }
}
