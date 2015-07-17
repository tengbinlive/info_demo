package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.joooonho.SelectableRoundedImageView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.MoneyAdapter;
import com.touyan.investment.bean.main.InvInfoBean;
import com.touyan.investment.bean.main.InvRecordRewardsResult;
import com.touyan.investment.bean.user.AccountResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.MGridView;

public class InfoRewardActivity extends AbsActivity {

    private static final int LOAD_BALANCE = 0x01;//余额

    private static final int LOAD_REWARD = 0x02;//打赏

    private InvestmentManager manager = new InvestmentManager();

    private MGridView money_ly;

    private MoneyAdapter mAdapter;

    private InvInfoBean invInfoBean;

    private EditText balance_et;

    private EditText need_et;

    private Button reward_bt;

    private double currentMoney;

    private double need;

    private final static String[] moneys = new String[]{"18金币", "38金币", "58金币", "88金币", "188金币", "其他金额"};

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_BALANCE:
                    loadBalance((CommonResponse) msg.obj);
                    break;
                case LOAD_REWARD:
                    loadReward((CommonResponse) msg.obj);
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
            balance_et.setText(result.getAccount().getUavail() + "金币");
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadReward(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            InvRecordRewardsResult recordRewardsResult = (InvRecordRewardsResult) resposne.getData();
            double rewardsAmount = recordRewardsResult.getRewardsAmount();
            if (rewardsAmount <= 0) {
                rewardsAmount = invInfoBean.getRewardsAmount() + need;
            }
            invInfoBean.setRewardsAmount(rewardsAmount);
            CommonUtil.showToast("打赏成功拉");
            balance_et.setText(currentMoney + "金币");
            Intent intent = new Intent();
            intent.putExtra(KEY, invInfoBean);
            setResult(InfoDetailActivity.REQUSETCODE, intent);
            finish();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }


    @Override
    public void EInit() {
        invInfoBean = (InvInfoBean) getIntent().getSerializableExtra(KEY);
        super.EInit();
        findView();
        initGridView();
        setGridViewSelect(0);
        getBalance();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_info_reward;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft(0, R.string.cancel);
        setToolbarIntermediateStrID(R.string.bottom_menu_reward);
    }

    private void getBalance() {
        dialogShow();
        manager.queryAccount(this, activityHandler, LOAD_BALANCE);
    }

    private void getReward() {
        String balanceStr = balance_et.getText().toString();
        double balance = Double.valueOf(balanceStr.replace("金币", ""));
        String needStr = need_et.getText().toString();
        need = Double.valueOf(needStr.replace("金币", ""));
        if (balance < need) {
            CommonUtil.showToast("您的余额不足拉");
            return;
        }
        dialogShow("正在打赏");
        currentMoney = balance - need;
        manager.recordRewards(this, invInfoBean.getInfoid(), invInfoBean.getPubsid(), need, activityHandler, LOAD_REWARD);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        reward_bt = (Button) findViewById(R.id.reward_bt);
        money_ly = (MGridView) findViewById(R.id.money_ly);
        balance_et = (EditText) findViewById(R.id.balance_et);
        need_et = (EditText) findViewById(R.id.need_et);
        TextView name = (TextView) findViewById(R.id.name);
        TextView title = (TextView) findViewById(R.id.title);
        SelectableRoundedImageView head = (SelectableRoundedImageView) findViewById(R.id.head);
        UserInfo userInfo = invInfoBean.getUser();
        name.setText(userInfo.getUalias());
        title.setText(invInfoBean.getItitle());
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), head);

        reward_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReward();
            }
        });
    }

    private void initGridView() {

        mAdapter = new MoneyAdapter(this, moneys);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        animationAdapter.setAbsListView(money_ly);

        money_ly.setAdapter(animationAdapter);

    }

    public void setGridViewSelect(int index){
        int length = moneys.length;
        if (index == length - 1) {
            need_et.setEnabled(true);
            need_et.setText("1");
        } else {
            need_et.setEnabled(false);
            need_et.setText(moneys[index]);
        }
    }

}
