package com.touyan.investment.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.core.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.bean.user.UserInfo;

/**
 * Created by Administrator on 2015/7/13.
 */
public class ModifyUserInfoActivity extends AbsActivity implements View.OnClickListener {

    private UserInfo userInfo;

    private ImageView userHeadImage;                 //用户头像
    private EditText userNameEdit;                   //用户姓名
    private EditText userCompanyEdit;                //用户公司
    private EditText userCityEdit;                   //用户城市
    private EditText userOccupationEdit;             //用户职位


    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
        findView();
        initUserInfoViews();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_modify_userinfo;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarRightStrID(R.string.login_fulfil);
        setToolbarIntermediateStrID(R.string.modify_userinfo_toolbar_title);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRightOnClick(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        userHeadImage = (ImageView) findViewById(R.id.user_head);
        userNameEdit = (EditText) findViewById(R.id.user_name);
        userCompanyEdit = (EditText) findViewById(R.id.user_company);
        userCityEdit = (EditText) findViewById(R.id.user_city);
        userOccupationEdit = (EditText) findViewById(R.id.user_occupation);
    }

    private void initUserInfoViews() {
        userInfo = App.getInstance().getgUserInfo();
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), userHeadImage);
        if (StringUtil.isNotBlank(userInfo.getUalias())) {
            userNameEdit.setHint(userInfo.getUalias());
        }
        if (StringUtil.isNotBlank(userInfo.getCompny())) {
            userCompanyEdit.setHint(userInfo.getCompny());
        }
        if (StringUtil.isNotBlank(userInfo.getLocatn())) {
            userCityEdit.setHint(userInfo.getLocatn());
        }
        if (StringUtil.isNotBlank(userInfo.getPostin())) {
            userOccupationEdit.setHint(userInfo.getPostin());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_btn:
                break;
        }
    }
}
