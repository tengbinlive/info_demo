package com.touyan.investment.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.kyleduo.switchbutton.SwitchButton;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.mview.EditTextWithDelete;

public class ActReleaseRoadshowActivity extends AbsActivity implements OnClickListener {

    private final static int[][] titleLayout = new int[][]{{R.string.act_release_roadshow_title, R.string.act_release_roadshow_title_hint}, {R.string.act_release_roadshow_date, R.string.act_release_roadshow_date_hint}, {R.string.act_release_roadshow_city, R.string.act_release_roadshow_city_hint}};

    private LinearLayout release_act_ly;

    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_act_release_roadshow;
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
        SwitchButton people_sw_bt = (SwitchButton) findViewById(R.id.people_sw_bt);
        final RelativeLayout people_num_ly = (RelativeLayout) findViewById(R.id.people_num_ly);
        people_sw_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    people_num_ly.setVisibility(View.VISIBLE);
                } else {
                    people_num_ly.setVisibility(View.GONE);
                }
            }
        });

        addReleaseInfo();
    }

    private void addReleaseInfo() {
        release_act_ly.removeAllViews();
        for (int i = 0; i < 3;i++ ) {
            LinearLayout item_release_title = (LinearLayout) mInflater.inflate(R.layout.item_release_title, release_act_ly, false);
            TextView release_title_tv = (TextView) item_release_title.findViewById(R.id.release_title_tv);
            EditText release_value_et = (EditText) item_release_title.findViewById(R.id.release_value_et);
            release_title_tv.setText(titleLayout[i][0]);
            release_value_et.setHint(titleLayout[i][1]);
            release_act_ly.addView(item_release_title);
        }

        EditTextWithDelete item_release_value = (EditTextWithDelete) mInflater.inflate(R.layout.item_release_value, release_act_ly, false);
        item_release_value.setHint(R.string.act_release_roadshow_value_hint);
        release_act_ly.addView(item_release_value);
    }

}
