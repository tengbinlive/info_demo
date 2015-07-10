package com.touyan.investment.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import com.kyleduo.switchbutton.SwitchButton;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class InfoReleaseActivity extends AbsActivity implements OnClickListener {
    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_info_release;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft(0, R.string.cancel);
        setToolbarIntermediateStrID(R.string.release_info);
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
        SwitchButton toll_sw_bt = (SwitchButton) findViewById(R.id.toll_sw_bt);
        final RelativeLayout toll_ly = (RelativeLayout) findViewById(R.id.toll_ly);
        toll_sw_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    toll_ly.setVisibility(View.VISIBLE);
                } else {
                    toll_ly.setVisibility(View.GONE);
                }
            }
        });

    }

}
