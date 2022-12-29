package com.wk.magnifyingView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MagnifierView extends RelativeLayout {
    private Bitmap mBitmap;
    private boolean mIsShowMagnifier = false;//是否显示放大镜
    private float xs = 0f;
    private float ys = 0f;
    private Paint paint;
    private final Path path = new Path();
    private final Matrix matrix = new Matrix();//矩阵
    float x = 50f, y = 50f, size = 400f, scale = 2;

    public MagnifierView(@NonNull Context context) {
        this(context, null);
    }

    public MagnifierView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagnifierView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //绘制放大镜边缘投影的画笔
        paint = new Paint();
        path.addCircle(x + size / 2f, y + size / 2f, size / 2f, Path.Direction.CW);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mIsShowMagnifier) {
            canvas.clipPath(path);
            matrix.setScale(scale, scale);
            matrix.postTranslate(-(xs * (mBitmap.getWidth() * scale)) + x + size / 2f,
                    -(ys * (mBitmap.getHeight() * scale)) + y + size / 2f);
            canvas.drawBitmap(mBitmap, matrix, paint);
        } else {
            super.dispatchDraw(canvas);
        }
    }

    public void setTouch(View v, MotionEvent event) {
        if (null == mBitmap) {
            mBitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(mBitmap);
            v.draw(canvas);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xs = event.getX() / v.getWidth();
                ys = event.getY() / v.getHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                mIsShowMagnifier = true;
                xs = event.getX() / v.getWidth();
                ys = event.getY() / v.getHeight();
                break;
            case MotionEvent.ACTION_UP:
                mIsShowMagnifier = false;
                break;
        }
        postInvalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mBitmap) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }
}
