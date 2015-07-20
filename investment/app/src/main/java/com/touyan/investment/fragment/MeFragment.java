package com.touyan.investment.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.core.util.StringUtil;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.activity.*;
import com.touyan.investment.bean.user.UserInfo;

public class MeFragment extends AbsFragment implements View.OnClickListener {

    private final static int REQUESTCODE_MODIFYUSERINFO = 0;

    private LayoutInflater mInflater;

    private UserInfo userInfo;

    private SelectableRoundedImageView userHeadImage;//用户头像
    private TextView userNameText;                   //用户姓名
    private TextView userCompanyText;                //用户公司
    private TextView userCityText;                   //用户城市
    private TextView userOccupationText;             //用户职位
    private TextView userAuthenticationText;         //用户是否认证标签
    private LinearLayout userTagLayout;              //用户标签列表

    private ImageButton modifyBtn;                   //右上角编辑按钮
    private TextView userFollowBtn;                  //用户关注按钮
    private TextView userFansBtn;                    //用户粉丝按钮
    private TextView userCollectBtn;                 //用户收藏按钮
    private TextView userWalletBtn;                  //用户钱包按钮
    private LinearLayout userInfoBtn;                //用户资讯按钮
    private LinearLayout userActivityBtn;            //用户活动按钮
    private LinearLayout userRewardBtn;              //用户悬赏按钮
    private LinearLayout userSettingBtn;             //用户设置按钮
    private Button userAuthenticationBtn;            //用户认证按钮

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = getActivity().getLayoutInflater();
        return mInflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    // 初始化资源
    private void init(View view) {
        userHeadImage = (SelectableRoundedImageView) view.findViewById(R.id.user_head);
        modifyBtn = (ImageButton) view.findViewById(R.id.user_modify);
        userNameText = (TextView) view.findViewById(R.id.user_name);
        userCompanyText = (TextView) view.findViewById(R.id.user_company);
        userCityText = (TextView) view.findViewById(R.id.user_city);
        userOccupationText = (TextView) view.findViewById(R.id.user_occupation);
        userAuthenticationText = (TextView) view.findViewById(R.id.user_authentication);
        userTagLayout = (LinearLayout) view.findViewById(R.id.user_tag_list);
        userFollowBtn = (TextView) view.findViewById(R.id.user_follow);
        userFansBtn = (TextView) view.findViewById(R.id.user_fans);
        userCollectBtn = (TextView) view.findViewById(R.id.user_collect);
        userWalletBtn = (TextView) view.findViewById(R.id.user_wallet);
        userInfoBtn = (LinearLayout) view.findViewById(R.id.user_information);
        userActivityBtn = (LinearLayout) view.findViewById(R.id.user_activity);
        userRewardBtn = (LinearLayout) view.findViewById(R.id.user_reward);
        userSettingBtn = (LinearLayout) view.findViewById(R.id.user_setting);
        userAuthenticationBtn = (Button) view.findViewById(R.id.user_authenticated_btn);
        initUserInfo();
        initBtnListener();


    }

    private void initUserInfo() {
        userInfo = App.getInstance().getgUserInfo();
        if (userInfo == null) {
            return;
        }
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), userHeadImage);
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

    private void initBtnListener() {
        modifyBtn.setOnClickListener(this);
        userFollowBtn.setOnClickListener(this);
        userFansBtn.setOnClickListener(this);
        userCollectBtn.setOnClickListener(this);
        userWalletBtn.setOnClickListener(this);
        userInfoBtn.setOnClickListener(this);
        userActivityBtn.setOnClickListener(this);
        userRewardBtn.setOnClickListener(this);
        userSettingBtn.setOnClickListener(this);
        userAuthenticationBtn.setOnClickListener(this);
    }

    @Override
    public void scrollToTop() {
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.user_modify:
                toActivityForResult(MeFragment.this.getActivity(), ModifyUserInfoActivity.class, REQUESTCODE_MODIFYUSERINFO);
                break;
            case R.id.user_follow:
                toActivity(MeFragment.this.getActivity(), UserFollowActivity.class);
                break;
            case R.id.user_fans:
                toActivity(MeFragment.this.getActivity(), UserFansActivity.class);
                break;
            case R.id.user_collect:
                toActivity(MeFragment.this.getActivity(), UserCollectActivity.class);
                break;
            case R.id.user_wallet:
                toActivity(MeFragment.this.getActivity(), UserWalletActivity.class);
                break;
            case R.id.user_information:
                intent = new Intent(getActivity(), MeInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.user_activity:
                intent = new Intent(getActivity(), MeActActivity.class);
                startActivity(intent);
                break;
            case R.id.user_reward:
                intent = new Intent(getActivity(), MeOfferRewardActivity.class);
                startActivity(intent);
                break;
            case R.id.user_setting:
                intent = new Intent(getActivity(), MeSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.user_authenticated_btn:
                break;
        }
    }

    private void toActivity(Activity activity, Class activityClass) {
        Intent intent = new Intent(activity, activityClass);
        startActivity(intent);
        activity.overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void toActivityForResult(Activity activity, Class activityClass, int requestCode) {
        Intent intent = new Intent(activity, activityClass);
        startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_MODIFYUSERINFO) {
            initUserInfo();
        }
    }
}
