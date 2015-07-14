package com.touyan.investment.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.core.CommonResponse;
import com.core.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.UserManager;

/**
 * Created by Administrator on 2015/7/13.
 */
public class ModifyUserInfoActivity extends AbsActivity implements View.OnClickListener {

    private final static int MODIFY_DATA = 0;

    private UserInfo userInfo;

    private ImageView userHeadImage;
    private EditText userNameEdit;
    private EditText userCompanyEdit;
    private EditText userCityEdit;
    private EditText userOccupationEdit;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case MODIFY_DATA:

                    break;
                default:
                    break;
            }
        }
    };
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
                userInfo = App.getInstance().getgUserInfo();
                UserManager userManager = new UserManager();
                userManager.modifyUserInfo(ModifyUserInfoActivity.this,
                        userInfo.getServno(),
                        StringUtil.isNotBlank(userNameEdit.getText().toString()) ? userNameEdit.getText().toString() : userInfo.getUalias(),
                        userInfo.getUphoto(),
                        StringUtil.isNotBlank(userCityEdit.getText().toString()) ? userCityEdit.getText().toString() : userInfo.getLocatn(),
                        userInfo.getInrank(),
                        StringUtil.isNotBlank(userCompanyEdit.getText().toString()) ? userCompanyEdit.getText().toString() : userInfo.getCompny(),
                        StringUtil.isNotBlank(userOccupationEdit.getText().toString()) ? userOccupationEdit.getText().toString() : userInfo.getPostin(),
                        userInfo.getTeleph(),
                        userInfo.getRscope(),
                        userInfo.getUisvip(),
                        userInfo.getTags(),
                        null,
                        null,
                        activityHandler,
                        MODIFY_DATA);
                break;
        }
    }
}
