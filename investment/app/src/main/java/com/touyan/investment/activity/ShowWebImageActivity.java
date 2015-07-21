package com.touyan.investment.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.core.util.CommonUtil;
import com.touyan.investment.R;
import ru.truba.touchgallery.GalleryWidget.GalleryViewPager;
import ru.truba.touchgallery.GalleryWidget.UrlPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowWebImageActivity extends Activity {

    private String[] imagePaths = null;
    private GalleryViewPager viewpager = null;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_show_webimage);

        imagePaths = getIntent().getStringArrayExtra("image");
        position = getIntent().getIntExtra("position", 0);
        viewpager = (GalleryViewPager) findViewById(R.id.gallery_viewpager);
        List<String> items = new ArrayList<String>();
        Collections.addAll(items, imagePaths);
        UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(this, items);

        viewpager.setOffscreenPageLimit(3);

        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(position);
    }


}
