package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.InvActDetailResult;
import com.touyan.investment.bean.main.InvReleaseActParam;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.EditTextWithDelete;

public class ActReleaseProductActivity extends AbsActivity implements OnClickListener {

    private InvestmentManager investmentManager = new InvestmentManager();

    private boolean isPublic;

    private EditText release_value_et;

    private TextView date_value_tv;

    private EditTextWithDelete value_ly;

    private static final int LOAD_RELEASE = 0x01;//发布

    private String titleStr;

    private String dateStr;

    private String contentStr;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_RELEASE:
                    loadRelease((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadRelease(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            InvActDetailResult releaseResult = (InvActDetailResult) resposne.getData();
            Intent intent = new Intent();
            intent.putExtra(KEY, releaseResult.getDetail());
            setResult(AbsFragment.RECODE_RELEASE, intent);
            CommonUtil.showToast("发布成功");
            finish();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }


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
        setToolbarIntermediateStrID(R.string.release_act_product);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRightStrID(R.string.release);
        setToolbarRightOnClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseInfo();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        SwitchButton sw_bt = (SwitchButton) findViewById(R.id.public_sw_bt);
        sw_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isPublic = b;
            }
        });
        sw_bt.setChecked(true);

        //产品名称
        LinearLayout titleLy = (LinearLayout) findViewById(R.id.title_ly);
        TextView release_title_tv = (TextView) titleLy.findViewById(R.id.release_title_tv);
        release_value_et = (EditText) titleLy.findViewById(R.id.release_value_et);
        release_title_tv.setText(R.string.act_release_product_title);
        release_value_et.setHint(R.string.act_release_product_title_hint);

        //时间
        date_value_tv = (TextView) findViewById(R.id.date_value_tv);

        //详情
        value_ly = (EditTextWithDelete) findViewById(R.id.value_ly);
        value_ly.setHint(R.string.act_release_product_value_hint);

    }

    private void releaseInfo() {
        titleStr = release_value_et.getText().toString();
        if (StringUtil.isBlank(titleStr)) {
            release_value_et.requestFocus();
            CommonUtil.showToast("标题没有哦~");
            return;
        }

//        dateStr = date_value_tv.getText().toString();
//        if (StringUtil.isBlank(dateStr)) {
//
//            CommonUtil.showToast("选个时间吧~");
//            return;
//        }

        contentStr = value_ly.getText().toString();
        if (StringUtil.isBlank(contentStr)) {
            value_ly.requestFocus();
            CommonUtil.showToast("说点什么吧~");
            return;
        }
        dialogShow(R.string.reviewing);
        InvReleaseActParam param = new InvReleaseActParam();
        param.setActvtp(InvReleaseActParam.TYPE_ACT);
        param.setIspubl(isPublic ? InvReleaseActParam.PUBLIC_YES : InvReleaseActParam.PUBLIC_NO);
        param.setByloct("上海");
        param.setAdress("上海");
        param.setAdress("上海");
        param.setStartm("2015-01-09 20:12:20");
        param.setEndtim("2015-01-09 20:12:20");
        param.setContnt(contentStr);
        param.setAtitle(titleStr);
        investmentManager.releaseAct(this, param, activityHandler, LOAD_RELEASE);

    }


}
