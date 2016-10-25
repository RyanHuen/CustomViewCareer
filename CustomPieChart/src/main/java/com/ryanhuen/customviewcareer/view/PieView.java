
package com.ryanhuen.customviewcareer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ryanhuen.customviewcareer.model.PieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanhuenwork on 16-10-25.
 */

public class PieView extends View {
    private int[] mColors = {
            0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00
    };
    private Paint mPaintFill = new Paint();
    private Paint mPaintStroke = new Paint();
    private int mWidth, mHeight;
    private List<PieModel> mModels = new ArrayList<>();
    private int mStartAngle;

    public PieView(Context context) {
        super(context);
        initPaint();
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();

    }

    /**
     * 更新models信息后，需要进行重绘
     *
     * @param models
     */
    public void setModels(List<PieModel> models) {
        mModels = models;
        initModels();
        invalidate();
    }

    /**
     * 更新初始角度后，需要进行重绘
     * 
     * @param startAngle
     */
    public void setStartAngle(int startAngle) {
        mStartAngle = startAngle;
        invalidate();
    }

    private void initModels() {
        int totalValue = 0;
        for (int i = 0; i < mModels.size(); i++) {
            PieModel pieModel = mModels.get(i);
            totalValue += pieModel.getValue();
            int colorIndex = i % mColors.length;
            pieModel.setColor(mColors[colorIndex]);
        }
        for (int i = 0; i < mModels.size(); i++) {
            PieModel pieModel = mModels.get(i);
            float percent = pieModel.getValue() / totalValue;
            pieModel.setPercentage(percent);
            float angle = percent * 360;
            pieModel.setAngle(angle);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void initPaint() {
        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintStroke.setStyle(Paint.Style.STROKE);
        // 设置抗锯齿
        mPaintFill.setAntiAlias(true);
        mPaintStroke.setAntiAlias(true);
        mPaintStroke.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mModels == null || mModels.isEmpty()) {
            return;
        }
        canvas.translate(mWidth / 2, mHeight / 2);
        float currentStartAngle = mStartAngle;
        float rad = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rectF = new RectF(-rad, -rad, rad, rad);
        for (int i = 0; i < mModels.size(); i++) {
            PieModel pieModel = mModels.get(i);
            mPaintFill.setColor(pieModel.getColor());
            canvas.drawArc(rectF, currentStartAngle, pieModel.getAngle(), true, mPaintFill);
            currentStartAngle += pieModel.getAngle();
        }
        canvas.translate(-(mWidth / 2), -(mHeight / 2));
        canvas.drawCircle(mWidth / 2, mHeight / 2, rad, mPaintStroke);
    }
}
