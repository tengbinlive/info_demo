package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.InvRecordRewardsResult;
import com.touyan.investment.bean.user.Account;
import com.touyan.investment.bean.user.AccountResult;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.GridViewWithHeaderAndFooter;

/**
 * Created by Administrator on 2015/7/20.
 */
public class UserWalletActivity extends AbsActivity {

    private InvestmentManager manager = new InvestmentManager();

    private static final int LOAD_BALANCE = 0x01;//余额

    private TextView uavail;
    private TextView losted;
    private TextView frozen;
    private TextView income;


    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_BALANCE:
                    loadBalance((CommonResponse) msg.obj);
                    break;

                default:
                    break;
            }
        }
    };

    private void loadBalance(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            AccountResult result = (AccountResult) resposne.getData();
            initView(result.getAccount());
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
        findView();
        getBalance();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_user_wallet;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediate("我的钱包");
        setToolbarRightVisbility(View.INVISIBLE, View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        uavail = (TextView) findViewById(R.id.uavail);
        losted = (TextView) findViewById(R.id.losted);
        frozen = (TextView) findViewById(R.id.frozen);
        income = (TextView) findViewById(R.id.income);
    }

    private void getBalance() {
        dialogShow();
        manager.queryAccount(this, activityHandler, LOAD_BALANCE);
    }

    private void initView(Account account) {
        SpannableStringBuilder builder;
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.textcolor_666));

        uavail.setText("" + account.getUavail());

        losted.setText("" + account.getLosted() + "币");
        builder = new SpannableStringBuilder(losted.getText().toString());
        builder.setSpan(colorSpan, builder.length() - 1, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        losted.setText(builder);

        frozen.setText("" + account.getFrozen() + "币");
        builder = new SpannableStringBuilder(frozen.getText().toString());
        builder.setSpan(colorSpan, builder.length() - 1, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        frozen.setText(builder);

        income.setText("" + account.getIncome() + "币");
        builder = new SpannableStringBuilder(income.getText().toString());
        builder.setSpan(colorSpan, builder.length() - 1, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        income.setText(builder);
    }
}
