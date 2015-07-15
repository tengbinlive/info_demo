package com.touyan.investment.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.adapter.UserFansGridViewAdapter;
import com.touyan.investment.bean.user.OtherInfoResult;
import com.touyan.investment.bean.user.QueryUserFansResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.GridViewWithHeaderAndFooter;

/**
 * Created by Administrator on 2015/7/15.
 */
public class UserFansDetailsActivity extends AbsActivity {

    private static final int OTHER_DATA = 0;

    private SelectableRoundedImageView userHeadImage;//用户头像
    private TextView userNameText;                   //用户姓名
    private TextView userCompanyText;                //用户公司
    private TextView userCityText;                   //用户城市
    private TextView userOccupationText;             //用户职位
    private TextView userFollowText;                 //用户关注数量
    private TextView userFansText;                   //用户粉丝数量
    private TextView userAuthenticationText;         //用户是否认证标签
    private LinearLayout userTagLayout;              //用户标签列表


    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case OTHER_DATA:
                    loadOtherData((CommonResponse) msg.obj);
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
        queryOtherInfo();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_userfans_details;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft("");
        setToolbarRightVisbility(View.INVISIBLE, View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        userHeadImage = (SelectableRoundedImageView) findViewById(R.id.user_head);
        userNameText = (TextView) findViewById(R.id.user_name);
        userCompanyText = (TextView) findViewById(R.id.user_company);
        userCityText = (TextView) findViewById(R.id.user_city);
        userOccupationText = (TextView) findViewById(R.id.user_occupation);
        userAuthenticationText = (TextView) findViewById(R.id.user_authentication);
        userTagLayout = (LinearLayout) findViewById(R.id.user_tag_list);
        userFollowText = (TextView) findViewById(R.id.user_follow_num);
        userFansText = (TextView) findViewById(R.id.user_fans_num);
    }

    private void loadOtherData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            OtherInfoResult result = (OtherInfoResult) resposne.getData();
            initUserInfo(result.getInfo(), result.getMysubnum(), result.getSubmynum());
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void initUserInfo(UserInfo userInfo, int followNum, int fansNum) {

        if (userInfo == null) {
            return;
        }
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), userHeadImage);

        userFollowText.setText(getResources().getString(R.string.user_follow) + " " + followNum);

        userFansText.setText(getResources().getString(R.string.user_fans) + " " + fansNum);

        if (StringUtil.isNotBlank(userInfo.getUalias())) {
            userNameText.setText(userInfo.getUalias());
        }
        if (StringUtil.isNotBlank(userInfo.getCompny())) {
            userCompanyText.setText(userInfo.getCompny());
        }
        if (StringUtil.isNotBlank(userInfo.getLocatn())) {
            userCityText.setText(userInfo.getLocatn());
        }
        if (StringUtil.isNotBlank(userInfo.getPostin())) {
            userOccupationText.setText(userInfo.getPostin());
        }
        if (StringUtil.isNotBlank(userInfo.getUisvip())) {
            if (userInfo.getUisvip().equals(UserInfo.ISVIP_CODE)) {
                userAuthenticationText.setBackgroundResource(R.drawable.user_unauthenticated);
                userAuthenticationText.setTextColor(getResources().getColor(R.color.white));
                userAuthenticationText.setText(R.string.user_unauthenticated);
            } else {
                userAuthenticationText.setBackgroundResource(R.drawable.user_authenticated);
                userAuthenticationText.setTextColor(getResources().getColor(R.color.theme));
                userAuthenticationText.setText(R.string.user_authenticated);
            }
        }
        String[] tags = getUserTags(userInfo.getTags());
        if (tags != null) {
            if (tags.length > 0) {
                initUserTag(tags);
                userTagLayout.setVisibility(View.VISIBLE);
            } else {
                userTagLayout.setVisibility(View.GONE);
            }
        } else {
            userTagLayout.setVisibility(View.GONE);
        }
    }

    private String[] getUserTags(String tagStr) {
        String[] split = StringUtil.split(tagStr, "/");
        return split;
    }

    private void initUserTag(String[] tags) {
        userTagLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.content_10dp), 0, getResources().getDimensionPixelSize(R.dimen.content_10dp), 0);
        for (int i = 0; i < tags.length; i++) {
            RelativeLayout tagItem = (RelativeLayout) mInflater.inflate(R.layout.item_user_tag, null);
            tagItem.setLayoutParams(layoutParams);
            TextView tagName = (TextView) tagItem.findViewById(R.id.tag_name);
            tagName.setText(tags[i]);
            userTagLayout.addView(tagItem);
        }
    }

    private void queryOtherInfo() {

        UserManager userManager = new UserManager();
        userManager.queryOtherInfo(this, getIntent().getStringExtra("otherid"), "" + getIntent().getStringExtra("userid"), activityHandler, OTHER_DATA);
        dialogShow(R.string.data_downloading);
    }


}
