package com.shubhamgupta16.materialkit;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UtilsKit {

    public static float dpToPx(float dp, Context context) {
        return dp * ((float)context.getResources().getDisplayMetrics().densityDpi / 160.0F);
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

    public static void fadeVisibleView(final View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0f);
            view.animate().setDuration(400).alpha(1);
        }
    }

    public static void fadeHideView(final View view) {
        if (view.getVisibility() != View.GONE) {
            view.animate().setDuration(400).alpha(0);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.GONE);
                }
            }, 400);
        }
    }


}
