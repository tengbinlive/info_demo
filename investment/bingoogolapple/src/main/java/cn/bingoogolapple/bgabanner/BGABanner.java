package cn.bingoogolapple.bgabanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.transformer.AccordionPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.AlphaPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.CubePageTransformer;
import cn.bingoogolapple.bgabanner.transformer.DefaultPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.DepthPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.FadePageTransformer;
import cn.bingoogolapple.bgabanner.transformer.FlipPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.RotatePageTransformer;
import cn.bingoogolapple.bgabanner.transformer.StackPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.ZoomCenterPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.ZoomFadePageTransformer;
import cn.bingoogolapple.bgabanner.transformer.ZoomPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.ZoomStackPageTransformer;

public class BGABanner extends RelativeLayout {
    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private BGAViewPager mViewPager;
    private List<View> mViews;
    private List<String> mTips;
    private LinearLayout mPointRealContainerLl;
    private TextView mTipTv;
    private List<ImageView> mPoints;
    private boolean mAutoPlayAble = true;
    private boolean mIsAutoPlaying = false;
    private int mAutoPlayInterval = 2000;
    private int mPageChangeDuration = 2000;
    private int mPointGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private int mPointLeftRightMargin;
    private int mPointTopBottomMargin;
    private int mPointContainerLeftRightPadding;
    private int mTipTextSize;
    private int mTipTextColor = Color.WHITE;
    private int mCurrentPoint = 0;
    private Drawable mPointFocusedDrawable;
    private Drawable mPointUnfocusedDrawable;
    private Drawable mPointContainerBackgroundDrawable;
    private Handler mPagerHandler;

    private Runnable mAutoPlayTask = new Runnable() {
        @Override
        public void run() {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            mPagerHandler.postDelayed(mAutoPlayTask, mAutoPlayInterval);
        }
    };

