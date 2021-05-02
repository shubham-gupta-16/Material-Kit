package com.shubhamgupta16.materialkit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

class FlexToolbar extends RelativeLayout {
    public FlexToolbar(Context context) {
        this(context, null);
    }

    public FlexToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    private final ImageView nav;
    private final TextView toolbarTitle;

    public FlexToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int actionBarSize;
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, outValue, true);
        actionBarSize = outValue.resourceId;

        float oneDP = UtilsKit.dpToPx(1, getContext());
        int navSize = (int) (oneDP * 30);

        Toolbar toolbar = new Toolbar(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, actionBarSize);
        params.addRule(CENTER_VERTICAL);
        toolbar.setLayoutParams(params);
        addView(toolbar);

        nav = new ImageView(getContext());
        LayoutParams navParams = new LayoutParams(navSize, navSize);
        navParams.setMarginStart((int) (oneDP * 15));
        navParams.setMarginEnd((int) (oneDP * 10));
        int p = (int) (oneDP * 3);
        nav.setPadding(p,p,p,p);
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

    public void setToolbarTitlePosition(int position) {
        LayoutParams p = (LayoutParams) toolbarTitle.getLayoutParams();
        if (position == MaterialConstants.TOOLBAR_TITLE_CENTER) {
            p.removeRule(END_OF);
            p.addRule(CENTER_HORIZONTAL);
        } else {
            p.addRule(END_OF, nav.getId());
            p.removeRule(CENTER_HORIZONTAL);
        }
        toolbarTitle.setLayoutParams(p);
    }

    public void setToolbarTitleColor(@ColorInt int color) {
        toolbarTitle.setTextColor(color);
    }

    public void setToolbarNavTint(@ColorInt int color) {
        nav.setColorFilter(color);
    }

    public void setToolbarTitleFont(@FontRes int font) {
        if (font != 0) {
            new Handler().post(() -> {
                Typeface typeface = ResourcesCompat.getFont(getContext(), font);
                toolbarTitle.setTypeface(typeface);
            });
        }
    }

    public void setToolbarNavSize(int size) {
        nav.getLayoutParams().height = size;
        nav.getLayoutParams().width = size;
    }

    public void setToolbarNavPadding(int padding) {
        nav.setPadding(padding, padding, padding, padding);

    }

    public void setToolbarTitleSize(int unit, float size) {
        toolbarTitle.setTextSize(unit, size);
    }

    public void setToolbarNavIcon(@DrawableRes int res) {
        if (res == android.R.color.transparent)
            nav.setVisibility(GONE);
        else
            nav.setVisibility(VISIBLE);
        nav.setImageResource(res);
    }

    public void setToolbarNavBackground(@DrawableRes int res) {
        nav.setBackgroundResource(res);
    }

    public void setToolbarNavClick(int navClick) {
        if (navClick == MaterialConstants.TOOLBAR_NAV_CLICK_FINISH)
            nav.setOnClickListener(v -> ((Activity) getContext()).finish());
        else nav.setOnClickListener(null);
    }

    public void setToolbarTitle(String title) {
        toolbarTitle.setText(title);
    }


}
