package com.shubhamgupta16.materialkit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UtilsKit {

    public static float dpToPx(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / 160.0F);
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth() , v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity, View view) {
        InputMethodManager iim = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        iim.toggleSoftInputFromWindow(
                view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
        view.requestFocus();
    }

}
