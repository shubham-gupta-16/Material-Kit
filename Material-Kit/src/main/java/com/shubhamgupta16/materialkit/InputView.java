package com.shubhamgupta16.materialkit;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.FontRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class InputView extends _TitleFrameView {

    private int editTextHorizontalPadding;
    private int editTextVerticalPadding;
    private int inputBackground, inputErrorBackground;
    private EditText input;
    private TextInputView.OnInputChangeListener onInputChangeListener;
    private boolean clearError = true;

    public InputView(Context context) {
        super(context);
    }

    public InputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void buildViews() {
        super.buildViews();
        input = new EditText(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(params);
        input.setGravity(Gravity.TOP);
        addView(input);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (clearError)
                    removeError();
                if (onInputChangeListener != null)
                    onInputChangeListener.onChange(input.getText(), s.toString().trim().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    ContentValues initialize(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        ContentValues contentValues = super.initialize(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InputView, defStyleAttr, 0);
        if (a.hasValue(R.styleable.InputView_inputHint))
            contentValues.put("inputHint", a.getString(R.styleable.InputView_inputHint));
        if (a.hasValue(R.styleable.InputView_inputFontFamily))
            contentValues.put("inputFontFamily", a.getResourceId(R.styleable.InputView_inputFontFamily, 0));
        if (a.hasValue(R.styleable.InputView_inputHintTextColor))
            contentValues.put("inputHintTextColor", a.getColor(R.styleable.InputView_inputHintTextColor, getResources().getColor(R.color.kitTextSecondary)));

//        android's
        if (a.hasValue(R.styleable.InputView_android_imeOptions))
            contentValues.put("imeOptions", a.getInt(R.styleable.InputView_android_imeOptions, 0));
        if (a.hasValue(R.styleable.InputView_android_inputType))
            contentValues.put("inputType", a.getInt(R.styleable.InputView_android_inputType, 0));
        if (a.hasValue(R.styleable.InputView_android_lines))
            contentValues.put("lines", a.getInt(R.styleable.InputView_android_lines, 0));
        if (a.hasValue(R.styleable.InputView_android_maxLines))
            contentValues.put("maxLines", a.getInt(R.styleable.InputView_android_maxLines, 0));
        if (a.hasValue(R.styleable.InputView_android_minLines))
            contentValues.put("minLines", a.getInt(R.styleable.InputView_android_minLines, 0));
        if (a.hasValue(R.styleable.InputView_android_maxLength))
            contentValues.put("maxLength", a.getInt(R.styleable.InputView_android_maxLength, 0));
//        customs
        contentValues.put("inputBackground", a.getResourceId(R.styleable.InputView_inputBackground, R.drawable.kit_form_outline));
        contentValues.put("inputErrorBackground", a.getResourceId(R.styleable.InputView_inputErrorBackground, R.drawable.kit_form_error_outline));
        contentValues.put("inputHorizontalPadding", a.getDimensionPixelSize(R.styleable.InputView_inputHorizontalPadding, UtilsKit.idpToPx(16, getContext())));
        contentValues.put("inputVerticalPadding", a.getDimensionPixelSize(R.styleable.InputView_inputVerticalPadding, UtilsKit.idpToPx(10, getContext())));
        contentValues.put("inputTextSize", a.getDimensionPixelSize(R.styleable.InputView_inputTextSize, UtilsKit.idpToPx(15, getContext())));
        contentValues.put("inputTextColor", a.getColor(R.styleable.InputView_inputTextColor, getResources().getColor(R.color.kitTextSecondary)));
        contentValues.put("clearErrorOnInput", a.getBoolean(R.styleable.InputView_clearErrorOnInput, false));
        a.recycle();
        return contentValues;
    }

    @Override
    protected void build(ContentValues contentValues) {
        super.build(contentValues);
        setInputBackground(contentValues.getAsInteger("inputBackground"));
        setInputErrorBackground(contentValues.getAsInteger("inputErrorBackground"));
        setInputHorizontalPadding(contentValues.getAsInteger("inputHorizontalPadding"));
        setInputVerticalPadding(contentValues.getAsInteger("inputVerticalPadding"));
        setClearErrorOnInput(contentValues.getAsBoolean("clearErrorOnInput"));
        setInputTextColor(contentValues.getAsInteger("inputTextColor"));
        setInputTextSize(TypedValue.COMPLEX_UNIT_PX, contentValues.getAsInteger("inputTextSize"));
        if (contentValues.containsKey("inputHint"))
            setInputHint(contentValues.getAsString("inputHint"));
        if (contentValues.containsKey("inputFontFamily"))
            setInputFont(contentValues.getAsInteger("inputFontFamily"));
        if (contentValues.containsKey("inputHintTextColor"))
            setInputHintTextColor(contentValues.getAsInteger("inputHintTextColor"));

//        android's
        if (contentValues.containsKey("imeOptions"))
            input.setImeOptions(contentValues.getAsInteger("imeOptions"));
        if (contentValues.containsKey("inputType"))
            input.setInputType(contentValues.getAsInteger("inputType"));
        if (contentValues.containsKey("maxLines"))
            input.setMaxLines(contentValues.getAsInteger("maxLines"));
        if (contentValues.containsKey("minLines"))
            input.setMinLines(contentValues.getAsInteger("minLines"));
        if (contentValues.containsKey("lines"))
            input.setLines(contentValues.getAsInteger("lines"));
        if (contentValues.containsKey("maxLength")) {
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(contentValues.getAsInteger("maxLength"));
            input.setFilters(filterArray);
        }

    }

    @Override
    public void setHorizontalGap(int horizontalGap) {
        super.setHorizontalGap(horizontalGap);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(horizontalGap, 0, horizontalGap, 0);
        input.setLayoutParams(params);
    }

    @Override
    public void setError(String error) {
        super.setError(error);
        input.setBackgroundResource(inputErrorBackground);
    }

    @Override
    public void removeError() {
        super.removeError();
        input.setBackgroundResource(inputBackground);
    }

    public void setClearErrorOnInput(boolean clearError) {
        this.clearError = clearError;
    }

    public void setInputTextColor(@ColorInt int color) {
        input.setTextColor(color);
    }

    private void setInputHintTextColor(@ColorInt int inputHintTextColor) {
        input.setHintTextColor(inputHintTextColor);
    }

    public void setInputTextSize(float size) {
        input.setTextSize(size);
    }

    public void setInputTextSize(int unit, float size) {
        input.setTextSize(unit, size);
    }

    public void setInputFont(@FontRes int font) {
        UtilsKit.setTypeFace(getContext(), input, font);
    }

    public void setOnInputChangeListener(TextInputView.OnInputChangeListener onInputChangeListener) {
        this.onInputChangeListener=onInputChangeListener;
    }

    public void setInputHorizontalPadding(int editTextHorizontalPadding) {
        this.editTextHorizontalPadding = editTextHorizontalPadding;
        input.setPadding(editTextHorizontalPadding, editTextVerticalPadding, editTextHorizontalPadding, editTextVerticalPadding);
    }

    public void setInputVerticalPadding(int editTextVerticalPadding) {
        this.editTextVerticalPadding = editTextVerticalPadding;
        input.setPadding(editTextHorizontalPadding, editTextVerticalPadding, editTextHorizontalPadding, editTextVerticalPadding);
    }

    public void setInputBackground(int inputBackground) {
        this.inputBackground = inputBackground;
        input.setBackgroundResource(inputBackground);
    }

    public void setInputErrorBackground(int inputErrorBackground) {
        this.inputErrorBackground = inputErrorBackground;
    }

    public EditText getInput() {
        return input;
    }

    public String getText() {
        return input.getText().toString();
    }

    public String getText(boolean trimmed) {
        if (trimmed)
            return getText().trim();
        else
            return getText();
    }

    public void setText(String text) {
        input.setText(text);
    }

    public void setInputHint(String text) {
        input.setHint(text);
    }
}
