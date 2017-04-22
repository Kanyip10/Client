package com.html5killer.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;


import com.html5killer.R;

import java.io.IOException;

public class Common {
    public static Drawable loadImageFromAsset1(Context ctx, String image) {
        try {
            return Drawable.createFromStream(ctx.getAssets().open(image), null);
        } catch (IOException e) {
            return ctx.getResources().getDrawable(R.drawable.logo);
        }
    }

    public static Bitmap loadImageFromAsset(Context ctx, String image) {
        try {
            return BitmapFactory.decodeStream(ctx.getAssets().open(image));
        } catch (IOException e) {
            return BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo);
        }
    }
}
