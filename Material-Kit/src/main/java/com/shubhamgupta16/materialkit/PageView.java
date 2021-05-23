package com.shubhamgupta16.materialkit;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.MenuRes;
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

    private FlexToolbar flexToolbar;
    private RelativeLayout relativeLayout;
    private final TypedArray a;

    private void _setupLinearLayout() {
        flexToolbar = new FlexToolbar(getContext());
        flexToolbar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.actionBarHeight)));
        flexToolbar.setId(android.R.id.navigationBarBackground);

        relativeLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        llp.addRule(RelativeLayout.BELOW, flexToolbar.getId());
        relativeLayout.setId(android.R.id.content);
        relativeLayout.setLayoutParams(llp);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        _setupLinearLayout();

        RelativeLayout layout = new RelativeLayout(getContext());
        layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(relativeLayout);
        layout.addView(flexToolbar);

        addView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        float oneDP = UtilsKit.dpToPx(1, getContext());

        a = context.obtainStyledAttributes(attrs, R.styleable.PageView, defStyleAttr, defStyleRes);

        setToolbarTitleFont(a.getResourceId(R.styleable.PageView_toolbarTitleFont, 0));
        setToolbarTitleSize(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.PageView_toolbarTitleSize, oneDP * 20));
        setToolbarTitle(a.getString(R.styleable.PageView_toolbarTitle));
        if (a.hasValue(R.styleable.PageView_toolbarNavIcon))
            setToolbarNavIcon(a.getResourceId(R.styleable.PageView_toolbarNavIcon, android.R.color.transparent));
        setToolbarNavClick(a.getInt(R.styleable.PageView_toolbarNavClick, MaterialConstants.TOOLBAR_NAV_CLICK_FINISH));
        setToolbarBackground(a.getResourceId(R.styleable.PageView_toolbarBackground, android.R.color.transparent));
        setToolbarTitlePosition(a.getInt(R.styleable.PageView_toolbarTitlePosition, MaterialConstants.TOOLBAR_TITLE_START));
        if (a.hasValue(R.styleable.PageView_toolbarHeight))
            setToolbarHeight(a.getDimensionPixelSize(R.styleable.PageView_toolbarHeight, 0));
        if (a.hasValue(R.styleable.PageView_toolbarTitleColor))
            setToolbarTitleColor(a.getColor(R.styleable.PageView_toolbarTitleColor, Color.WHITE));
        if (a.hasValue(R.styleable.PageView_toolbarMenu))
            setMenuItems(a.getResourceId(R.styleable.PageView_toolbarMenu, 0));
        if (a.hasValue(R.styleable.PageView_toolbarNavTint))
            setToolbarNavTint(a.getColor(R.styleable.PageView_toolbarNavTint, Color.WHITE));
        if (a.hasValue(R.styleable.PageView_toolbarMenuTint)){
            setToolbarMenuTint(a.getColor(R.styleable.PageView_toolbarMenuTint, Color.WHITE));
        }

        int color = a.getColor(R.styleable.PageView_toolbarShadowColor, 0x10000000);
        View view = new View(getContext());
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                a.getDimensionPixelSize(R.styleable.PageView_toolbarShadowHeight, (int) (oneDP * 10)));
        p.addRule(RelativeLayout.BELOW, flexToolbar.getId());
        view.setLayoutParams(p);

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{color, 0x00FFFFFF});
        gd.setCornerRadius(0f);

        view.setBackground(gd);
        layout.addView(view);

    }

    private void setToolbarMenuTint(int color) {
        flexToolbar.setToolbarMenuTint(color);
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) relativeLayout.addView(child);
        else super.addView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) relativeLayout.addView(child, params);
        else super.addView(child, params);
    }

    public void setDefaultToolbarTitlePosition(int position) {
        if (!a.hasValue(R.styleable.PageView_toolbarTitlePosition))
            setToolbarTitlePosition(position);
    }

    public void setToolbarTitlePosition(int position) {
        if (position == MaterialConstants.TOOLBAR_TITLE_CENTER) {
            flexToolbar.toolbarTitle.setGravity(Gravity.CENTER);
        } else {
            flexToolbar.toolbarTitle.setGravity(Gravity.LEFT);
        }
    }

    public void setDefaultToolbarTitleColor(@ColorInt int color) {
        if (!a.hasValue(R.styleable.PageView_toolbarTitleColor))
            setToolbarTitleColor(color);
    }

    public void setToolbarTitleColor(@ColorInt int color) {
        flexToolbar.toolbarTitle.setTextColor(color);
    }

    public void setDefaultToolbarNavTint(@ColorInt int color) {
        if (!a.hasValue(R.styleable.PageView_toolbarNavTint))
            setToolbarNavTint(color);
    }

    public void setToolbarNavTint(@ColorInt int color) {
        flexToolbar.nav.setColorFilter(color);
    }

    public void setDefaultToolbarTitleFont(@FontRes int font) {
        if (!a.hasValue(R.styleable.PageView_toolbarTitleFont))
            setToolbarTitleFont(font);
    }

    public void setToolbarTitleFont(@FontRes int font) {
        if (font != 0) {
            new Handler().post(() -> {
                Typeface typeface = ResourcesCompat.getFont(getContext(), font);
                flexToolbar.toolbarTitle.setTypeface(typeface);
            });
        }
    }


    public void setDefaultToolbarTitleSize(int unit, float size) {
        if (!a.hasValue(R.styleable.PageView_toolbarTitleSize))
            setToolbarTitleSize(unit, size);
    }

    public void setToolbarTitleSize(int unit, float size) {
        flexToolbar.toolbarTitle.setTextSize(unit, size);
    }

    public void setDefaultToolbarNavIcon(@DrawableRes int res) {
        if (!a.hasValue(R.styleable.PageView_toolbarNavIcon))
            setToolbarNavIcon(res);
    }

    public void setToolbarNavIcon(@DrawableRes int res) {
        flexToolbar.nav.setImageResource(res);
        if (res == android.R.color.transparent) {
            flexToolbar.nav.setVisibility(GONE);
            flexToolbar.toolbarTitle.setPadding(0, 0, 0, 0);
        } else {
            if (!flexToolbar.hasMenuItems())
                flexToolbar.toolbarTitle.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.actionBarHeight), 0);
            flexToolbar.nav.setVisibility(VISIBLE);
        }
