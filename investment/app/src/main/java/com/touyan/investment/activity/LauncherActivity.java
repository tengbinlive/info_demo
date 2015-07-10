package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.core.util.Log;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;

import java.util.ArrayList;

public class LauncherActivity extends AbsActivity implements OnClickListener {

    private final static int TO_LOGIN = 0;
    private final static int TO_GUIDE = 1;
    private boolean isTo = false;
    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case TO_LOGIN:
                    toLogining();
                    break;
                case TO_GUIDE:
                    toGuide();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(false);
        findView();
        if (!isTo) {
            activityHandler.sendEmptyMessageDelayed(App.isFirstLunch() ? TO_GUIDE : TO_LOGIN, 3500);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_launcher;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.launcher_ly) {
            if (!isTo) {
                if (App.isFirstLunch()) {
                    toGuide();
                } else {
                    toLogining();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isTo = false;
    }

    private void findView() {
        RelativeLayout launcher_ly = (RelativeLayout) findViewById(R.id.launcher_ly);
        launcher_ly.setOnClickListener(this);
    }


    private void toLogining() {
        isTo = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade, R.anim.launcher_out);
    }

    private void toGuide() {
        isTo = true;
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade, R.anim.launcher_out);
    }

}
