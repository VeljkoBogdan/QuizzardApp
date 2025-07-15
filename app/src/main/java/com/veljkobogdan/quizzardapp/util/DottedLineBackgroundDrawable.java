package com.veljkobogdan.quizzardapp.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DottedLineBackgroundDrawable extends Drawable {
    private final Paint horizontalPaint = new Paint();
    private final Paint verticalPaint = new Paint();

    public DottedLineBackgroundDrawable() {
        horizontalPaint.setColor(Color.GRAY);
        horizontalPaint.setStyle(Paint.Style.STROKE);
        horizontalPaint.setStrokeWidth(2f);
        horizontalPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));

        verticalPaint.setColor(Color.GRAY);
        verticalPaint.setStyle(Paint.Style.STROKE);
        verticalPaint.setStrokeWidth(2f);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int height = getBounds().height();
        int width = getBounds().width();
        int lineCount = 17;
        int verticalLineCount = 7;

        float spacing = height / (float) lineCount;
        float offset = 22f;
        for (int i = 1; i <= lineCount; i++) {
            float y = i * spacing - offset;
            canvas.drawLine(0, y, width, y, horizontalPaint);
        }

        float spacingX = width / (float) verticalLineCount;
        float startOffsetY = 48f;
        for (int i = 0; i <= verticalLineCount; i++) {
            float x = i * spacingX ;
            canvas.drawLine(x, startOffsetY, x, height, verticalPaint);
        }
    }

    @Override public void setAlpha(int alpha) {
        horizontalPaint.setAlpha(alpha);
        verticalPaint.setAlpha(alpha);
    }
    @Override public void setColorFilter(@Nullable ColorFilter colorFilter) {
        horizontalPaint.setColorFilter(colorFilter);
        verticalPaint.setColorFilter(colorFilter);
    }
    @Override public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
