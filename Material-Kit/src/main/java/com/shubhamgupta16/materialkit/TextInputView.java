package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;


public class TextInputView extends TextInputLayout {
    public TextInputView(@NonNull Context context) {
        this(context, null);
    }

    public TextInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private OnInputChangeListener onInputChangeListener;
    private boolean clearError = false;

    public TextInputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        addOnEditTextAttachedListener(new OnEditTextAttachedListener() {
            @Override
            public void onEditTextAttached(@NonNull TextInputLayout textInputLayout) {
                if (textInputLayout.getEditText() == null) return;
                textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (clearError)
                            setErrorEnabled(false);
                        if (onInputChangeListener != null)
                            onInputChangeListener.onChange(s, count);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
    }

    public void clearErrorOnInput(boolean clearError) {
        this.clearError = clearError;
    }

    public String getText() {
        if (getEditText() != null)
            return getEditText().getText().toString();
        return "";
    }

    public void setText(String text) {
        if (getEditText() != null)
            getEditText().setText(text);
    }


    public void setOnInputChangeListener(OnInputChangeListener onInputChangeListener) {
        this.onInputChangeListener = onInputChangeListener;
    }

    public interface OnInputChangeListener {
        void onChange(CharSequence input, int count);
    }


}
