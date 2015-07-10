package com.touyan.investment.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import com.core.CommonResponse;
import com.handmark.pulltorefresh.PullToRefreshScrollView;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.manager.InvestmentManager;

public class FriendsActivity extends AbsActivity {

    private InvestmentManager manager = new InvestmentManager();

    private static final int INIT_LIST = 0x01;//初始化数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    //列表
    private PullToRefreshScrollView mScrollView;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne, int what) {
        dialogDismiss();
        if (what == INIT_LIST) {
            addTestData();
        } else {
            addTestData();
        }
//        if (resposne.isSuccess()) {
//            if (what == INIT_LIST) {
//                review_ly.removeAllViews();
//                addTestData();
//             } else {
//                addTestData();
//            }
//        } else {
//            CommonUtil.showToast(resposne.getErrorTip());
//        }
        mScrollView.onRefreshComplete();
    }

    private void addTestData() {
    }

    @Override
    public void EInit() {
        super.EInit();
        findView();
        getDataList(INIT_LIST);
    }


    private void getDataList(int what) {
        manager.LoginAct(this, "", "" + COUNT_MAX, activityHandler, what);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_info_detail;
    }

    @Override
    public void initActionBar() {
        setToolbarIntermediateStrID(R.string.friends);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRight(R.drawable.friends_add);
    }

    private void findView() {
    }

}
