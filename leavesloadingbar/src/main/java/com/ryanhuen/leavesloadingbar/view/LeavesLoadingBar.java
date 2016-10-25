
package com.ryanhuen.leavesloadingbar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ryanhuenwork on 16-10-25.
 */

public class LeavesLoadingBar extends View {
    private int mCurrentProgressPostion;
    private int mProgressWidth;
    // ProgessBar的弧度
    private float mArcRadius;

    //画笔
    Paint mPaintRectStroke;
    Paint mPaintRectFill;
    Paint mPaintRadiusFill;

    public LeavesLoadingBar(Context context) {
        super(context);
        initPaint();
    }


    public LeavesLoadingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public LeavesLoadingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private int mWidth, mHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void initPaint() {
        mPaintRectStroke = new Paint();
        mPaintRectStroke.setStyle(Paint.Style.STROKE);
        mPaintRectFill = new Paint();
        mPaintRectFill.setStyle(Paint.Style.FILL);
        mPaintRadiusFill = new Paint();
        mPaintRadiusFill.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rectF = new RectF(-(mWidth / 6 * 2), -(mHeight / 24 * 2), mWidth / 6 * 2, mHeight / 24 * 2);
        canvas.drawRect(rectF, mPaintRectStroke);
        canvas.drawArc(rectF, 90, 180, false, mPaintRadiusFill);

    }
}
