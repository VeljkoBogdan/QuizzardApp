package com.veljkobogdan.quizzardapp.util;

import android.content.Context;

public class DisplayUtil {
    public static int dpToPx(Context context, int dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

}
