package com.touyan.investment.mview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @Type: MyGridView
 * 让GridView可以做ScrollView的子控件，但尺寸不会减小
 */
public class MGridView extends GridView {
	
    public MGridView(Context context) {
        super(context);  
    }  
    
    public MGridView(Context context, AttributeSet attrs) {
        super(context, attrs);  
    }  
    
    public MGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);  
    }  
  
    @Override  
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }    
}  
