package com.touyan.investment.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.rey.material.widget.CheckBox;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;

public class RegisterActivity extends AbsActivity implements OnClickListener {
    private Button next_btn ;
    private CheckBox agree_cb ;
    private boolean isAgree = false;
    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(false);
        findView();

        showAgreementAlert(null ,null);
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
            isAgree = agree_cb.isChecked();
            if (isAgree){
                Intent intent = new Intent(this, RegisterNextActivity.class);
                startActivity(intent);
            }else {
                showAgreementAlert(null ,null);
                Toast.makeText(this,"请阅读同意协议",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void findView() {
        next_btn = (Button) findViewById(R.id.next_btn);
        agree_cb = (CheckBox) findViewById(R.id.agree_cb);
        next_btn.setOnClickListener(this);
    }

    private void showAgreementAlert(String titleStr ,String contentStr) {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();//带有编辑框的要用Dialog，否则弹不出键盘
        dlg.setCanceledOnTouchOutside(false);

        dlg.show();
        Window window = dlg.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(0));
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,dialog_fb_instruction.xml文件中定义view内容
        window.setContentView(R.layout.dialog_agreement);
        TextView titleTv = (TextView) window.findViewById(R.id.agreement_title);
        TextView contentTv = (TextView) window.findViewById(R.id.agreement_content);
        //titleTv.setText(titleStr);
        //contentTv.setText(contentStr);
        ImageButton okBt = (ImageButton) window.findViewById(R.id.dialog_btn_cancel);
        okBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }
}
