package com.touyan.investment.activity;

import android.content.Intent;
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
public class AddFriendActivity extends AbsActivity implements View.OnClickListener {
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
        findView();
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

    private void findView() {
        searchEt.setOnClickListener(this);
        inviteBtn.setOnClickListener(this);
        knowBtn.setOnClickListener(this);
        createGroupBtn.setOnClickListener(this);
        recommendGroupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.search_et) {

        } else if (id == R.id.inviteBtn) {

        } else if (id == R.id.knowBtn) {
            Intent intent = new Intent(this, AddfriendMayKnowActivity.class);
            startActivity(intent);
        }else if (id == R.id.createGroupBtn) {
            Intent intent = new Intent(this, CreateGroupActivity.class);
            startActivity(intent);

        }else if (id == R.id.recommendGroupBtn) {
            Intent intent = new Intent(this, HotGroupRecoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
