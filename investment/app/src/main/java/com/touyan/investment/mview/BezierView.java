package com.touyan.investment.mview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.touyan.investment.R;

/**
 * Description : custom layout to draw bezier
 */
public class BezierView extends FrameLayout {

    public final static int NO_VALUE = -9999;//不改变位置

    // 默认定点圆半径
    public static final float DEFAULT_RADIUS = 25;

    private Paint paint;
    private Path path;

    private int Offset = 15;

    // 手势坐标
    float x = 300;
    float y = 300;

    // 锚点坐标
    float anchorX = 200;
    float anchorY = 300;

    // 起点坐标
    float startX = -180;
    float startY = -150;

    // 定点圆半径
    public float radius = DEFAULT_RADIUS;

    // 判断动画是否开始
    boolean isAnimStart;

    // 判断是否开始拖动
    boolean isTouch;

    ImageView exploredImageView;
    TextView tipImageView;

    public interface EndOnBack {
        void endOnBack();
    }

    private EndOnBack endOnBack;

    public void setEndOnBack(EndOnBack endOnBack) {
        this.endOnBack = endOnBack;
    }

    public BezierView(Context context) {
        super(context);
        init(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Drawable textBg = null;
        int textColos = 0;
        int pathColos = 0;
        if (attrs != null) {
            // Styleables from XML
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BezierView);
            startX = a.getDimension(R.styleable.BezierView_offsetX, 0);
            float offsetY = a.getDimension(R.styleable.BezierView_offsetY, 0);
            float statusBarOffset = a.getDimension(R.styleable.BezierView_statusBarOffset, 0);
            startY = offsetY + statusBarOffset;
            textBg = a.getDrawable(R.styleable.BezierView_viewBg);
            textColos = a.getColor(R.styleable.BezierView_numColos, 0xfff12e40);
            pathColos = a.getColor(R.styleable.BezierView_pathColos, 0xffffffff);
            a.recycle();
        } else {
            startX = getContext().getResources().getDimension(R.dimen.tip_offset_x);
            startY = getContext().getResources().getDimension(R.dimen.tip_offset_y);
            textColos = 0xfff12e40;
            pathColos = 0xffffffff;

        }
        if(textBg==null){
            textBg = context.getResources().getDrawable(R.drawable.skin_tips_message_white);
        }

        path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(pathColos);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        exploredImageView = new ImageView(getContext());
        exploredImageView.setLayoutParams(params);
        exploredImageView.setImageResource(R.drawable.tip_anim);
        exploredImageView.setVisibility(View.INVISIBLE);

        tipImageView = new TextView(getContext());
        tipImageView.setTextColor(textColos);
        tipImageView.setText("0");
        tipImageView.setTextSize(10);
        tipImageView.setLayoutParams(params);
        tipImageView.setGravity(Gravity.CENTER);
        tipImageView.setVisibility(View.INVISIBLE);
        tipImageView.setBackground(textBg);

        addView(tipImageView);
        addView(exploredImageView);
    }

    public void setMessageAttr(int textColos,int pathColos,int textBg){
        if(textColos>0){
            tipImageView.setTextColor(textColos);
        }
        if(pathColos>0){
            paint.setColor(pathColos);
        }
        if(textBg>0){
            tipImageView.setBackgroundResource(textBg);
        }
    }

    public void setNewMessage(String message, float x, float y) {
        if(x!=NO_VALUE) {
            startX = x;
            anchorX = startX;
            this.x = startX;
        }
        if(y!=NO_VALUE) {
            startY = y;
            anchorY = startY;
            this.y = startY;
        }
        isTouch = false;
        isAnimStart = false;
        tipImageView.setText(message);
        tipImageView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        exploredImageView.setX(startX - exploredImageView.getWidth() / 2);
        exploredImageView.setY(startY - exploredImageView.getHeight() / 2);

        tipImageView.setX(startX - tipImageView.getWidth() / 2);
        tipImageView.setY(startY - tipImageView.getHeight() / 2);

        super.onLayout(changed, left, top, right, bottom);
    }


    private void calculate() {
        float distance = (float) Math.sqrt(Math.pow(y - startY, 2) + Math.pow(x - startX, 2));
        radius = -distance / 20 + DEFAULT_RADIUS;

        if (radius < 8) {
            endAnimation();
        }

        // 根据角度算出四边形的四个点
        float offsetX = (float) (radius * Math.sin(Math.atan((y - startY) / (x - startX))));
        float offsetY = (float) (radius * Math.cos(Math.atan((y - startY) / (x - startX))));

        float x1 = startX - offsetX;
        float y1 = startY + offsetY;

        float x2 = x - offsetX;
        float y2 = y + offsetY;

        float x3 = x + offsetX;
        float y3 = y - offsetY;

        float x4 = startX + offsetX;
        float y4 = startY - offsetY;

        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(anchorX, anchorY, x2, y2);
        path.lineTo(x3, y3);
        path.quadTo(anchorX, anchorY, x4, y4);
        path.lineTo(x1, y1);

        // 更改图标的位置
        tipImageView.setX(x - tipImageView.getWidth() / 2);
        tipImageView.setY(y - tipImageView.getHeight() / 2);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (isAnimStart || !isTouch) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
        } else {
            calculate();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);
            canvas.drawPath(path, paint);
            canvas.drawCircle(startX, startY, radius, paint);
            canvas.drawCircle(x, y, radius, paint);
        }
        super.dispatchDraw(canvas);
    }

    private void endAnimation() {
        isAnimStart = true;
        exploredImageView.setVisibility(View.VISIBLE);
        exploredImageView.setImageResource(R.drawable.tip_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) exploredImageView.getDrawable();
        animationDrawable.stop();
        animationDrawable.start();
        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {

            duration += animationDrawable.getDuration(i);

        }

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            public void run() {

                if (null != endOnBack) {
                    endOnBack.endOnBack();
                }

            }

        }, duration);
        tipImageView.setVisibility(View.GONE);
        this.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            getParent().requestDisallowInterceptTouchEvent(true);
            // 判断触摸点是否在tipImageView中
            Rect rect = new Rect();
            int[] location = new int[2];
            tipImageView.getDrawingRect(rect);
            tipImageView.getLocationOnScreen(location);
            rect.left = location[0] - Offset;
            rect.top = location[1] - Offset;
            rect.right = rect.right + location[0] + Offset;
            rect.bottom = rect.bottom + location[1] + Offset;
            int rawX = (int) event.getRawX();
            int rawY = (int) event.getRawY();
            if (!rect.contains(rawX, rawY)) {
                return false;
            }
            isTouch = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            getParent().requestDisallowInterceptTouchEvent(false);
            isTouch = false;
            tipImageView.setX(startX - tipImageView.getWidth() / 2);
            tipImageView.setY(startY - tipImageView.getHeight() / 2);
        }
        if (isAnimStart) {
            return true;
        }
        anchorX = (event.getX() + startX) / 2;
        anchorY = (event.getY() + startY) / 2;
        x = event.getX();
        y = event.getY();
        postInvalidate();
        return true;
    }

}
