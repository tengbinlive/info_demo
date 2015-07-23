package com.touyan.investment.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.core.CommonResponse;
import com.handmark.pulltorefresh.PullToRefreshListView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.GungNewsAdapter;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class RecommendFragment extends AbsFragment {

    private InvestmentManager manager = new InvestmentManager();

    private LayoutInflater mInflater;

    private static final int INIT_LIST = 0x01;//初始化数据处理

    private static final int COUNT_MAX = 15;//加载数据最大值

    //列表
    private PullToRefreshListView mListView;
    private ListView mActualListView;
    private GungNewsAdapter mAdapter;
    private SwingBottomInAnimationAdapter animationAdapter;

    private ArrayList<String> mArrayList;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case INIT_LIST:
                    loadData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne) {
        dialogDismiss();
        testData();
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
        mListView.onRefreshComplete();
    }

    private void testData() {
        mArrayList = new ArrayList<String>();
        mArrayList.add("");
        mArrayList.add("");
        mArrayList.add("");
        mArrayList.add("");
        mArrayList.add("");
        mArrayList.add("");
//        mAdapter.refresh(mArrayList);
        animationAdapter.reset();
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = getActivity().getLayoutInflater();
        View viewGroup = mInflater.inflate(R.layout.fragment_recommend, container, false);
        init(viewGroup);
        return viewGroup;
    }

    // 初始化资源
    private void init(View viewGroup) {
    }


    @Override
    public void scrollToTop() {
    }

}
