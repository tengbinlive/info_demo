package com.touyan.investment.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.mview.IndexableListView;
import com.touyan.investment.mview.PullToRefreshIndexableListView;

/**
 * Created by Administrator on 2015/7/27.
 */
public class InviteContactsActivity extends AbsActivity {

    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.ptflistview)
    PullToRefreshIndexableListView ptflistview;
    @Bind(R.id.loading_message)
    TextView loadingMessage;
    @Bind(R.id.ll_listEmpty)
    LinearLayout llListEmpty;

    IndexableListView listview;

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
        findView();

    }

    @Override
    public int getContentView() {
        return R.layout.activity_invite_contacts;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediate("联系人");
        setToolbarRightVisbility(View.INVISIBLE, View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        listview = ptflistview.getRefreshableView();
        listview.setFastScrollEnabled(true);
        listview.setEmptyView(llListEmpty);
    }


}
