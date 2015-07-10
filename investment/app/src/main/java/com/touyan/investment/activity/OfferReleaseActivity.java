package com.touyan.investment.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.kyleduo.switchbutton.SwitchButton;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.mview.EditTextWithDelete;

public class OfferReleaseActivity extends AbsActivity implements OnClickListener {

    private LinearLayout release_ly;

    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_offer_release;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft(0, R.string.cancel);
        setToolbarIntermediateStrID(R.string.offer_release);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRightStrID(R.string.release);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        release_ly = (LinearLayout) findViewById(R.id.release_ly);
        SwitchButton sw_bt = (SwitchButton) findViewById(R.id.sw_bt);
        sw_bt.setChecked(true);
        sw_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        addReleaseInfo();
    }

    private void addReleaseInfo() {

        EditTextWithDelete item_release_value = (EditTextWithDelete) mInflater.inflate(R.layout.item_release_value, release_ly, false);
        item_release_value.setHint(R.string.offer_release_value_hint);
        release_ly.addView(item_release_value);
    }

}
