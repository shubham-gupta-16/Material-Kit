package com.shubhamgupta16.materialkit;

import android.content.Context;

public class UtilsKit {

    public static float dpToPx(float dp, Context context) {
        return dp * ((float)context.getResources().getDisplayMetrics().densityDpi / 160.0F);
    }

}
