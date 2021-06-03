package com.shubhamgupta16.materialkit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.List;

public class RadioScrollView extends _TitleFrameView {
    // todo optimize getCheckChange by global variable
    private RadioGroup group;

    public RadioScrollView(Context context) {
        super(context);
    }

    public RadioScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int radioGap;
    private int radioBackground;
    private Typeface radioFont;
    private int radioHorizontalPadding, radioVerticalGap, radioTextColor;
    private float radioTextSize;
    private OnCheckedChangeListener onCheckedChangeListener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    protected void init() {
        super.init();
//        HorizontalScrollView
        HorizontalScrollView hor = new HorizontalScrollView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, (int) UtilsKit.dpToPx(8, getContext()), 0, 0);
        hor.setLayoutParams(params);
        hor.setScrollBarSize(0);
        addView(hor);

//        Radio Group
        group = new RadioGroup(getContext());
        group.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        group.setOrientation(HORIZONTAL);
        hor.addView(group);

        setRadioHorizontalPadding((int) UtilsKit.dpToPx(20, getContext()));
        setRadioVerticalGap((int) UtilsKit.dpToPx(10, getContext()));
        setRadioGap((int) UtilsKit.dpToPx(10, getContext()));
        setRadioBackground(R.drawable.kit_radio_round);
        setRadioTextColor(Color.BLACK);
        setRadioTextSize(UtilsKit.dpToPx(14, getContext()));

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                removeError();
                if (onCheckedChangeListener != null)
                    onCheckedChangeListener.onCheckedChange(getCheckedText(), getCheckedPosition());
            }
        });
    }

    @Override
    public void setHorizontalPadding(int horizontalPadding) {
        super.setHorizontalPadding(horizontalPadding);
        _updateRadioUI();
    }

    public void setRadioTextColor(@ColorInt int color) {
        radioTextColor = color;
        _updateRadioUI();
    }

    public void setRadioTextSize(float size) {
        radioTextSize = size;
        _updateRadioUI();
    }

    public void setRadioFont(@FontRes int font) {
        this.radioFont = ResourcesCompat.getFont(getContext(), font);
        _updateRadioUI();
    }

    public void setRadioBackground(@DrawableRes int radioBackground) {
        this.radioBackground = radioBackground;
        _updateRadioUI();
    }

    public void setRadioGap(int radioGap) {
        this.radioGap = radioGap;
        _updateRadioFullUI();
    }

    public void setRadioHorizontalPadding(int radioHorizontalPadding) {
        this.radioHorizontalPadding = radioHorizontalPadding;
        _updateRadioUI();
    }

    public void setRadioVerticalGap(int radioVerticalGap) {
        this.radioVerticalGap = radioVerticalGap;
        _updateRadioUI();
    }

    private void _updateRadioFullUI() {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton rBtn = (RadioButton) group.getChildAt(i);
            rBtn.setPadding(radioHorizontalPadding, radioVerticalGap, radioHorizontalPadding, radioVerticalGap);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i == 0)
                params.setMarginStart(horizontalPadding);
            if (i == group.getChildCount() - 1)
                params.setMarginEnd(horizontalPadding);
            else
                params.setMarginEnd(radioGap);
            rBtn.setLayoutParams(params);
        }
    }

    private void _updateRadioUI() {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton rBtn = (RadioButton) group.getChildAt(i);
            rBtn.setPadding(radioHorizontalPadding, radioVerticalGap, radioHorizontalPadding, radioVerticalGap);
            rBtn.setBackgroundResource(radioBackground);
            rBtn.setTextColor(radioTextColor);
            rBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, radioTextSize);
            if (radioFont != null)
                rBtn.setTypeface(radioFont);
        }
    }

    private RadioButton _buildRadio(String text) {
        RadioButton button = new RadioButton(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        button.setText(text);
        button.setButtonDrawable(null);
        return button;
    }

    public void setList(List<String> strings) {
        group.removeAllViews();
        addList(strings);
    }

    public void addList(List<String> strings) {
        if (strings == null) return;
        for (String text : strings) {
            RadioButton button = _buildRadio(text);
            group.addView(button);
        }
        _updateRadioFullUI();
        Log.d("tagtag", "atlesast here");
        _updateRadioUI();
    }

    public void setChecked(int position) {
        if (position < 0) return;
        if (position >= group.getChildCount()) return;
        ((RadioButton) group.getChildAt(position)).setChecked(true);
    }

    public void setChecked(String text) {
        if (text == null) return;
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton rBtn = (RadioButton) group.getChildAt(i);
            if (rBtn.getText().equals(text)) {
                rBtn.setChecked(true);
            }
        }
    }

    public int getCheckedPosition() {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton rBtn = (RadioButton) group.getChildAt(i);
            if (rBtn.isChecked())
                return i;
        }
        return -1;
    }

    public String getCheckedText() {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton rBtn = (RadioButton) group.getChildAt(i);
            if (rBtn.isChecked())
                return rBtn.getText().toString();
        }
        return null;
    }

    public void clearChecked() {
        for (int i = 0; i < group.getChildCount(); i++) {
            ((RadioButton) group.getChildAt(i)).setChecked(false);
        }
    }

    public boolean hasChecked(){
        return getCheckedPosition() >= 0;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(String text, int position);
    }
}
