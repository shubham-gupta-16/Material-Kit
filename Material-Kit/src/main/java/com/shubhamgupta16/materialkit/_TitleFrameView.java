package com.shubhamgupta16.materialkit;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.FontRes;
import androidx.annotation.Nullable;

public class _TitleFrameView extends LinearLayout {

    private TextView titleView, errorTextView;
    private int errorColor, titleColor;
    private LinearLayout layout;

    public _TitleFrameView(Context context) {
        this(context, null);
    }

    public _TitleFrameView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public _TitleFrameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        buildViews();


        ContentValues contentValues = initialize(context, attrs, defStyleAttr);
        build(contentValues);
    }

    ContentValues initialize(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable._TitleFrameView, defStyleAttr, 0);
        ContentValues contentValues = new ContentValues();
        if (a.hasValue(R.styleable._TitleFrameView_title))
            contentValues.put("title", a.getString(R.styleable._TitleFrameView_title));
        if (a.hasValue(R.styleable._TitleFrameView_titleFontFamily))
            contentValues.put("titleFontFamily", a.getResourceId(R.styleable._TitleFrameView_titleFontFamily, 0));
        if (a.hasValue(R.styleable._TitleFrameView_errorFontFamily))
            contentValues.put("errorFontFamily", a.getResourceId(R.styleable._TitleFrameView_errorFontFamily, 0));
        contentValues.put("titleTextSize", a.getDimensionPixelSize(R.styleable._TitleFrameView_titleTextSize, UtilsKit.idpToPx(14, getContext())));
        contentValues.put("errorTextSize", a.getDimensionPixelSize(R.styleable._TitleFrameView_errorTextSize, UtilsKit.idpToPx(12, getContext())));
        contentValues.put("titleTextColor", a.getColor(R.styleable._TitleFrameView_titleTextColor, getResources().getColor(R.color.kitTextPrimary)));
        contentValues.put("errorColor", a.getColor(R.styleable._TitleFrameView_errorTextColor, getResources().getColor(R.color.kitError)));
        contentValues.put("horizontalGap", a.getDimensionPixelSize(R.styleable._TitleFrameView_horizontalGap, 0));
        contentValues.put("titleBottomGap", a.getDimensionPixelSize(R.styleable._TitleFrameView_titleBottomGap, UtilsKit.idpToPx(8, getContext())));
        contentValues.put("hideTitle", a.getBoolean(R.styleable._TitleFrameView_hideTitle, false));
        a.recycle();
        return contentValues;
    }

    protected void build(ContentValues contentValues) {
        setTitleBottomGap(contentValues.getAsInteger("titleBottomGap"));
        setHorizontalGap(contentValues.getAsInteger("horizontalGap"));
        setErrorColor(contentValues.getAsInteger("errorColor"));
        setTitleTextColor(contentValues.getAsInteger("titleTextColor"));
        setErrorTextSize(TypedValue.COMPLEX_UNIT_PX, contentValues.getAsInteger("errorTextSize"));
        setTitleTextSize(TypedValue.COMPLEX_UNIT_PX, contentValues.getAsInteger("titleTextSize"));
        if (contentValues.containsKey("titleFontFamily"))
            setTitleFont(contentValues.getAsInteger("titleFontFamily"));
        if (contentValues.containsKey("errorFontFamily"))
            setErrorFont(contentValues.getAsInteger("errorFontFamily"));
        if (contentValues.containsKey("title"))
            setTitle(contentValues.getAsString("title"));
        if (contentValues.getAsBoolean("hideTitle"))
            hideTitle();
        else
            showTitle();

    }

    void buildViews() {
        //        TitleView
        titleView = new TextView(getContext());
        LayoutParams p = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(titleView, p);

        layout = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(layout, params);

        errorTextView = new TextView(getContext());
        LayoutParams ep = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        ep.setMargins(0, (int) UtilsKit.dpToPx(4, getContext()), 0, 0);
        addView(errorTextView, ep);
        setOrientation(VERTICAL);
    }


    @Override
    public void addView(View child) {
        layout.addView(child);
    }

    public void hideTitle(){
        titleView.setVisibility(GONE);
    }
    public void showTitle(){
        titleView.setVisibility(VISIBLE);
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
        UtilsKit.setTypeFace(getContext(),titleView, font);
    }

    public void setErrorFont(@FontRes int font) {
        UtilsKit.setTypeFace(getContext(),errorTextView, font);
    }

    public void setHorizontalGap(int horizontalGap) {
        titleView.setPadding(horizontalGap, 0, 0, titleView.getPaddingBottom());
        errorTextView.setPadding(horizontalGap, 0, 0, 0);
    }

    public void setTitleBottomGap(int bottom) {
        titleView.setPadding(titleView.getPaddingLeft(), 0, 0, bottom);
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
