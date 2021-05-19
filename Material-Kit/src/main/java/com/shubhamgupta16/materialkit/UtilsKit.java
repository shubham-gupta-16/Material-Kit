package com.shubhamgupta16.materialkit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class UtilsKit {

    public static float dpToPx(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / 160.0F);
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

    public static void fadeVisibleView(final View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0f);
            view.animate().setDuration(400).alpha(1);
        }
    }

    public static void fadeHideView(final View view) {
        if (view.getVisibility() != View.GONE) {
            view.animate().setDuration(400).alpha(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                    view.setAlpha(1f);
                }
            });
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    view.setVisibility(View.GONE);
//                }
//            }, 400);
        }
    }

    public static void circleReveal(View view, boolean isShow, int cx, int cy, int radius) {
        Animator anim;
        if (isShow) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, radius);
            view.setVisibility(View.VISIBLE);
        } else {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                }
            });
        }
        anim.start();
    }

    public static void circleReveal(View view, boolean isShow, int cx, int cy) {
        int radius = (int) Math.sqrt(Math.pow(view.getMeasuredWidth(), 2) + Math.pow(view.getMeasuredHeight(), 2));
        circleReveal(view, isShow, cx, cy, radius);
        Log.d("tagtag",  radius + "");
    }

}
