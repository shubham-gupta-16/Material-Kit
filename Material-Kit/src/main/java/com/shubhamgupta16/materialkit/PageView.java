package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;

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

    private void _setupLinearLayout() {
        flexToolbar = new FlexToolbar(getContext());
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, outValue, true);
        flexToolbar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(outValue.resourceId)));
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

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PageView, defStyleAttr, defStyleRes);

        setToolbarTitleFont(a.getResourceId(R.styleable.PageView_toolbarTitleFont, 0));
        if (a.hasValue(R.styleable.PageView_toolbarNavSize))
            setToolbarNavSize(a.getDimensionPixelSize(R.styleable.PageView_toolbarNavSize, 0));
        if (a.hasValue(R.styleable.PageView_toolbarNavPadding))
            setToolbarNavPadding(a.getDimensionPixelSize(R.styleable.PageView_toolbarNavPadding, 0));
        setToolbarTitleSize(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.PageView_toolbarTitleSize, oneDP * 20));

        setToolbarTitle(a.getString(R.styleable.PageView_toolbarTitle));
        setToolbarNavIcon(a.getResourceId(R.styleable.PageView_toolbarNavIcon, android.R.color.transparent));
        setToolbarNavClick(a.getInt(R.styleable.PageView_toolbarNavClick, MaterialConstants.TOOLBAR_NAV_CLICK_FINISH));
        setToolbarBackground(a.getResourceId(R.styleable.PageView_toolbarBackground, android.R.color.transparent));
        setToolbarTitlePosition(a.getInt(R.styleable.PageView_toolbarTitlePosition, MaterialConstants.TOOLBAR_TITLE_START));
        if (a.hasValue(R.styleable.PageView_toolbarHeight))
            setToolbarHeight(a.getDimensionPixelSize(R.styleable.PageView_toolbarHeight, 0));
        if (a.hasValue(R.styleable.PageView_toolbarTitleColor))
            setToolbarTitleColor(a.getColor(R.styleable.PageView_toolbarTitleColor, Color.WHITE));
        if (a.hasValue(R.styleable.PageView_toolbarNavTint))
            setToolbarNavTint(a.getColor(R.styleable.PageView_toolbarNavTint, Color.WHITE));

        if (a.hasValue(R.styleable.PageView_toolbarNavBackground))
            setToolbarNavBackground(a.getResourceId(R.styleable.PageView_toolbarNavBackground, android.R.color.transparent));
        else {
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true);
            setToolbarNavBackground(outValue.resourceId);
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

        a.recycle();
    }

    private void setToolbarNavPadding(int padding) {
        flexToolbar.setToolbarNavPadding(padding);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) relativeLayout.addView(child, params);
        else super.addView(child, params);
    }

    public void setToolbarTitlePosition(int position) {
        flexToolbar.setToolbarTitlePosition(position);
    }

    public void setToolbarTitleColor(@ColorInt int color) {
        flexToolbar.setToolbarTitleColor(color);
    }

    public void setToolbarNavTint(@ColorInt int color) {
        flexToolbar.setToolbarNavTint(color);
    }

    public void setToolbarTitleFont(@FontRes int font) {
        flexToolbar.setToolbarTitleFont(font);
    }

    public void setToolbarNavSize(int size) {
        flexToolbar.setToolbarNavSize(size);
    }

    public void setToolbarTitleSize(int unit, float size) {
        flexToolbar.setToolbarTitleSize(unit, size);
    }

    public void setToolbarNavIcon(@DrawableRes int res) {
        flexToolbar.setToolbarNavIcon(res);
    }

    public void setToolbarNavBackground(@DrawableRes int res) {
        flexToolbar.setToolbarNavBackground(res);
    }

    public void setToolbarNavClick(int navClick) {
        flexToolbar.setToolbarNavClick(navClick);
    }
    public void setToolbarNavClick(OnClickListener onClickListener) {
        flexToolbar.setToolbarNavClick(onClickListener);
    }

    public void setToolbarTitle(String title) {
        flexToolbar.setToolbarTitle(title);
    }

    public void setToolbarHeight(int height) {
        flexToolbar.getLayoutParams().height = height;
    }

    public void setToolbarBackground(@DrawableRes int res) {
        flexToolbar.setBackgroundResource(res);
    }
}
