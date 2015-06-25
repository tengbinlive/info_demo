package com.touyan.investment.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class RegisterActivity extends AbsActivity implements OnClickListener {
    private Button next_btn ;
    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(false);
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.register);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_btn) {
            Intent intent = new Intent(this, RegisterNextActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {
        next_btn = (Button) findViewById(R.id.next_btn);
        next_btn.setOnClickListener(this);
    }

}