//        if (res == android.R.color.transparent)
//            flexToolbar.nav.setVisibility(GONE);
//        else
//            flexToolbar.nav.setVisibility(VISIBLE);
    }

    public void setDefaultToolbarNavClick(int navClick) {
        if (!a.hasValue(R.styleable.PageView_toolbarNavClick))
            setToolbarNavClick(navClick);
    }

    public void setToolbarNavClick(int navClick) {
        if (navClick == MaterialConstants.TOOLBAR_NAV_CLICK_FINISH)
            flexToolbar.nav.setOnClickListener(v -> ((Activity) getContext()).finish());
        else flexToolbar.nav.setOnClickListener(null);
    }

    public void setToolbarNavClick(OnClickListener onClickListener) {
        flexToolbar.nav.setOnClickListener(onClickListener);
    }

    public void setDefaultToolbarTitle(String title) {
        if (!a.hasValue(R.styleable.PageView_toolbarTitle))
            setToolbarTitle(title);
    }

    public void setToolbarTitle(String title) {
        flexToolbar.toolbarTitle.setText(title);
    }

    public void setDefaultToolbarHeight(int height) {
        if (!a.hasValue(R.styleable.PageView_toolbarHeight))
            flexToolbar.getLayoutParams().height = height;
    }

    public void setToolbarHeight(int height) {
        flexToolbar.getLayoutParams().height = height;
    }

    public void setDefaultToolbarBackground(@DrawableRes int res) {
        if (!a.hasValue(R.styleable.PageView_toolbarBackground))
            flexToolbar.setBackgroundResource(res);
    }

    public void setToolbarBackground(@DrawableRes int res) {
        flexToolbar.setBackgroundResource(res);
    }

    //    getters
    public String getToolbarTitle() {
        return flexToolbar.toolbarTitle.getText().toString();
    }

    public void setMenuItems(@MenuRes int menuRes) {
        flexToolbar.setMenuItems(menuRes);
    }

    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClickListener){
        flexToolbar.onMenuItemClickListener = onMenuItemClickListener;
    }
}
