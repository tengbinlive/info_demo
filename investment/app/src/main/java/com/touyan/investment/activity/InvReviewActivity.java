package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.InvReViewParam;
import com.touyan.investment.bean.main.InvReplysBean;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.EditTextWithDelete;

import java.sql.Date;

public class InvReviewActivity extends AbsActivity {

    public final static String KEY_TYPE = "KEY_TYPE";

    private String reviewType;

    private EditTextWithDelete review_value;

    private String id;

    private InvReplysBean replysBean;

    private static final int LOAD_DATA = 0x01;//初始化数据处理

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            CommonUtil.showToast(R.string.success_review);
            Intent intent = new Intent();
            intent.putExtra(KEY, replysBean);
            setResult(AbsDetailActivity.REQUSETCODE);
            finish();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    @Override
    public void EInit() {
        reviewType = getIntent().getStringExtra(KEY_TYPE);
        id = getIntent().getStringExtra(KEY);
        setToolbarIntermediateStrID(InvReViewParam.TYPE_OFFER.equals(reviewType) ? R.string.bottom_menu_reply : R.string.review);
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_inv_review;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft(0, R.string.cancel);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRightStrID(R.string.send);
        setToolbarRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReView();
            }
        });
    }

    private void findView() {
        review_value = (EditTextWithDelete) findViewById(R.id.review_value);
    }

    //发表评论
    private void sendReView() {
        String contnt = review_value.getText().toString();
        if (StringUtil.isBlank(contnt)) {
            CommonUtil.showToast(R.string.contents_not_empty);
            return;
        }
        dialogShow(R.string.reviewing);
        replysBean = new InvReplysBean();
        replysBean.setUser(App.getInstance().getgUserInfo());
        replysBean.setContnt(contnt);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        replysBean.setRptime(curDate);
        InvestmentManager manager = new InvestmentManager();
        manager.replyDiscuss(this, id, App.getInstance().getgUserInfo().getServno(), contnt, reviewType, activityHandler, LOAD_DATA);
    }

}
