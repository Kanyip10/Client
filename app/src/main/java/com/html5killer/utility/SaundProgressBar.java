package com.html5killer.utility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.html5killer.R;


public class SaundProgressBar extends ProgressBar {
    public SaundProgressBar(Context context) {
        super(context);
    }

    public SaundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SaundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected synchronized void onDraw(Canvas canvas) {
        updateProgressBar();
        super.onDraw(canvas);
    }

    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        invalidate();
    }

    private float getScale(int progress) {
        return getMax() > 0 ? ((float) progress) / ((float) getMax()) : 0.0f;
    }

    private void updateProgressBar() {
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable != null && (progressDrawable instanceof LayerDrawable)) {
            LayerDrawable d = (LayerDrawable) progressDrawable;
            float scale = getScale(getProgress());
            Drawable progressBar = d.findDrawableByLayerId(R.id.progress);
            int width = d.getBounds().right - d.getBounds().left;
            if (progressBar != null) {
                Rect progressBarBounds = progressBar.getBounds();
                progressBarBounds.right = progressBarBounds.left + ((int) ((((float) width) * scale) + 0.5f));
                progressBar.setBounds(progressBarBounds);
            }
            Drawable patternOverlay = d.findDrawableByLayerId(R.id.pattern);
            if (patternOverlay == null) {
                return;
            }
            Rect patternOverlayBounds;
            if (progressBar != null) {
                patternOverlayBounds = progressBar.copyBounds();
                int left = patternOverlayBounds.left;
                int right = patternOverlayBounds.right;
                if (left + 1 <= right) {
                    left++;
                }
                patternOverlayBounds.left = left;
                if (right > 0) {
                    right--;
                }
                patternOverlayBounds.right = right;
                patternOverlay.setBounds(patternOverlayBounds);
                return;
            }
            patternOverlayBounds = patternOverlay.getBounds();
            patternOverlayBounds.right = patternOverlayBounds.left + ((int) ((((float) width) * scale) + 0.5f));
            patternOverlay.setBounds(patternOverlayBounds);
        }
    }
}
