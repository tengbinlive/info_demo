package com.touyan.investment.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class RegisterNextActivity extends AbsActivity implements OnClickListener {
    private Button register_btn ;
    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(false);
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_registernext;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.register);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_btn) {
            Intent intent = new Intent(this, RegisterNextActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {
        register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);
    }

}
