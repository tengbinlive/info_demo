package com.touyan.investment.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

/**
 * Created by Administrator on 2015/7/23.
 */
public class AddFriendActivity extends AbsActivity {


    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.inviteBtn)
    LinearLayout inviteBtn;
    @Bind(R.id.knowBtn)
    LinearLayout knowBtn;
    @Bind(R.id.createGroupBtn)
    LinearLayout createGroupBtn;
    @Bind(R.id.recommendGroupBtn)
    LinearLayout recommendGroupBtn;

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_add_friend;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.add);
        setToolbarRightVisbility(View.INVISIBLE, View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
