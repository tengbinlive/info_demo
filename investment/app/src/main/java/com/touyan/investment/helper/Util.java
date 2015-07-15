package com.touyan.investment.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.view.ViewHelper;

public class Util {

	private final static Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

	public final static int TOP = 0;
	public final static int BOTTOM = TOP + 1;
	public final static int LEFT = BOTTOM + 1;
	public final static int RIGHT = LEFT + 1;

	private static Util instance;

	public static Util getInstance() {
		if (instance == null) instance = new Util();
		return instance;
	}

	public static void setTextViewDrawaleAnchor(Context context,TextView textView,int iconid, int anchor) {
		Drawable drawable;
		if(iconid<=0){
			textView.setCompoundDrawables(null, null, null, null);
			return ;
		}
		drawable = context.getResources().getDrawable(iconid);
		// 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		if (anchor == TOP) {
			textView.setCompoundDrawables(null, drawable, null, null);
		} else if (anchor == BOTTOM) {
			textView.setCompoundDrawables(null, null, null, drawable);
		} else if (anchor == LEFT) {
			textView.setCompoundDrawables(drawable, null, null, null);
		} else if (anchor == RIGHT) {
			textView.setCompoundDrawables(null, null, drawable, null);
		}else{
			textView.setCompoundDrawables(null, null, null, null);
		}
	}

	public static void viewScaleAnimation(View view) {
		PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1, 1.3f, 1);
		PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1, 1.3f, 1);
		ViewHelper.setPivotX(view, view.getWidth() >> 1);
		ViewHelper.setPivotY(view, view.getHeight() >> 1);
		ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY).setDuration(150);
		animator2.setInterpolator(mInterpolator);
		animator2.start();
	}

}
