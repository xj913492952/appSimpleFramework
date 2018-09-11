package com.style.view.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.style.framework.R;
import com.style.utils.Utils;

/**
 * 类似仪表盘百分比进度 View
 */
public class SportDayCircleView extends BaseProgressBar {
    private Paint paint;
    protected Paint textPaint;

    private RectF rectF = new RectF();

    private float strokeWidth;

    private int max;
    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private int smallCircleStrokeColor = 0x99CCCCCC;
    private int smallCircleColor = 0xFFFFFFFF;
    private float arcFinishedStartAngle;

    private final int default_finished_color = Color.WHITE;
    private final int default_unfinished_color = Color.rgb(72, 106, 176);

    private final float default_stroke_width;
    private final int default_max = 100;

    private float radius;

    public SportDayCircleView(Context context) {
        this(context, null);
    }

    public SportDayCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SportDayCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        default_stroke_width = Utils.dp2px(context, 4);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcProgress, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    protected void initByAttributes(TypedArray attributes) {
        finishedStrokeColor = attributes.getColor(R.styleable.ArcProgress_arc_finished_color, default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.ArcProgress_arc_unfinished_color, default_unfinished_color);
        arcFinishedStartAngle = attributes.getFloat(R.styleable.ArcProgress_arc_finished_start_angle, arcFinishedStartAngle);
        setMax(attributes.getInt(R.styleable.ArcProgress_arc_max, default_max));
        setProgress(attributes.getInt(R.styleable.ArcProgress_arc_progress, 0));
        strokeWidth = attributes.getDimension(R.styleable.ArcProgress_arc_stroke_width, default_stroke_width);
    }

    protected void initPainters() {
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(default_unfinished_color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        rectF.set(strokeWidth / 2f, strokeWidth / 2f, width - strokeWidth / 2f, MeasureSpec.getSize(heightMeasureSpec) - strokeWidth / 2f);
        radius = width / 2f;
        float angle = 180;
        float arcBottomHeight = radius * (float) (1 - Math.cos(Math.toRadians(angle)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        //以画布中心点旋转坐标系
        canvas.rotate(arcFinishedStartAngle, getWidth() / 2, getHeight() / 2);
        float finishedSweepAngle = getProgress() / (float) getMax() * 360;
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(unfinishedStrokeColor);
        canvas.drawArc(rectF, 0, 360, false, paint);
        paint.setColor(finishedStrokeColor);
        canvas.drawArc(rectF, 0, finishedSweepAngle, false, paint);

        canvas.translate(radius, radius);
        float r = radius - strokeWidth / 2;
        //弧度，单位π
        double radian = Math.toRadians(finishedSweepAngle);
        float x = (float) (r * Math.cos(radian));
        float y = (float) (r * Math.sin(radian));
        float r2 = strokeWidth / 2 + strokeWidth / 3;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(smallCircleColor);
        canvas.drawCircle(x, y, r2, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(smallCircleStrokeColor);
        canvas.drawCircle(x, y, r2, paint);
        canvas.restore();

    }
}
