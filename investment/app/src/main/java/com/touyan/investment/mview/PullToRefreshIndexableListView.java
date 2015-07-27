package com.touyan.investment.mview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.touyan.investment.R;

/**
 * Created by Administrator on 2015/7/27.
 */
public class PullToRefreshIndexableListView extends PullToRefreshBase<IndexableListView> {
    public PullToRefreshIndexableListView(Context context) {
        super(context);
    }

    public PullToRefreshIndexableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshIndexableListView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshIndexableListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }


    @Override
    protected IndexableListView createRefreshableView(Context context, AttributeSet attrs) {

        IndexableListView indexableListView;
        indexableListView = new IndexableListView(context, attrs, R.style.IndexableListView);
        indexableListView.setId(com.handmark.pulltorefresh.R.id.scrollview);
        return indexableListView;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return mRefreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        View scrollViewChild = mRefreshableView.getChildAt(0);
        if (null != scrollViewChild) {
            return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;
    }


    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;   //表示向下传递事件
                }
        }

        return super.onInterceptTouchEvent(ev);
    }

}
