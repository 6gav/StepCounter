package com.usfit.stepcounter;

import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AliasingDrawableWrapper extends DrawableWrapper {
    private static final DrawFilter DRAW_FILTER =
            new PaintFlagsDrawFilter(Paint.FILTER_BITMAP_FLAG, 0);

    public AliasingDrawableWrapper(Drawable wrapped) {
        super(wrapped);
    }

    @Override
    public void draw(Canvas canvas) {
        DrawFilter oldDrawFilter = canvas.getDrawFilter();
        canvas.setDrawFilter(DRAW_FILTER);
        super.draw(canvas);
        canvas.setDrawFilter(oldDrawFilter);
    }
}
