package com.touyan.investment.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.bean.user.ModifyUserInfoResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.UserManager;

import java.util.ArrayList;

/**
 * Created by zxh on 2015/7/13.
 */
public class ModifyUserInfoActivity extends AbsActivity implements View.OnClickListener {

    private final static int MODIFY_DATA = 0;

    private final static int REQUESTCODE_USERTAG = 0;

    private UserInfo userInfo;

    private ImageView userHeadImage;
    private EditText userNameEdit;
    private EditText userCompanyEdit;
    private EditText userCityEdit;
    private EditText userOccupationEdit;

    private LinearLayout userHeadBtn;
    private LinearLayout userTagBtn;

    private StringBuffer tagsStr = null;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case MODIFY_DATA:
                    loadModifyUserInfoData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 处理登陆数据
     *
     * @param resposne
     */
    private void loadModifyUserInfoData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            ModifyUserInfoResult result = (ModifyUserInfoResult) resposne.getData();
            App.getInstance().setgUserInfo(result.getUsinfo());
            scrollToFinishActivity();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
        findView();
        initBtnListener();
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

        userHeadBtn = (LinearLayout) findViewById(R.id.user_head_btn);
        userTagBtn = (LinearLayout) findViewById(R.id.user_tag_btn);


    }

    private void initBtnListener() {
        userHeadBtn.setOnClickListener(this);
        userTagBtn.setOnClickListener(this);
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
                        StringUtil.isNotBlank(tagsStr.toString()) ? tagsStr.toString() : userInfo.getTags(),
                        null,
                        null,
                        activityHandler,
                        MODIFY_DATA);
                dialogShow(R.string.data_uploading);
                break;
            case R.id.user_head_btn:
                break;
            case R.id.user_tag_btn:
                toActivityForResult(ModifyUserInfoActivity.this, ModifyUserTagActivity.class, REQUESTCODE_USERTAG);
                break;
        }
    }

    private void toActivityForResult(Activity activity, Class activityClass, int requestCode) {
        Intent intent = new Intent(activity, activityClass);
        startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_USERTAG && data != null) {
            ArrayList<Integer> userTagItemList = data.getIntegerArrayListExtra(KEY);
            if (userTagItemList == null) {
                return;
            }

            String[] userTagArray = getResources().getStringArray(R.array.user_tag_array);
            tagsStr = new StringBuffer();
            for (int i = 0; i < userTagItemList.size(); i++) {
                tagsStr.append(userTagArray[userTagItemList.get(i)]).append("/");
            }

        }
    }
}
