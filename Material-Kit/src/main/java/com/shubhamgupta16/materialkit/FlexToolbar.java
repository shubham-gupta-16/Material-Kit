package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
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

    final ImageView nav;
    final TextView toolbarTitle;
    final Toolbar toolbar;

    public FlexToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        float oneDP = UtilsKit.dpToPx(1, getContext());
        int navSize = (int) (oneDP * 30);

        toolbar = new Toolbar(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        toolbar.setLayoutParams(params);
        addView(toolbar);

        nav = new ImageView(getContext());
        LayoutParams navParams = new LayoutParams(navSize, navSize);
        navParams.setMarginStart((int) (oneDP * 15));
        navParams.setMarginEnd((int) (oneDP * 10));
        int p = (int) (oneDP * 3);
        nav.setPadding(p, p, p, p);
        navParams.addRule(CENTER_VERTICAL);
        nav.setLayoutParams(navParams);
        nav.setId(android.R.id.icon2);
        addView(nav);


        toolbarTitle = new TextView(getContext());
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.setMarginStart((int) (oneDP * 15));
        titleParams.addRule(END_OF, nav.getId());
        titleParams.addRule(CENTER_VERTICAL);
        toolbarTitle.setLayoutParams(titleParams);
        toolbarTitle.setTypeface(Typeface.DEFAULT_BOLD);
        addView(toolbarTitle);
    }
}
