package com.touyan.investment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.joooonho.SelectableRoundedImageView;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;

public class MeFragment extends AbsFragment implements View.OnClickListener {

    private LayoutInflater mInflater;

    private SelectableRoundedImageView userHeadImage;//用户头像
    private ImageButton modifyBtn;                   //右上角编辑按钮
    private TextView userNameText;                   //用户姓名
    private TextView userCompanyText;                //用户公司
    private TextView userCityText;                   //用户城市
    private TextView userOccupationText;             //用户职位
    private TextView userAuthenticationText;         //用户是否认证标签
    private LinearLayout userTagLayout;              //用户标签列表
    private TextView userFollowBtn;                  //用户关注按钮
    private TextView userFansBtn;                    //用户粉丝按钮
    private TextView userCollectBtn;                 //用户收藏按钮
    private TextView userWalletBtn;                  //用户钱包按钮
    private LinearLayout userInfoBtn;                //用户资讯
    private LinearLayout userActivityBtn;            //用户活动
    private LinearLayout userRewardBtn;              //用户悬赏
    private LinearLayout userSettingBtn;             //用户设置
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

        initBtnListener();


    }

    private void initUserInfo(){

    }

    private void initBtnListener(){
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
}
