package com.touyan.investment.fragment;

import android.app.ActionBar;
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
import com.touyan.investment.activity.ModifyUserInfoActivity;
import com.touyan.investment.bean.user.TagBean;
import com.touyan.investment.bean.user.UserInfo;

import java.util.ArrayList;

public class MeFragment extends AbsFragment implements View.OnClickListener {

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
                userAuthenticationText.setText(R.string.user_unauthenticated);
            } else {
                userAuthenticationText.setBackgroundResource(R.drawable.user_authenticated);
                userAuthenticationText.setText(R.string.user_authenticated);
            }
        }

        if (userInfo.getTags() != null) {
            if (userInfo.getTags().size() > 0) {
                initUserTag(userInfo.getTags());
                userTagLayout.setVisibility(View.VISIBLE);
            } else {
                userTagLayout.setVisibility(View.GONE);
            }
        } else {
            userTagLayout.setVisibility(View.GONE);
        }


    }

    private void initUserTag(ArrayList<TagBean> tags) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.content_10dp), 0, getResources().getDimensionPixelSize(R.dimen.content_10dp), 0);
        for (int i = 0; i < tags.size(); i++) {
            RelativeLayout tagItem = (RelativeLayout) mInflater.inflate(R.layout.item_user_tag, null);
            tagItem.setLayoutParams(layoutParams);
            TextView tagName = (TextView) tagItem.findViewById(R.id.tag_name);
            tagName.setText(tags.get(i).getTgname());
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
        switch (view.getId()) {
            case R.id.user_modify:
                toActivity(ModifyUserInfoActivity.class);
                break;
            case R.id.user_follow:
                break;
            case R.id.user_fans:
                break;
            case R.id.user_collect:
                break;
            case R.id.user_wallet:
                break;
            case R.id.user_information:
                break;
            case R.id.user_activity:
                break;
            case R.id.user_reward:
                break;
            case R.id.user_setting:
                break;
            case R.id.user_authenticated_btn:
                break;
        }
    }

    private void toActivity(Class activityClass) {
        Activity activity = MeFragment.this.getActivity();
        Intent intent = new Intent(activity, activityClass);
        startActivity(intent);
        activity.overridePendingTransition(R.anim.push_translate_in_right, 0);
    }
}
