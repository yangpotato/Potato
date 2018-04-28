package com.yang.potato.potato.custView;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by potato on 2018/1/11.
 */

public class LoginAnimationView extends View {
    //背景paint
    private Paint paint;
    //文字paint
    private Paint textPaint;
    //view矩形的大小
    private RectF rectF = new RectF();
    //文字所在的矩形
    private Rect textRect = new Rect();
    //圆角矩形到椭圆动画
    private ValueAnimator setRectToAngle;
    //椭圆到圆动画
    private ValueAnimator setAngleToCircle;
    //圆到椭圆动画
    private ValueAnimator setCircleToAngle;
    //椭圆到圆角矩形动画
    private ValueAnimator setAngleToRect;
    //颜色渐变动画
    private ValueAnimator setMoveAnimation;

    private AnimatorSet animatorSet = new AnimatorSet();
    private AnimatorSet animatorSet1 = new AnimatorSet();

    //View的高度
    private int height;
    //View的宽度
    private int width;
    //两圆心的距离
    private int two_circle_distance;
    //圆移动的距离
    private int distance;
    //文字大小
    private int textSize = 35;
    //圆角大小
    private int circleAngle = 30;
    //动画速度
    private int duration = 1000;
    private int colorEnd;
    private int colorStart;

    //背景颜色
    private String bg_color = "#cc3B9CD8";
    //文字颜色
    private String text_color = "#ffffff";
    //文字
    private String text = "登  录";

    private LoginButtonListener mLoginButtonListener;

    public void setmLoginButtonListener(LoginButtonListener loginButtonListener) {
        mLoginButtonListener = loginButtonListener;
    }

    public LoginAnimationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();

        //点击事件回调
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginButtonListener.onClickListener();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRectF(canvas);
        drawText(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        //变成圆所移动的最大距离
        distance = (w - h) / 2;

        initAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 初始化Paint
     */
    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(bg_color));
        paint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor(text_color));
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        setRectToAngle();
        setAngleToCircle();
        setCircleToAngle();
        setAngleToRect();
        setMoveAnimation();

        animatorSet.play(setAngleToCircle).before(setMoveAnimation).after(setRectToAngle);

        animatorSet1.play(setCircleToAngle).before(setAngleToRect);

    }

    /**
     * 开始动画
     */
    public void start() {
        animatorSet.start();
        this.setEnabled(false);
    }

    /**
     * 回退动画
     */
    public void back() {
        animatorSet.cancel();
        animatorSet1.start();
        this.setEnabled(true);
    }

    public void setText(String text){
        this.text = text;
    }


    /**
     * 绘制圆角矩形
     *
     * @param canvas
     */
    private void drawRectF(Canvas canvas) {
        rectF.left = two_circle_distance;
        rectF.top = 0;
        rectF.right = width - two_circle_distance;
        rectF.bottom = height;
        canvas.drawRoundRect(rectF, circleAngle, circleAngle, paint);
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        textRect.left = two_circle_distance;
        textRect.top = 0;
        textRect.right = width - two_circle_distance;
        textRect.bottom = height;

        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;

        canvas.drawText(text, textRect.centerX(), baseline, textPaint);
    }

    /**
     * 圆角矩形到椭圆动画
     */
    private void setRectToAngle() {
        setRectToAngle = ValueAnimator.ofInt(circleAngle, height / 2);
        setRectToAngle.setDuration(duration);
        setRectToAngle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                circleAngle = (int) valueAnimator.getAnimatedValue();
                //Log.d("popp","angle:"+circleAngle);
                invalidate();
            }
        });
    }

    /**
     * 椭圆到圆的动画
     */
    private void setAngleToCircle() {
        setAngleToCircle = ValueAnimator.ofInt(0, distance);
        setAngleToCircle.setDuration(duration);
        setAngleToCircle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                two_circle_distance = (int) valueAnimator.getAnimatedValue();
                //文字设置渐变透明
                int alpha = 255 - (two_circle_distance * 255) / distance;
                textPaint.setAlpha(alpha);

                invalidate();
            }
        });
    }

    /**
     * 圆到椭圆的动画
     */
    private void setCircleToAngle() {
        setCircleToAngle = ValueAnimator.ofInt(distance, 0);
        setCircleToAngle.setDuration(duration);
        setCircleToAngle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                two_circle_distance = (int) valueAnimator.getAnimatedValue();
                //文字设置渐变透明
                int alpha = 255 - (two_circle_distance * 255) / distance;
                textPaint.setAlpha(alpha);
                text = "登  录";
                invalidate();
            }
        });
    }

    /**
     * 椭圆到圆角矩形动画
     */
    private void setAngleToRect() {
        setAngleToRect = ValueAnimator.ofInt(height / 2, circleAngle);
        setAngleToRect.setDuration(duration);
        setAngleToRect.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                circleAngle = (int) valueAnimator.getAnimatedValue();
                //Log.d("popp","angle:"+circleAngle);
                invalidate();
            }
        });
    }

    /**
     * 伸张动画
     */
    private void setMoveAnimation(){
        setMoveAnimation = ValueAnimator.ofInt(distance,distance/2,distance);
        setMoveAnimation.setDuration(2000);
        setMoveAnimation.setRepeatCount(-1);
        setMoveAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                two_circle_distance = (int) valueAnimator.getAnimatedValue();
                int alpha = 255 - (two_circle_distance * 255) / distance;
                textPaint.setAlpha(alpha);
                text = "Loading";
                invalidate();
            }
        });
    }


    /**
     * 回调接口
     */
    public interface LoginButtonListener {
        void onClickListener();
    }
}
