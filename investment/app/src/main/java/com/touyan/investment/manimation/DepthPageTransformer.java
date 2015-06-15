package com.touyan.investment.manimation;


import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 */
public class DepthPageTransformer implements ViewPager.PageTransformer {
	private static final float MIN_SCALE = 0.45f;
	private static final float MAX_SCALE = 1.55f;
	private static final float MIN_ALPHA = 0.0f;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void transformPage(View view, float position) {
		int pageWidth = view.getWidth();

		if (position < -1) { // [-Infinity,-1)
			// This page is way off-screen to the left.
			view.setAlpha(0);
		} else if (position <= 0) { // [-1,0]
			// Use the default slide transition when moving to the left page

			// Counteract the default slide transition
			view.setTranslationX(pageWidth * -position);

			// Scale the page down (between MIN_SCALE and 1)
			float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);
			view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

		} else if (position <= 1) { // (0,1]
			// Fade the page out.

			view.setTranslationX(0);
			view.setAlpha(1 - position);
			float scaleFactor = MAX_SCALE + (1 - MAX_SCALE) * (1 - Math.abs(position));
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);

		} else { // (1,+Infinity]
			// This page is way off-screen to the right.

			view.setAlpha(0);
		}
	}
}