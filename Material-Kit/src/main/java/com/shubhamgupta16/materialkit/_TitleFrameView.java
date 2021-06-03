package com.shubhamgupta16.materialkit;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.FontRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class _TitleFrameView extends LinearLayout {

    private TextView titleView, errorTextView;
    private int errorColor, titleColor;
    private LinearLayout layout;
    protected int horizontalPadding = 0;

    public _TitleFrameView(Context context) {
        super(context);
        init();
        initialize();
    }

    public _TitleFrameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initialize();
    }

    public _TitleFrameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initialize();
    }

    protected void init() {
        //        TitleView
        titleView = new TextView(getContext());
        LayoutParams p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 0, 0, (int) UtilsKit.dpToPx(8, getContext()));
        addView(titleView, p);


        layout = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(layout, params);

        errorTextView = new TextView(getContext());
        LayoutParams ep = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        ep.setMargins(0, (int) UtilsKit.dpToPx(4, getContext()), 0, 0);
        addView(errorTextView, ep);
    }

    private void initialize() {
        setHorizontalPadding((int) UtilsKit.dpToPx(20, getContext()));
        setBottomPadding(horizontalPadding);
        setErrorTextSize(UtilsKit.dpToPx(12, getContext()));
        setErrorColor(Color.RED);
        setOrientation(VERTICAL);
    }

    @Override
    public void addView(View child) {
        layout.addView(child);
    }

    public void setErrorColor(@ColorInt int errorColor) {
        this.errorColor = errorColor;
        errorTextView.setTextColor(errorColor);
    }

    public void setErrorTextSize(float size) {
        errorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setErrorTextSize(int unit, float size) {
        errorTextView.setTextSize(unit, size);
    }

    public void setError(String error) {
        errorTextView.setText(error);
        titleView.setTextColor(errorColor);
        ValueAnimator anim = ValueAnimator.ofInt(errorTextView.getMeasuredHeight(), (int) UtilsKit.dpToPx(20, getContext())).setDuration(150);
        anim.addUpdateListener(AnimUtils.heightAnimListener(errorTextView));
        anim.start();
    }

    public void removeError() {
        errorTextView.setText("");
        titleView.setTextColor(titleColor);
        ValueAnimator anim = ValueAnimator.ofInt(errorTextView.getMeasuredHeight(), 0).setDuration(80);
        anim.addUpdateListener(AnimUtils.heightAnimListener(errorTextView));
        anim.start();
    }

    public void setTitleTextColor(@ColorInt int color) {
        titleColor = color;
        titleView.setTextColor(color);
    }

    public void setTitleTextSize(int unit, float size) {
        titleView.setTextSize(unit, size);
    }

    public void setTitleTextSize(float size) {
        titleView.setTextSize(size);
    }


    public void setTitleFont(@FontRes int font) {
        Typeface titleFont = ResourcesCompat.getFont(getContext(), font);
        titleView.setTypeface(titleFont);
    }

    public void setErrorFont(@FontRes int font) {
        Typeface titleFont = ResourcesCompat.getFont(getContext(), font);
        errorTextView.setTypeface(titleFont);
    }

    public void setBottomPadding(int bottomPadding) {
        setPadding(0, 0, 0, bottomPadding);
    }

    public void setHorizontalPadding(int horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
        titleView.setPadding(horizontalPadding, 0, 0, 0);
        errorTextView.setPadding(horizontalPadding, 0, 0, 0);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setTitle(String text) {
        setTitle(text, false);
    }

    @SuppressLint("SetTextI18n")
    public void setTitle(String text, boolean isMandatory) {
        if (isMandatory)
            titleView.setText(text + " *");
        else
            titleView.setText(text);
    }

    public String getTitle() {
        String title = titleView.getText().toString();
        if (title.endsWith(" *"))
            return title.substring(0, title.length() - 2);
        return title;
    }
}
