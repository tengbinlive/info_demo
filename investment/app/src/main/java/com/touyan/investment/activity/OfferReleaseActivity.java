package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.InvOfferBean;
import com.touyan.investment.bean.main.InvReleaseOfferParam;
import com.touyan.investment.bean.main.InvReleaseOfferResult;
import com.touyan.investment.bean.user.AccountResult;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.EditTextWithDelete;

public class OfferReleaseActivity extends AbsActivity {

    private InvestmentManager manager = new InvestmentManager();

    private boolean isPublic;

    private EditTextWithDelete value_ly;

    private EditText offer_money_et;

    private TextView valid_offer_money;

    private static final int LOAD_RELEASE = 0x01;//发布

    private static final int LOAD_BALANCE = 0x02;//余额

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_RELEASE:
                    loadRelease((CommonResponse) msg.obj);
                    break;
                case LOAD_BALANCE:
                    loadBalance((CommonResponse) msg.obj);
                default:
                    break;
            }
        }
    };

    private void loadBalance(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            AccountResult result = (AccountResult) resposne.getData();
            valid_offer_money.setText("可用悬赏金币数：" + result.getAccount().getUavail());
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadRelease(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            InvReleaseOfferResult result = (InvReleaseOfferResult) resposne.getData();
            InvOfferBean bean = result.getReward();
            bean.setUser(App.getInstance().getgUserInfo());
            Intent intent = new Intent();
            intent.putExtra(KEY, bean);
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
        dialogShow();
        getBalance();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_offer_release;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft(0, R.string.cancel);
        setToolbarIntermediateStrID(R.string.offer_release);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRightStrID(R.string.release);
        setToolbarRightOnClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseInfo();
            }
        });
    }

    private void findView() {
        SwitchButton sw_bt = (SwitchButton) findViewById(R.id.sw_bt);
        sw_bt.setChecked(true);
        sw_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isPublic = b;
            }
        });

        //赏金
        offer_money_et = (EditText) findViewById(R.id.offer_money_et);

        valid_offer_money = (TextView) findViewById(R.id.valid_offer_money);

        //详情
        value_ly = (EditTextWithDelete) findViewById(R.id.value_ly);
        value_ly.setHint(R.string.offer_release_value_hint);

    }

    private void getBalance() {
        dialogShow();
        manager.queryAccount(this, activityHandler, LOAD_BALANCE);
    }

    private void releaseInfo() {

        String contentStr = value_ly.getText().toString();
        if (StringUtil.isBlank(contentStr)) {
            value_ly.requestFocus();
            CommonUtil.showToast("说点什么吧~");
            return;
        }
        dialogShow(R.string.carrying);
        InvReleaseOfferParam param = new InvReleaseOfferParam();
        param.setRtitle("");
        param.setContnt(contentStr);
        String money = offer_money_et.getText().toString();
        if (StringUtil.isBlank(money)) {
            money = "0";
        }
        param.setAmount(Integer.parseInt(money));
        param.setIsshow(isPublic ? InvReleaseOfferParam.PUBLIC_YES : InvReleaseOfferParam.PUBLIC_NO);
        dialogShow(R.string.carrying);
        manager.releaseReward(this, param, activityHandler, LOAD_RELEASE);
    }


}
