package com.html5killer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.google.android.gms.location.DetectedActivity;

public class TouchImage extends ImageView {
    Paint paint;
    Point point;

    class Point {
        float f50x;
        float f51y;

        Point() {
        }
    }

    public TouchImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        this.point = new Point();
        this.paint.setColor(getDrawingCacheBackgroundColor());
        this.paint.setStyle(Style.STROKE);
        this.paint.setStrokeWidth(4.0f);
    }

    protected void onDraw1(Canvas canvas) {
        super.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case DetectedActivity.IN_VEHICLE /*0*/:
                this.point.f50x = event.getX();
                this.point.f51y = event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
