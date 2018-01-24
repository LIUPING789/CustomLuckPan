package com.knight.customluckpan.weight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.knight.customluckpan.R;
import com.knight.customluckpan.util.Logger;
import com.knight.customluckpan.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description: ${TODO}
 * autour: Knight
 * new date: 24/01/2018 on 15:35
 * e-mail: 37442216knight@gmail.com
 * update: 24/01/2018 on 15:35
 * version: v 1.0
 */
public class CustomRotateLuckPan extends View {
    private Context context;
    private int panNum = 0;
    private Paint wfPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint hPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint wPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int InitAngle = 0;
    private int radius = 0;
    private int verPanRadius;
    private int diffRadius;
    private Integer[] images;
    private String[] strs;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private ScrollerCompat scroller;
    private int screenWidth, screeHeight;
    private static final long ONE_WHEEL_TIME = 500;
    private int INSIDE_CIRCLE_INDENTATION = 32;

    public CustomRotateLuckPan(Context context) {
        this(context, null);
    }

    public CustomRotateLuckPan(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ResourceAsColor")
    public CustomRotateLuckPan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        screeHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        scroller = ScrollerCompat.create(context);
        checkPanState(context, attrs);
        InitAngle = 360 / panNum;
        verPanRadius = 360 / panNum;
        diffRadius = verPanRadius / 2;
        wfPaint.setColor(Color.rgb(252, 252, 252));
        wPaint.setStrokeWidth(10);
        hPaint.setColor(Color.rgb(248, 221, 50));
        wPaint.setColor(Color.rgb(255, 255, 255));
        lPaint.setColor(Color.rgb(100, 193, 201));

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(Util.dip2px(context, 14));
        setClickable(true);
        for (int i = 0; i < panNum; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), images[i]);
            bitmaps.add(bitmap);
        }
    }


    private void checkPanState(Context context, AttributeSet attrs) {
        Logger.getLogger().d("start load luckpan resources ...");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.luckpan);
        panNum = typedArray.getInteger(R.styleable.luckpan_pannum, 0);
        if (360 % panNum != 0)
            throw new RuntimeException("can't split pan for all icon.");
        int nameArray = typedArray.getResourceId(R.styleable.luckpan_names, -1);
        if (nameArray == -1) throw new RuntimeException("Can't find pan name.");
        strs = context.getResources().getStringArray(nameArray);
        int iconArray = typedArray.getResourceId(R.styleable.luckpan_icons, -1);
        if (iconArray == -1) throw new RuntimeException("Can't find pan icon.");

        String[] iconStrs = context.getResources().getStringArray(iconArray);
        List<Integer> iconLists = new ArrayList<>();
        for (int i = 0; i < iconStrs.length; i++) {
            iconLists.add(context.getResources().getIdentifier(iconStrs[i], "mipmap", context.getPackageName()));
        }

        images = iconLists.toArray(new Integer[iconLists.size()]);
        Logger.getLogger().d(Arrays.toString(images));
        typedArray.recycle();
        if (strs == null || images == null)
            throw new RuntimeException("Can't find string or icon resources.");
        if (strs.length != panNum || images.length != panNum)
            throw new RuntimeException("The string length or icon length  isn't equals panNum.");
        Logger.getLogger().d("load luckpan resources successfully -_- ~");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int MinValue = Math.min(screenWidth, screeHeight);
        MinValue -= Util.dip2px(context, 38) * 2;
        setMeasuredDimension(MinValue, MinValue);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int MinValue = Math.min(width, height);
        radius = MinValue / 2;
        RectF rectF = new RectF(getPaddingLeft() + Util.dip2px(context, INSIDE_CIRCLE_INDENTATION), getPaddingTop() + Util.dip2px(context, INSIDE_CIRCLE_INDENTATION), width - Util.dip2px(context, INSIDE_CIRCLE_INDENTATION), height - Util.dip2px(context, INSIDE_CIRCLE_INDENTATION));
        RectF rectD = new RectF(getPaddingLeft(), getPaddingTop(), width, height);
        int angle = (panNum % 4 == 0) ? InitAngle : InitAngle - diffRadius;
        Logger.getLogger().d(String.valueOf(angle));

        for (int i = 0; i < panNum; i++) {
            if (i % 2 == 0) {
                canvas.drawArc(rectD, angle, verPanRadius, true, wPaint);
            } else if (i % 4 == 1) {
                canvas.drawArc(rectD, angle, verPanRadius, true, lPaint);
            } else {
                canvas.drawArc(rectD, angle, verPanRadius, true, hPaint);
            }
            angle += verPanRadius;
        }

        for (int i = 0; i < panNum; i++) {
            canvas.drawArc(rectF, angle, verPanRadius, true, wPaint);
            angle += verPanRadius;
        }

        for (int i = 0; i < panNum; i++) {
            drawIcon(width / 2, height / 2, radius, (panNum % 4 == 0) ? InitAngle + diffRadius : InitAngle, i, canvas);
            InitAngle += verPanRadius;
        }

        for (int i = 0; i < panNum; i++) {
            drawText((panNum % 4 == 0) ? InitAngle + diffRadius + (diffRadius * 3 / 4) : InitAngle + diffRadius, strs[i], 2 * radius, textPaint, canvas, rectD);
            InitAngle += verPanRadius;
        }
    }

    private void drawText(float startAngle, String string, int mRadius, Paint mTextPaint, Canvas mCanvas, RectF mRange) {
        Path path = new Path();

        path.addArc(mRange, startAngle, verPanRadius);
        float textWidth = mTextPaint.measureText(string);
        float hOffset = (panNum % 4 == 0) ? ((float) (mRadius * Math.PI / panNum / 2))
                : ((float) (mRadius * Math.PI / panNum / 2 - textWidth / 2));
        float vOffset = mRadius / 2 / 6;

        mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
    }

    private void drawIcon(int xx, int yy, int mRadius, float startAngle, int i, Canvas mCanvas) {

        int imgWidth = mRadius / 6;

        float angle = (float) Math.toRadians(verPanRadius + startAngle);

        float x = (float) (xx + (mRadius / 2 + mRadius / 12) * Math.cos(angle));
        float y = (float) (yy + (mRadius / 2 + mRadius / 12) * Math.sin(angle));

        RectF rect = new RectF(x - imgWidth * 2 / 3, y - imgWidth * 2 / 3, x + imgWidth
                * 2 / 3, y + imgWidth * 2 / 3);

        Bitmap bitmap = bitmaps.get(i);

        mCanvas.drawBitmap(bitmap, null, rect, null);
    }


    public void setImages(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
        this.invalidate();
    }

    public void setStr(String... strs) {
        this.strs = strs;
        this.invalidate();
    }


    protected void startRotate(int pos) {
        int lap = (int) (Math.random() * 12) + 4;
        int angle = 0;
        if (pos < 0) {
            angle = (int) (Math.random() * 360);
        } else {
            int initPos = queryPosition();
            if (pos > initPos) {
                angle = (pos - initPos) * verPanRadius;
                lap -= 1;
                angle = 360 - angle;
            } else if (pos < initPos) {
                angle = (initPos - pos) * verPanRadius;
            } else {

            }
        }

        int increaseDegree = lap * 360 + angle;
        long time = (lap + angle / 360) * ONE_WHEEL_TIME;
        int DesRotate = increaseDegree + InitAngle;

        //TODO 为了每次都能旋转到转盘的中间位置
        int offRotate = DesRotate % 360 % verPanRadius;
        DesRotate -= offRotate;
        DesRotate += diffRadius;

        ValueAnimator animtor = ValueAnimator.ofInt(InitAngle, DesRotate);
        animtor.setInterpolator(new AccelerateDecelerateInterpolator());
        animtor.setDuration(time);
        animtor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int updateValue = (int) animation.getAnimatedValue();
                InitAngle = (updateValue % 360 + 360) % 360;
                ViewCompat.postInvalidateOnAnimation(CustomRotateLuckPan.this);
            }
        });

        animtor.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (((LuckPanLayout) getParent()).getAnimationEndListener() != null) {
                    ((LuckPanLayout) getParent()).setStartBtnEnable(true);
                    ((LuckPanLayout) getParent()).setDelayTime(LuckPanLayout.DEFAULT_TIME_PERIOD);
                    ((LuckPanLayout) getParent()).getAnimationEndListener().endAnimation(queryPosition());
                }
            }
        });
        animtor.start();
    }


    private int queryPosition() {
        InitAngle = (InitAngle % 360 + 360) % 360;
        int pos = (InitAngle / verPanRadius);
        if (panNum == 4) pos++;
        return calcumAngle(pos);
    }

    private int calcumAngle(int pos) {
        if (pos >= 0 && pos <= panNum / 2) {
            pos = panNum / 2 - pos;
        } else {
            pos = (panNum - pos) + panNum / 2;
        }
        return pos;
    }


    @Override
    protected void onDetachedFromWindow() {
        clearAnimation();
        if (getParent() instanceof LuckPanLayout) {
            ((LuckPanLayout) getParent()).getHandler().removeCallbacksAndMessages(null);
        }
        super.onDetachedFromWindow();
    }

    public void setRotate(int rotation) {
        rotation = (rotation % 360 + 360) % 360;
        InitAngle = rotation;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            setRotate(scroller.getCurrY());
        }
        super.computeScroll();
    }
}
