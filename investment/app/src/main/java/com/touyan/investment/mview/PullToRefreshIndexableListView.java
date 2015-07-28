package com.touyan.investment.mview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import com.handmark.pulltorefresh.OverscrollHelper;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.PullToRefreshListView;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            indexableListView = new InternalListViewSDK9(context, attrs, R.style.IndexableListView);
        } else {
            indexableListView = new IndexableListView(context, attrs, R.style.IndexableListView);
        }

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

    @TargetApi(9)
    class InternalListViewSDK9 extends IndexableListView {

        public InternalListViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public InternalListViewSDK9(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(PullToRefreshIndexableListView.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);

            return returnValue;
        }
    }

}
