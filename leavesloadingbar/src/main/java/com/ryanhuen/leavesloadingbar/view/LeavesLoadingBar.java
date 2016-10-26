
package com.ryanhuen.leavesloadingbar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.ryanhuen.leavesloadingbar.R;

/**
 * Created by ryanhuenwork on 16-10-25.
 */

public class LeavesLoadingBar extends View {
    /**
     * 总进度
     */
    private int mTotalProgress = 100;
    /**
     * 当前进度
     */
    private int mCurrentProgress;

    /**
     * 当前进度走到的长度
     */
    private int mCurrentProgressPostion;
    /**
     * 进度绘制区域长度
     */
    private int mProgressBarWidth;
    /**
     * 进度绘制区域高度
     */
    private int mProgressBarHeight;

    public LeavesLoadingBar(Context context) {
        this(context, null);
    }

    public LeavesLoadingBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * progressBar边框占据一定的像素，所以会有一个Margin
     */
    private int mBorderMargin, mBorderWindmillMargin;

    public LeavesLoadingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);
        initBitmap(context);
        mBorderMargin = context.getResources().getDimensionPixelSize(R.dimen.border_margin);
        mBorderWindmillMargin = context.getResources()
                .getDimensionPixelSize(R.dimen.border_winmill_margin);
    }

    private Bitmap mLeafBitmap;

    /**
     * 进度条边框
     */
    private Bitmap mBorderBitmap;
    /**
     * 提取边框bitmap区域大小
     */
    private Rect mBorderSrcRectF;
    private int mBorderWidth;
    private int mBorderHeight;
    /**
     * 绘制边框的区域大小
     */
    private RectF mBorderDstRectF;

    /**
     * 没有progress的进度区域(不包含圆弧部分)
     */
    private RectF mUnProgressRectF;
    /**
     * 已经Progress的进度区域（不包含圆弧部分）
     */
    private RectF mProgressedRectF;

    /**
     * 弧度部分结束区域
     */
    private float mArcEndLocation;
    /**
     * 弧度部分区域的半径
     */
    private float mArcAreaRadius;

    /**
     * 弧度绘制区域
     */
    private RectF mArcAreaRectF;

    /**
     * 画笔区域
     */
    private Paint mBitmapPaint;
    private Paint mUnProgressAreaPaint;
    private Paint mProgressedAreaPaint;

    private int mColorUnprogress = 0xFF7AAC5E;
    private int mColorProgressed = 0xffffa800;

    public int getColorUnprogress() {
        return mColorUnprogress;
    }

    public void setColorUnprogress(int colorUnprogress) {
        mColorUnprogress = colorUnprogress;
    }

    public int getColorProgressed() {
        return mColorProgressed;
    }

    public void setColorProgressed(int colorProgressed) {
        mColorProgressed = colorProgressed;
    }

    private void initPaint(Context context) {
        // 初始化位图画笔
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        // 初始化未进度区域画笔
        mUnProgressAreaPaint = new Paint();
        mUnProgressAreaPaint.setAntiAlias(true);
        mUnProgressAreaPaint.setColor(mColorUnprogress);

        // 初始化已进度区域画笔
        mProgressedAreaPaint = new Paint();
        mProgressedAreaPaint.setAntiAlias(true);
        mProgressedAreaPaint.setColor(mColorProgressed);

    }

    private void initBitmap(Context context) {
        mLeafBitmap = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.leaf))
                .getBitmap();
        mBorderBitmap = ((BitmapDrawable) context.getResources()
                .getDrawable(R.drawable.leaf_border))
                        .getBitmap();
        mBorderWidth = mBorderBitmap.getWidth();
        mBorderHeight = mBorderBitmap.getHeight();
    }

    /**
     * View的总宽高
     */
    private int mViewWidth, mViewHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 初始化View总宽高
        mViewWidth = w;
        mViewHeight = h;
        // 初始化进度绘制区域宽高
        mProgressBarWidth = mViewWidth - getPaddingLeft() - getPaddingRight() - mBorderMargin
                - mBorderWindmillMargin;
        mProgressBarHeight = mViewHeight - getPaddingTop() - getPaddingBottom() - mBorderMargin * 2;
        // 初始化圆弧半径
        mArcAreaRadius = mProgressBarHeight / 2;

        // 初始化进度条边框Bitmap截取区域大小
        mBorderSrcRectF = new Rect(0, 0, mBorderWidth, mBorderHeight);

        // 初始化进度条边框绘制区域的大小
        mBorderDstRectF = new RectF(getPaddingLeft(), getPaddingTop(),
                mViewWidth - getPaddingRight(),
                mViewHeight - getPaddingBottom());
        // 初始化progress绘制区域，分别包含已进度和未进度区域，已进度区域不包含弧度部分
        mUnProgressRectF = new RectF(getPaddingLeft() + mCurrentProgressPostion + mBorderMargin,
                mBorderMargin + getPaddingTop(),
                mViewWidth - getPaddingRight() - mBorderWindmillMargin,
                mViewHeight - getPaddingBottom() - mBorderMargin);
        mProgressedRectF = new RectF(getPaddingLeft() + mArcAreaRadius, getPaddingTop(),
                mCurrentProgressPostion, mViewHeight - getPaddingBottom());

        mArcAreaRectF = new RectF(getPaddingLeft() + mBorderMargin, getPaddingTop() + mBorderMargin,
                mBorderMargin + mArcAreaRadius * 2 + getPaddingBottom(),
                mViewHeight - mBorderMargin - getPaddingBottom());

        mArcEndLocation = getPaddingLeft() + mBorderMargin + mArcAreaRadius;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgress(canvas);
        drawBitmaps(canvas);

    }

    private void drawBitmaps(Canvas canvas) {
        canvas.drawBitmap(mBorderBitmap, mBorderSrcRectF, mBorderDstRectF, mBitmapPaint);
    }

    private void drawProgress(Canvas canvas) {
        if (mCurrentProgress >= mTotalProgress) {
            mCurrentProgress = 100;
        }
        mCurrentProgressPostion = (int) (mProgressBarWidth
                * (mCurrentProgress * 1f / mTotalProgress));
        if (mCurrentProgressPostion <= mArcAreaRadius) {
            // 当前进度还没有超过圆弧半径，需要绘制圆弧
            // 绘制未进度的弧形区域
            canvas.drawArc(mArcAreaRectF, 90, 180, false, mUnProgressAreaPaint);

            // 绘制未进度的矩形区域
            mUnProgressRectF.left = mArcEndLocation;
            canvas.drawRect(mUnProgressRectF, mUnProgressAreaPaint);

            // 绘制已进度的弧形区域
            int angle = (int) Math.toDegrees(
                    Math.acos((mArcAreaRadius - mCurrentProgressPostion) / mArcAreaRadius));
            int startAngle = 180 - angle;
            int sweepAngle = 2 * angle;
            canvas.drawArc(mArcAreaRectF, startAngle, sweepAngle, false, mProgressedAreaPaint);
        } else {
            // 超出圆弧半径，绘制矩形
        }

    }
}
