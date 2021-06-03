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

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.core.content.res.ResourcesCompat;

public class SearchView extends RelativeLayout {

    private EditText editText;
    private ImageButton backButton, clearButton, micButton;
    private android.widget.SearchView.OnQueryTextListener onQueryTextListener;
    private boolean micEnabled = true;

    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SearchView, defStyleAttr, defStyleRes);

        if (a.hasValue(R.styleable.SearchView_hintTextColor))
            setHintTextColor(a.getColor(R.styleable.SearchView_hintTextColor, Color.GRAY));
        if (a.hasValue(R.styleable.SearchView_clearButtonTint))
            setClearButtonTint(a.getColor(R.styleable.SearchView_clearButtonTint, Color.BLACK));
        if (a.hasValue(R.styleable.SearchView_micButtonTint))
            setMicButtonTint(a.getColor(R.styleable.SearchView_micButtonTint, Color.BLACK));
        if (a.hasValue(R.styleable.SearchView_backButtonTint))
            setBackButtonTint(a.getColor(R.styleable.SearchView_backButtonTint, Color.BLACK));
        if (a.hasValue(R.styleable.SearchView_backButtonIcon))
            setBackButtonIcon(a.getResourceId(R.styleable.SearchView_backButtonIcon, 0));
        if (a.hasValue(R.styleable.SearchView_clearButtonIcon))
            setClearButtonIcon(a.getResourceId(R.styleable.SearchView_clearButtonIcon, 0));
        if (a.hasValue(R.styleable.SearchView_micButtonIcon))
            setMicButtonIcon(a.getResourceId(R.styleable.SearchView_micButtonIcon, 0));
        if (a.hasValue(R.styleable.SearchView_android_inputType))
            setInputType(a.getInteger(R.styleable.SearchView_android_inputType, 0));
        if (a.hasValue(R.styleable.SearchView_android_textColor))
            setTextColor(a.getColor(R.styleable.SearchView_android_textColor, Color.BLACK));
        if (a.hasValue(R.styleable.SearchView_android_maxLength))
            setMaxLength(a.getInteger(R.styleable.SearchView_android_maxLength, 0));
        if (a.hasValue(R.styleable.SearchView_android_hint))
            setHint(a.getString(R.styleable.SearchView_android_hint));
        if (a.hasValue(R.styleable.SearchView_android_fontFamily))
            setFontFamily(a.getResourceId(R.styleable.SearchView_android_fontFamily, 0));
        if (a.hasValue(R.styleable.SearchView_micEnabled))
            setMicEnabled(a.getBoolean(R.styleable.SearchView_micEnabled, true));
        if (a.hasValue(R.styleable.SearchView_android_textSize))
            setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimension(R.styleable.SearchView_android_textSize, 0));
        a.recycle();
    }

    private void init() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(getContext()).inflate(R.layout.kit_search_bar, null, false);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        view.setLayoutParams(params);
        addView(view);

        backButton = view.findViewById(R.id.backButton);
        editText = view.findViewById(R.id.searchBar);
        clearButton = view.findViewById(R.id.closeButton);
        micButton = view.findViewById(R.id.micButton);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    clearButton.setVisibility(GONE);
                    if (micEnabled)
                        micButton.setVisibility(VISIBLE);
                } else {
                    clearButton.setVisibility(VISIBLE);
                    if (micEnabled)
                        micButton.setVisibility(GONE);
                }
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

    public void setOnMicButtonClickListener(OnClickListener onMicClickListener) {
        micButton.setOnClickListener(onMicClickListener);
    }

    public void setOnQueryTextListener(android.widget.SearchView.OnQueryTextListener onQueryTextListener) {
        this.onQueryTextListener = onQueryTextListener;
    }

    public void setOnBackButtonClickListener(OnClickListener onCloseClickListener) {
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

    public void setMicButtonIcon(@DrawableRes int res) {
        micButton.setImageResource(res);
    }

    public void setBackButtonTint(@ColorInt int color) {
        backButton.setColorFilter(color);
    }

    private void setMicButtonTint(int color) {
        micButton.setColorFilter(color);
    }

    public void setClearButtonTint(@ColorInt int color) {
        clearButton.setColorFilter(color);
    }

    public void setMaxLength(int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    public void setMicEnabled(boolean showMic) {
        this.micEnabled = showMic;
        if (!showMic)
            micButton.setVisibility(GONE);
        else if (getQuery().length() == 0)
            micButton.setVisibility(VISIBLE);
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
