package com.html5killer.utility;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class CoverFlow extends Gallery {
    private Camera mCamera;
    private int mCoveflowCenter;
    private int mMaxRotationAngle;
    private int mMaxZoom;

    public CoverFlow(Context context) {
        super(context);
        this.mCamera = new Camera();
        this.mMaxRotationAngle = 60;
        this.mMaxZoom = -120;
        setStaticTransformationsEnabled(true);
        setBackgroundColor(MEASURED_STATE_MASK);
    }

    public CoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCamera = new Camera();
        this.mMaxRotationAngle = 60;
        this.mMaxZoom = -120;
        setStaticTransformationsEnabled(true);
        setBackgroundColor(MEASURED_STATE_MASK);
    }

    public CoverFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCamera = new Camera();
        this.mMaxRotationAngle = 60;
        this.mMaxZoom = -120;
        setStaticTransformationsEnabled(true);
        setBackgroundColor(MEASURED_STATE_MASK);
    }

    public int getMaxRotationAngle() {
        return this.mMaxRotationAngle;
    }

    public void setMaxRotationAngle(int maxRotationAngle) {
        this.mMaxRotationAngle = maxRotationAngle;
    }

    public int getMaxZoom() {
        return this.mMaxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        this.mMaxZoom = maxZoom;
    }

    private int getCenterOfCoverflow() {
        return (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2) + getPaddingLeft();
    }

    private static int getCenterOfView(View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }

    protected boolean getChildStaticTransformation(View child, Transformation t) {
        int childCenter = getCenterOfView(child);
        int childWidth = child.getWidth();
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);
        if (childCenter == this.mCoveflowCenter) {
            transformImageBitmap((ImageView) child, t, 0);
        } else {
            int rotationAngle = (int) ((((float) (this.mCoveflowCenter - childCenter)) / ((float) childWidth)) * ((float) this.mMaxRotationAngle));
            if (Math.abs(rotationAngle) > this.mMaxRotationAngle) {
                if (rotationAngle < 0) {
                    rotationAngle = -this.mMaxRotationAngle;
                } else {
                    rotationAngle = this.mMaxRotationAngle;
                }
            }
            transformImageBitmap((ImageView) child, t, rotationAngle);
        }
        return true;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mCoveflowCenter = getCenterOfCoverflow();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void transformImageBitmap(ImageView child, Transformation t, int rotationAngle) {
        this.mCamera.save();
        Matrix imageMatrix = t.getMatrix();
        int imageHeight = child.getLayoutParams().height;
        int imageWidth = child.getLayoutParams().width;
        int rotation = Math.abs(rotationAngle);
        this.mCamera.translate(0.0f, 0.0f, 100.0f);
        if (rotation < this.mMaxRotationAngle) {
            this.mCamera.translate(0.0f, 0.0f, (float) (((double) this.mMaxZoom) + (((double) rotation) * 1.5d)));
        }
        this.mCamera.rotateY((float) rotationAngle);
        this.mCamera.getMatrix(imageMatrix);
        imageMatrix.preTranslate((float) (-(imageWidth / 2)), (float) (-(imageHeight / 2)));
        imageMatrix.postTranslate((float) (imageWidth / 2), (float) (imageHeight / 2));
        this.mCamera.restore();
    }
}
