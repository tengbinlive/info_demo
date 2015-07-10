package cn.bingoogolapple.bgabanner.transformer;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * 创建时间:15/6/19 上午8:41
 * 描述:
 */
public class AccordionPageTransformer extends BGAPageTransformer {

    @Override
    public void handleInvisiblePage(View view, float position) {
    }

    @Override
    public void handleLeftPage(View view, float position) {
        ViewHelper.setPivotX(view, view.getWidth());
        ViewHelper.setScaleX(view, 1.0f + position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewHelper.setPivotX(view, 0);
        ViewHelper.setScaleX(view, 1.0f - position);
    }

}