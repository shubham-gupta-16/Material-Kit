package com.shubhamgupta16.materialkit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.core.content.res.ResourcesCompat;

public class KitSearchView extends RelativeLayout {

    private EditText editText;
    private ImageButton backButton, clearButton;
    private SearchView.OnQueryTextListener onQueryTextListener;
    private TypedArray a;

    public KitSearchView(Context context) {
        super(context);
        init();
    }

    public KitSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KitSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public KitSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        a = context.obtainStyledAttributes(attrs, R.styleable.KitSearchView, defStyleAttr, defStyleRes);

        if (a.hasValue(R.styleable.KitSearchView_hintTextColor))
            setHintTextColor(a.getColor(R.styleable.KitSearchView_hintTextColor, Color.GRAY));
        if (a.hasValue(R.styleable.KitSearchView_clearButtonTint))
            setClearButtonTint(a.getColor(R.styleable.KitSearchView_clearButtonTint, Color.BLACK));
        if (a.hasValue(R.styleable.KitSearchView_backButtonTint))
            setBackButtonTint(a.getColor(R.styleable.KitSearchView_backButtonTint, Color.BLACK));
        if (a.hasValue(R.styleable.KitSearchView_backButtonIcon))
            setBackButtonIcon(a.getResourceId(R.styleable.KitSearchView_backButtonIcon, 0));
        if (a.hasValue(R.styleable.KitSearchView_clearButtonIcon))
            setClearButtonIcon(a.getResourceId(R.styleable.KitSearchView_clearButtonIcon, 0));
        if (a.hasValue(R.styleable.KitSearchView_android_inputType))
            setInputType(a.getInteger(R.styleable.KitSearchView_android_inputType, 0));
        if (a.hasValue(R.styleable.KitSearchView_android_textColor))
            setTextColor(a.getColor(R.styleable.KitSearchView_android_textColor, Color.BLACK));
        if (a.hasValue(R.styleable.KitSearchView_android_maxLength))
            setMaxLength(a.getInteger(R.styleable.KitSearchView_android_maxLength, 0));
        if (a.hasValue(R.styleable.KitSearchView_android_hint))
            setHint(a.getString(R.styleable.KitSearchView_android_hint));
        if (a.hasValue(R.styleable.KitSearchView_android_fontFamily))
            setFontFamily(a.getResourceId(R.styleable.KitSearchView_android_fontFamily, 0));
        if (a.hasValue(R.styleable.KitSearchView_android_textSize))
            setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.KitSearchView_android_textSize, 0));
        a.recycle();
    }

    private void init() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getContext()).inflate(R.layout.search_bar, null, false);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        view.setLayoutParams(params);
        addView(view);

        backButton = view.findViewById(R.id.backButton);
        editText = view.findViewById(R.id.searchBar);
        clearButton = view.findViewById(R.id.closeButton);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                    clearButton.setVisibility(GONE);
                else
                    clearButton.setVisibility(VISIBLE);
                if (onQueryTextListener != null)
                    onQueryTextListener.onQueryTextChange(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clearButton.setOnClickListener(v -> editText.setText(""));

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (onQueryTextListener != null)
                    onQueryTextListener.onQueryTextSubmit(editText.getText().toString());
                editText.clearFocus();
                UtilsKit.hideKeyboard((Activity) getContext());
                return true;
            }
            return false;
        });
    }

    public void setOnQueryTextListener(SearchView.OnQueryTextListener onQueryTextListener) {
        this.onQueryTextListener = onQueryTextListener;
    }

    public void setOnBackClickListener(OnClickListener onCloseClickListener) {
        backButton.setOnClickListener(onCloseClickListener);
    }

    public void setTextSize(int unit, float size) {
        editText.setTextSize(unit, size);
    }

    public void setTextSize(float size) {
        editText.setTextSize(size);
    }

    public String getQuery() {
        return editText.getText().toString();
    }

    public void setQuery(String query) {
        setQuery(query, false);
    }

    public void setQuery(String query, boolean submit) {
        editText.setText(query);
        if (submit)
            if (onQueryTextListener != null)
                onQueryTextListener.onQueryTextSubmit(query);
    }

    public void setHint(String string) {
        editText.setHint(string);
    }

    public void setHintTextColor(@ColorInt int color) {
        editText.setHintTextColor(color);
    }

    public void setTextColor(@ColorInt int color) {
        editText.setTextColor(color);
    }

    public void setQueryTextColor(@ColorInt int color) {
        editText.setTextColor(color);
    }

    public void setBackButtonIcon(@DrawableRes int res) {
        backButton.setImageResource(res);
    }

    public void setClearButtonIcon(@DrawableRes int res) {
        clearButton.setImageResource(res);
    }

    public void setBackButtonTint(@ColorInt int color) {
        backButton.setColorFilter(color);
    }

    public void setClearButtonTint(@ColorInt int color) {
        clearButton.setColorFilter(color);
    }

    public void setMaxLength(int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    public void setTypeface(Typeface typeface) {
        editText.setTypeface(typeface);
    }

    public void setInputType(int inputType) {
        editText.setInputType(inputType);
    }

    public void setFontFamily(@FontRes int font) {
        if (font != 0) {
            new Handler().post(() -> {
                Typeface typeface = ResourcesCompat.getFont(getContext(), font);
                setTypeface(typeface);
            });
        }
    }
}
