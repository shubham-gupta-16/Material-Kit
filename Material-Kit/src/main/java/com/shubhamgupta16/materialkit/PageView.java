package com.shubhamgupta16.materialkit;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

public class PageView extends RelativeLayout {


    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    private Toolbar toolbar;
    private final RelativeLayout relLayout;
    private LinearLayout linearLayout;
    private final ImageView nav;
    private final TextView toolbarTitle;
    public int NAV_CLICK_NONE = 0;
    public int NAV_CLICK_FINISH = 1;
    public int TITLE_CENTER = 1;
    public int TITLE_START = 0;

    private void _setupLinearLayout() {
        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams llp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setPadding(0, 105, 0, 0);
        linearLayout.setId(android.R.id.content);
        linearLayout.setLayoutParams(llp);
        Log.wtf("tagtag", "init");
        addView(linearLayout);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        _setupLinearLayout();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PageView, defStyleAttr, defStyleRes);

        int actionBarSize;
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, outValue, true);
        actionBarSize = outValue.resourceId;

        float oneDP = UtilsKit.dpToPx(1, getContext());
        int navSize = (int) (oneDP * 26);


        relLayout = new RelativeLayout(getContext());
        relLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, actionBarSize));
        addView(relLayout);


        toolbar = new Toolbar(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, actionBarSize);
        params.addRule(CENTER_VERTICAL);
        toolbar.setLayoutParams(params);
        toolbar.setElevation(0);
        relLayout.addView(toolbar);
        nav = new ImageView(getContext());
        LayoutParams navParams = new LayoutParams(navSize, navSize);
        navParams.setMarginStart((int) (oneDP * 15));
        navParams.addRule(CENTER_VERTICAL);
        nav.setLayoutParams(navParams);
        relLayout.addView(nav);
        nav.setId(android.R.id.icon2);
        toolbarTitle = new TextView(getContext());
        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.setMarginStart((int) (oneDP * 15));
        titleParams.addRule(END_OF, nav.getId());
        titleParams.addRule(CENTER_IN_PARENT);
        toolbarTitle.setLayoutParams(titleParams);
        toolbarTitle.setTypeface(Typeface.DEFAULT_BOLD);
        relLayout.addView(toolbarTitle);


        setToolbarTitleFont(a.getResourceId(R.styleable.PageView_toolbarTitleFont, 0));
        setToolbarNavSize(a.getDimensionPixelSize(R.styleable.PageView_toolbarNavSize, navSize));
        setToolbarTitleSize(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.PageView_toolbarTitleSize, 20));
        setToolbarHeight(a.getDimensionPixelSize(R.styleable.PageView_toolbarHeight, actionBarSize));
        setToolbarTitle(a.getString(R.styleable.PageView_toolbarTitle));
        setToolbarNavIcon(a.getResourceId(R.styleable.PageView_toolbarNavIcon, android.R.color.transparent));
        setToolbarNavClick(a.getInt(R.styleable.PageView_toolbarNavClick, 1));
        setToolbarBackground(a.getResourceId(R.styleable.PageView_toolbarBackground, android.R.color.transparent));
        setToolbarTitlePosition(a.getInt(R.styleable.PageView_toolbarTitlePosition, TITLE_START));

        if (a.hasValue(R.styleable.PageView_toolbarTitleColor))
            setToolbarTitleColor(a.getColor(R.styleable.PageView_toolbarTitleColor, Color.WHITE));
        if (a.hasValue(R.styleable.PageView_toolbarNavTint))
            setToolbarNavTint(a.getColor(R.styleable.PageView_toolbarNavTint, Color.WHITE));

        if (a.hasValue(R.styleable.PageView_toolbarNavBackground))
            setToolbarNavBackground(a.getResourceId(R.styleable.PageView_toolbarNavBackground, android.R.color.transparent));
        else {
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true);
            setToolbarNavBackground(outValue.resourceId);
        }
        a.recycle();
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        linearLayout.addView(child);
        Log.wtf("tagtag", "4");
    }

    public void setToolbarTitlePosition(int position) {
        LayoutParams p = (LayoutParams) toolbarTitle.getLayoutParams();
        if (position == TITLE_CENTER)
            p.removeRule(END_OF);
        else
            p.addRule(END_OF, nav.getId());
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

    public void setToolbarHeight(int height) {
        relLayout.getLayoutParams().height = height;
        linearLayout.setPadding(0, height, 0, 0);

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
        if (navClick == NAV_CLICK_FINISH)
            nav.setOnClickListener(v -> ((Activity) getContext()).finish());
        else nav.setOnClickListener(null);
    }

    public void setToolbarTitle(String title) {
        toolbarTitle.setText(title);
    }

    public void setToolbarBackground(@DrawableRes int res) {
        relLayout.setBackgroundResource(res);
    }
}
