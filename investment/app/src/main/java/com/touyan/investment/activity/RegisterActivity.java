package com.touyan.investment.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.rey.material.widget.CheckBox;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.mview.EditTextWithDelete;

public class RegisterActivity extends AbsActivity implements OnClickListener {
    private EditTextWithDelete register_et;
    private Button next_btn;
    private CheckBox agree_cb;
    private boolean isAgree = false;

    @Override
    public void EInit() {
        super.EInit();
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
            isAgree = agree_cb.isChecked();
            if (isAgree) {
                String phoneNum = register_et.getText().toString();
                if(StringUtil.isBlank(phoneNum)){
                    CommonUtil.showToast("请输入您的手机号码");
                    return;
                }
                if(StringUtil.checkMobile(phoneNum)){
                    Intent intent = new Intent(this, RegisterNextActivity.class);
                    intent.putExtra(KEY,phoneNum);
                    startActivity(intent);
                }else{
                    CommonUtil.showToast("暂不支持您输入的号码");
                }
            } else {
                showAgreementAlert();
                CommonUtil.showToast("请阅读同意协议");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        register_et = (EditTextWithDelete) findViewById(R.id.register_et);
        next_btn = (Button) findViewById(R.id.next_btn);
        agree_cb = (CheckBox) findViewById(R.id.agree_cb);
        agree_cb.setChecked(true);
        next_btn.setOnClickListener(this);
    }

    private void showAgreementAlert() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();//带有编辑框的要用Dialog，否则弹不出键盘
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
        Window window = dlg.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(0));
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,dialog_fb_instruction.xml文件中定义view内容
        window.setContentView(R.layout.dialog_agreement);
        WebView webview = (WebView) window.findViewById(R.id.webview);
        WebSettings setting = webview.getSettings();

        setting.setDefaultTextEncodingName("utf-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

        setting.setAppCacheEnabled(true);

        //打开本包内asset目录下的index.html文件
        webview.loadUrl("file:///android_asset/protocol.html");
        ImageButton okBt = (ImageButton) window.findViewById(R.id.dialog_btn_cancel);
        okBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }

}
