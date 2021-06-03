package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.ColorInt;
import androidx.annotation.FontRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class InputView extends _TitleFrameView {

    private int editTextHorizontalPadding;
    private int editTextVerticalPadding;
    private int inputBackground, inputErrorBackground;
    private EditText input;
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
    protected void init() {
        super.init();
        input = new EditText(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(params);
        addView(input);

        setInputBackground(R.drawable.kit_form_outline);
        setInputErrorBackground(R.drawable.kit_form_error_outline);
        setInputHorizontalPadding((int) UtilsKit.dpToPx(10, getContext()));
        setInputVerticalPadding((int) UtilsKit.dpToPx(10, getContext()));
    }

    @Override
    public void setHorizontalPadding(int horizontalPadding) {
        super.setHorizontalPadding(horizontalPadding);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(horizontalPadding, 0, horizontalPadding, 0);
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

    public void clearErrorOnInput(boolean clearError) {
        this.clearError = clearError;
    }

    public void setInputTextColor(@ColorInt int color) {
        input.setTextColor(color);
    }

    public void setInputTextSize(float size) {
        input.setTextSize(size);
    }

    public void setInputTextSize(int unit, float size) {
        input.setTextSize(unit, size);
    }

    public void setInputFont(@FontRes int font) {
        Typeface titleFont = ResourcesCompat.getFont(getContext(), font);
        input.setTypeface(titleFont);
    }

    public void setOnInputChangeListener(TextInputView.OnInputChangeListener onInputChangeListener) {
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

    public void setHint(String text) {
        input.setHint(text);
    }
}
