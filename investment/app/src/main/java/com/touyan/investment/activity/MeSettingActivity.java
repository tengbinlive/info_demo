package com.touyan.investment.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.mview.BottomView;

public class MeSettingActivity extends AbsActivity implements OnClickListener {
    private Context context;
    private Button loginout_btn;
    private RelativeLayout password_rl, about_rl;
    private BottomView mBottomView;

    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_meset;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.setting_str);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginout_btn) {
            selectPict();
        } else if (v.getId() == R.id.password_rl) {
            Intent intent = new Intent(context, ResetPasswordActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.about_rl) {
            Intent intent = new Intent(context, AboutInvActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        context = MeSettingActivity.this;
        loginout_btn = (Button) findViewById(R.id.loginout_btn);
        password_rl = (RelativeLayout) findViewById(R.id.password_rl);
        about_rl = (RelativeLayout) findViewById(R.id.about_rl);

        loginout_btn.setOnClickListener(this);
        password_rl.setOnClickListener(this);
        about_rl.setOnClickListener(this);
    }

    private void selectPict() {
        if (mBottomView != null) {
            mBottomView.showBottomView(true);
            return;
        }
        mBottomView = new BottomView(this, R.style.BottomViewTheme_Defalut, R.layout.bottom_view);
        mBottomView.setAnimation(R.style.BottomToTopAnim);

        TextView top = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_1);
        TextView shareFriend = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_2);
        TextView shareGroup = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_3);
        TextView cancel = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_cancel);
        View divider1 = (View) mBottomView.getView().findViewById(R.id.divider1);
        divider1.setVisibility(View.GONE);
        shareGroup.setVisibility(View.GONE);
        top.setText("退出当前账号");
        shareFriend.setText("退出");
        shareFriend.setTextColor(getResources().getColor(R.color.dialog_text_blue));
        cancel.setText("取消");
        cancel.setTextColor(getResources().getColor(R.color.dialog_text_blue));
        shareFriend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomView.dismissBottomView();
            }
        });
        mBottomView.showBottomView(true);
    }
}
