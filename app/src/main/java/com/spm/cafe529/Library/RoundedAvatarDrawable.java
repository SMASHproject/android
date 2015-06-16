package com.spm.cafe529.Library;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by sungho2 on 2015-06-14.
 */

public class RoundedAvatarDrawable extends Drawable {
    private final Bitmap mBitmap;
    private final Paint mPaint;
    private final Paint mOutlinePaint;
    private final RectF mRectF;
    private final int mBitmapWidth;
    private final int mBitmapHeight;

    public RoundedAvatarDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        mRectF = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);

        mOutlinePaint = new Paint();
        mOutlinePaint.setColor(Color.rgb(0x8b, 0x59, 0x27));
        mOutlinePaint.setAntiAlias(true);
        mOutlinePaint.setDither(true);

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        RectF lineRectF = new RectF();
        lineRectF.set(mRectF.left + 6, mRectF.top + 6, mRectF.right - 6, mRectF.bottom - 6);
        canvas.drawOval(mRectF,mOutlinePaint);
        canvas.drawOval(lineRectF, mPaint);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        mRectF.set(bounds);
    }

    @Override
    public void setAlpha(int alpha) {
        if (mPaint.getAlpha() != alpha) {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmapWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmapHeight;
    }

    public void setAntiAlias(boolean aa) {
        mPaint.setAntiAlias(aa);
        invalidateSelf();
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        mPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override
    public void setDither(boolean dither) {
        mPaint.setDither(dither);
        invalidateSelf();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }


}