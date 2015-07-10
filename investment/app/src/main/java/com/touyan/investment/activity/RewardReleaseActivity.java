package com.touyan.investment.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kyleduo.switchbutton.SwitchButton;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.mview.EditTextWithDelete;

public class RewardReleaseActivity extends AbsActivity implements OnClickListener {

    private LinearLayout release_act_ly;

    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_act_release_product;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft(0, R.string.cancel);
        setToolbarIntermediateStrID(R.string.release_act);
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
        release_act_ly = (LinearLayout) findViewById(R.id.release_act_ly);
        SwitchButton sw_bt = (SwitchButton) findViewById(R.id.public_sw_bt);
        sw_bt.setChecked(true);
        sw_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                } else {
                }
            }
        });

        addReleaseInfo();
    }

    private void addReleaseInfo() {
        EditTextWithDelete item_release_value = (EditTextWithDelete) mInflater.inflate(R.layout.item_release_value, release_act_ly, false);
        item_release_value.setHint(R.string.act_release_product_value_hint);
        release_act_ly.addView(item_release_value);
    }

}
