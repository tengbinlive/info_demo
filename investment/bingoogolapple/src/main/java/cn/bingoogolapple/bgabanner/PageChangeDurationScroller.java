package cn.bingoogolapple.bgabanner;

import android.content.Context;
import android.widget.Scroller;

/**
 * 创建时间:15/6/19 下午11:59
 * 描述:
 */
public class PageChangeDurationScroller extends Scroller {
    private int mDuration = 1000;

    public PageChangeDurationScroller(Context context) {
        super(context);
    }

    public PageChangeDurationScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}