    public BGABanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BGABanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDefaultAttrs(context);
        initCustomAttrs(context, attrs);
        initView(context);
    }

    private void initDefaultAttrs(Context context) {
        mViewPager = new BGAViewPager(context);
        mPagerHandler = new Handler();
        mPointLeftRightMargin = dp2px(context, 3);
        mPointTopBottomMargin = dp2px(context, 6);
        mPointContainerLeftRightPadding = dp2px(context, 10);
        mTipTextSize = sp2px(context, 4);
        mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#44aaaaaa"));
        mPointFocusedDrawable = getResources().getDrawable(R.drawable.banner_shape_point_select);
        mPointUnfocusedDrawable = getResources().getDrawable(R.drawable.banner_shape_point_normal);
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BGABanner);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.BGABanner_banner_pointFocusedImg) {
            mPointFocusedDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.BGABanner_banner_pointUnfocusedImg) {
            mPointUnfocusedDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.BGABanner_banner_pointContainerBackground) {
            mPointContainerBackgroundDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.BGABanner_banner_pointLeftRightMargin) {
            /**
             * getDimension和getDimensionPixelOffset的功能差不多,都是获取某个dimen的值,如果是dp或sp的单位,将其乘以density,如果是px,则不乘;两个函数的区别是一个返回float,一个返回int. getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.
             */
            mPointLeftRightMargin = typedArray.getDimensionPixelOffset(attr, mPointLeftRightMargin);
        } else if (attr == R.styleable.BGABanner_banner_pointContainerLeftRightPadding) {
            mPointContainerLeftRightPadding = typedArray.getDimensionPixelOffset(attr, mPointContainerLeftRightPadding);
        } else if (attr == R.styleable.BGABanner_banner_pointTopBottomMargin) {
            mPointTopBottomMargin = typedArray.getDimensionPixelOffset(attr, mPointTopBottomMargin);
        } else if (attr == R.styleable.BGABanner_banner_pointGravity) {
            mPointGravity = typedArray.getInt(attr, mPointGravity);
        } else if (attr == R.styleable.BGABanner_banner_pointAutoPlayAble) {
            mAutoPlayAble = typedArray.getBoolean(attr, mAutoPlayAble);
        } else if (attr == R.styleable.BGABanner_banner_pointAutoPlayInterval) {
            mAutoPlayInterval = typedArray.getInteger(attr, mAutoPlayInterval);
        } else if (attr == R.styleable.BGABanner_banner_pageChangeDuration) {
            mPageChangeDuration = typedArray.getInteger(attr, mPageChangeDuration);
        } else if (attr == R.styleable.BGABanner_banner_transitionEffect) {
            int ordinal = typedArray.getInt(attr, TransitionEffect.Accordion.ordinal());
            setTransitionEffect(TransitionEffect.values()[ordinal]);
        } else if (attr == R.styleable.BGABanner_banner_tipTextColor) {
            mTipTextColor = typedArray.getColor(attr, mTipTextColor);
        } else if (attr == R.styleable.BGABanner_banner_tipTextSize) {
            mTipTextSize = typedArray.getDimensionPixelOffset(attr, mTipTextSize);
        }
    }

    private void initView(Context context) {
        addView(mViewPager, new RelativeLayout.LayoutParams(RMP, RMP));
        setPageChangeDuration(mPageChangeDuration);

        if (mPointFocusedDrawable == null) {
            throw new RuntimeException("pointFocusedImg不能为空");
        } else if (mPointUnfocusedDrawable == null) {
            throw new RuntimeException("pointUnfocusedImg不能为空");
        }

        RelativeLayout pointContainerRl = new RelativeLayout(context);
        if (Build.VERSION.SDK_INT >= 16) {
            pointContainerRl.setBackground(mPointContainerBackgroundDrawable);
        } else {
            pointContainerRl.setBackgroundDrawable(mPointContainerBackgroundDrawable);
        }
        pointContainerRl.setPadding(mPointContainerLeftRightPadding, 0, mPointContainerLeftRightPadding, 0);
        RelativeLayout.LayoutParams pointContainerLp = new RelativeLayout.LayoutParams(RMP, RWC);
        // 处理圆点在顶部还是底部
        if ((mPointGravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP) {
            pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else {
            pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        addView(pointContainerRl, pointContainerLp);

        mPointRealContainerLl = new LinearLayout(context);
        mPointRealContainerLl.setId(R.id.banner_pointContainerId);
        mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams pointRealContainerLp = new RelativeLayout.LayoutParams(RWC, RWC);
        pointContainerRl.addView(mPointRealContainerLl, pointRealContainerLp);

        RelativeLayout.LayoutParams tipLp = new RelativeLayout.LayoutParams(RMP, mPointFocusedDrawable.getIntrinsicHeight() + 2 * mPointTopBottomMargin);
        mTipTv = new TextView(context);
        mTipTv.setGravity(Gravity.CENTER_VERTICAL);
        mTipTv.setSingleLine(true);
        mTipTv.setEllipsize(TextUtils.TruncateAt.END);
        mTipTv.setTextColor(mTipTextColor);
        mTipTv.setTextSize(mTipTextSize);
        pointContainerRl.addView(mTipTv, tipLp);

        int horizontalGravity = mPointGravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        // 处理圆点在左边、右边还是水平居中
        if (horizontalGravity == Gravity.LEFT) {
            pointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tipLp.addRule(RelativeLayout.RIGHT_OF, R.id.banner_pointContainerId);
            mTipTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else if (horizontalGravity == Gravity.RIGHT) {
            pointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tipLp.addRule(RelativeLayout.LEFT_OF, R.id.banner_pointContainerId);
        } else {
            pointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tipLp.addRule(RelativeLayout.LEFT_OF, R.id.banner_pointContainerId);
        }
    }

    /**
     * 设置页码切换过程的时间长度
     *
     * @param duration 页码切换过程的时间长度
     */
    public void setPageChangeDuration(int duration) {
        if (duration > 0 && duration < 5000) {
            mViewPager.setPageChangeDuration(duration);
        }
    }

    /**
     * 设置每一页的控件和文案
     *
     * @param views 每一页的控件集合
     * @param tips  每一页的提示文案集合
     */
    public void setViewsAndTips(List<View> views, List<String> tips) {
        if (mAutoPlayAble && views.size() < 3) {
            throw new RuntimeException("开启指定轮播时至少有三个页面");
        }
        if (tips != null && tips.size() < views.size()) {
            throw new RuntimeException("提示文案数必须等于页面数量");
        }
        mViews = views;
        mTips = tips;
        mViewPager.setAdapter(new PageAdapter());
        mViewPager.setOnPageChangeListener(new ChangePointListener());

        initPoints();
        processAutoPlay();
    }

    /**
     * 设置每一页的控件
     *
     * @param views 每一页的控件集合
     */
    public void setViews(List<View> views) {
        setViewsAndTips(views, null);
    }

    /**
     * 设置每一页的提示文案
     *
     * @param tips 提示文案集合
     */
    public void setTips(List<String> tips) {
        if (tips != null && mViews != null && tips.size() < mViews.size()) {
            throw new RuntimeException("提示文案数必须等于页面数量");
        }
        mTips = tips;
    }

    private void initPoints() {
        mPointRealContainerLl.removeAllViews();
        mViewPager.removeAllViews();
        if (mPoints != null) {
            mPoints.clear();
        } else {
            mPoints = new ArrayList<ImageView>();
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
        lp.setMargins(mPointLeftRightMargin, mPointTopBottomMargin, mPointLeftRightMargin, mPointTopBottomMargin);
        ImageView imageView;
        for (int i = 0; i < mViews.size(); i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(lp);
            imageView.setImageDrawable(mPointUnfocusedDrawable);
            mPoints.add(imageView);
            mPointRealContainerLl.addView(imageView);
        }
    }

    private void processAutoPlay() {
        if (mAutoPlayAble) {
            mViewPager.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            stopAutoPlay();
                            break;
                        case MotionEvent.ACTION_UP:
                            startAutoPlay();
                            break;
                    }
                    return false;
                }
            });
            int zeroItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mViews.size();
            mViewPager.setCurrentItem(zeroItem);
        } else {
            switchToPoint(mCurrentPoint);
        }
    }

    /**
     * Set the currently selected page.
     *
     * @param item Item index to select
     */
    public void setCurrentItem(int item) {
        if (mAutoPlayAble) {
            int realCurrentItem = mViewPager.getCurrentItem();
            int currentItem = realCurrentItem % mViews.size();
            int offset = item - currentItem;

            // 这里要使用循环递增或递减设置，否则会ANR
            if (offset < 0) {
                for (int i = -1; i >= offset; i--) {
                    mViewPager.setCurrentItem(realCurrentItem + i, false);
                }
            } else if (offset > 0) {
                for (int i = 1; i <= offset; i++) {
                    mViewPager.setCurrentItem(realCurrentItem + i, false);
                }
            }
            stopAutoPlay();
            startAutoPlay();
        } else {
            mViewPager.setCurrentItem(item, false);
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startAutoPlay();
        } else if (visibility == INVISIBLE) {
            stopAutoPlay();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPagerHandler != null) {
            mPagerHandler.removeCallbacks(mAutoPlayTask);
        }
    }

    private void startAutoPlay() {
        if (mAutoPlayAble && !mIsAutoPlaying) {
            mIsAutoPlaying = true;
            mPagerHandler.postDelayed(mAutoPlayTask, mAutoPlayInterval);
        }
    }

    private void stopAutoPlay() {
        if (mAutoPlayAble && mIsAutoPlaying) {
            mIsAutoPlaying = false;
            mPagerHandler.removeCallbacks(mAutoPlayTask);
        }
    }

    private void switchToPoint(int newCurrentPoint) {
        mPoints.get(mCurrentPoint).setImageDrawable(mPointUnfocusedDrawable);
        mPoints.get(newCurrentPoint).setImageDrawable(mPointFocusedDrawable);
        mCurrentPoint = newCurrentPoint;

        if (mTipTv != null && mTips != null) {
            mTipTv.setText(mTips.get(newCurrentPoint % mTips.size()));
        }
    }

    /**
     * 设置页面也换动画
     *
     * @param effect
     */
    public void setTransitionEffect(TransitionEffect effect) {
        switch (effect) {
            case Default:
                mViewPager.setPageTransformer(true, new DefaultPageTransformer());
                break;
            case Alpha:
                mViewPager.setPageTransformer(true, new AlphaPageTransformer());
                break;
            case Rotate:
                mViewPager.setPageTransformer(true, new RotatePageTransformer());
                break;
            case Cube:
                mViewPager.setPageTransformer(true, new CubePageTransformer());
                break;
            case Flip:
                mViewPager.setPageTransformer(true, new FlipPageTransformer());
                break;
            case Accordion:
                mViewPager.setPageTransformer(true, new AccordionPageTransformer());
                break;
            case ZoomFade:
                mViewPager.setPageTransformer(true, new ZoomFadePageTransformer());
                break;
            case Fade:
                mViewPager.setPageTransformer(true, new FadePageTransformer());
                break;
            case ZoomCenter:
                mViewPager.setPageTransformer(true, new ZoomCenterPageTransformer());
                break;
            case ZoomStack:
                mViewPager.setPageTransformer(true, new ZoomStackPageTransformer());
                break;
            case Stack:
                mViewPager.setPageTransformer(true, new StackPageTransformer());
                break;
            case Depth:
                mViewPager.setPageTransformer(true, new DepthPageTransformer());
                break;
            case Zoom:
                mViewPager.setPageTransformer(true, new ZoomPageTransformer());
                break;
            default:
                break;
        }
    }

    /**
     * 设置自定义页面切换动画
     *
     * @param transformer
     */
    public void setPageTransformer(ViewPager.PageTransformer transformer) {
        if (transformer != null) {
            mViewPager.setPageTransformer(true, transformer);
        }
    }

    private final class PageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mAutoPlayAble ? Integer.MAX_VALUE : mViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViews.get(position % mViews.size());

            // 在destroyItem方法中销毁的话，当只有3页时会有问题
            if (container.equals(view.getParent())) {
                container.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private final class ChangePointListener extends BGAViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            switchToPoint(position % mViews.size());
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mTipTv != null && mTips != null) {
                if (positionOffset > 0.5) {
                    mTipTv.setText(mTips.get((position + 1) % mTips.size()));
                    ViewHelper.setAlpha(mTipTv, positionOffset);
                } else {
                    ViewHelper.setAlpha(mTipTv, 1 - positionOffset);
                    mTipTv.setText(mTips.get(position % mTips.size()));
                }
            }
        }
    }

    public enum TransitionEffect {
        Default,
        Alpha,
        Rotate,
        Cube,
        Flip,
        Accordion,
        ZoomFade,
        Fade,
        ZoomCenter,
        ZoomStack,
        Stack,
        Depth,
        Zoom
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

}