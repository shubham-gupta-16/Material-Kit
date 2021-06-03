package com.shubhamgupta16.materialkit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;

public class AnimUtils {

    public static void fadeVisibleView(final View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            view.setAlpha(0f);
            view.animate().setDuration(400).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setAlpha(1);
                }
            });
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
        }
    }

    public static Animator circleReveal(View view, int cx, int cy, int radius) {
        return _circleReveal(view, true, cx, cy, radius);
    }

    public static Animator circleUnReveal(View view, int cx, int cy, int radius) {
        return _circleReveal(view, false, cx, cy, radius);
    }

    public static Animator circleReveal(View view, int cx, int cy) {
        return _circleReveal(view, true, cx, cy);
    }

    public static Animator circleUnReveal(View view, int cx, int cy) {
        return _circleReveal(view, false, cx, cy);
    }

    static Animator _circleReveal(View view, boolean isShow, int cx, int cy, int radius) {
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
        return anim;
    }

    static Animator _circleReveal(View view, boolean isShow, int cx, int cy) {
        int radius = (int) Math.sqrt(Math.pow(view.getMeasuredWidth(), 2) + Math.pow(view.getMeasuredHeight(), 2));
        return _circleReveal(view, isShow, cx, cy, radius);
    }

    public static void scaleAnimation(final View v, float from, float to, int dur) {
        scaleAnimation(v, from, to, dur, 1, false);
    }
    public static void scaleAnimation(final View v, float from, float to, int dur, Interpolator interpolator) {
        scaleAnimation(v, from, to, dur, 1, false, interpolator);
    }

    public static void scaleAnimation(final View v, float from, float to, int dur, int repeatCount, boolean reverseRepeat) {
        scaleAnimation(v,from, to, dur,repeatCount, reverseRepeat, new LinearInterpolator());
    }

    public static ValueAnimator.AnimatorUpdateListener heightAnimListener(View view){
        return _valueAnimationListener(view, 0);
    }

    public static ValueAnimator.AnimatorUpdateListener widthAnimListener(View view){
        return _valueAnimationListener(view, 1);
    }


    private static ValueAnimator.AnimatorUpdateListener _valueAnimationListener(View view, int hOrW){
        return new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (hOrW == 0)
                    layoutParams.height = val;
                else
                    layoutParams.width = val;
                view.setLayoutParams(layoutParams);
            }
        };
    }

    public static void scaleAnimation(final View v, float from, float to, int dur, int repeatCount, boolean reverseRepeat, Interpolator interpolator) {
        final int[] count = {0};
        final ScaleAnimation fade_in = new ScaleAnimation(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(dur);
        fade_in.setFillAfter(true);
        fade_in.setInterpolator(interpolator);
        fade_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (count[0] < repeatCount - 1) {
                    if (reverseRepeat && repeatCount % 2 == 0)
                        fade_in.setInterpolator(new ReverseInterpolator(fade_in.getInterpolator()));
                    v.startAnimation(fade_in);
                    count[0]++;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(fade_in);
    }

    public static class ReverseInterpolator implements Interpolator {
        private final Interpolator delegate;

        public ReverseInterpolator(Interpolator delegate) {
            this.delegate = delegate;
        }

        public ReverseInterpolator() {
            this(new LinearInterpolator());
        }

        @Override
        public float getInterpolation(float input) {
            return 1 - delegate.getInterpolation(input);
        }
    }

}
