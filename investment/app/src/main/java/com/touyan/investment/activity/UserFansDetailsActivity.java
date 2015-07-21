package com.touyan.investment.activity;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.openapi.OpenApiSimpleResult;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.EditerAdapter;
import com.touyan.investment.adapter.InvestmentPagerAdapter;
import com.touyan.investment.bean.user.OtherInfoResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.fragment.CollectedInvActFragment;
import com.touyan.investment.fragment.CollectedInvInfoFragment;
import com.touyan.investment.fragment.CollectedInvOfferFragment;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.FilterView;
import com.touyan.investment.mview.NoScrollViewPager;

import java.util.ArrayList;


/**
 * Created by Administrator on 2015/7/15.
 */
public class UserFansDetailsActivity extends AbsActivity {
    private UserManager userManager = new UserManager();
    private static final int OTHER_DATA = 0;
    private static final int FOLLOW_OTHER = 1;

    private SelectableRoundedImageView userHeadImage;//用户头像
    private TextView userNameText;                   //用户姓名
    private TextView userCompanyText;                //用户公司
    private TextView userCityText;                   //用户城市
    private TextView userOccupationText;             //用户职位
    private TextView userFollowText;                 //用户关注数量
    private TextView userFansText;                   //用户粉丝数量
    private TextView userAuthenticationText;         //用户是否认证标签
    private LinearLayout userTagLayout;              //用户标签列表
    private RelativeLayout followBtn;

    private TextView fragmentTitle;
    private NoScrollViewPager viewPager;

    private InvestmentPagerAdapter adapter;
    private ArrayList<AbsFragment> fragments;
    private final static int INVESTMENT_NEWS = 0;//资讯
    private final static int INVESTMENT_ACT = INVESTMENT_NEWS + 1;//活动
    private final static int INVESTMENT_OFFER = INVESTMENT_ACT + 1;//悬赏

    private int currentPager = INVESTMENT_NEWS;

    private FilterView menuRightPoupWindow;
    private int gapRigth;


    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case OTHER_DATA:
                    loadOtherData((CommonResponse) msg.obj);
                    break;
                case FOLLOW_OTHER:
                    followOtherData((CommonResponse) msg.obj);
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
        initViewPager(getSupportFragmentManager());
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
        viewPager = (NoScrollViewPager) findViewById(R.id.view_pager);
        fragmentTitle = (TextView) findViewById(R.id.fragment_title_text);

        fragmentTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenuRigth();
            }
        });

        followBtn = (RelativeLayout) findViewById(R.id.follow_btn);

    }

    private void initViewPager(FragmentManager fm) {
        fragments = new ArrayList<AbsFragment>();
        fragments.add(new CollectedInvInfoFragment());
        fragments.add(new CollectedInvActFragment());
        fragments.add(new CollectedInvOfferFragment());

        adapter = new InvestmentPagerAdapter(fm, fragments);

        viewPager.setAdapter(adapter);

    }

    private void loadOtherData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            OtherInfoResult result = (OtherInfoResult) resposne.getData();
            initUserInfo(result, result.getMysubnum(), result.getSubmynum());
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void followOtherData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            OpenApiSimpleResult result = (OpenApiSimpleResult) resposne.getData();

            followBtn.setClickable(false);
            ((TextView) followBtn.getChildAt(0)).setText("已关注");
            ((TextView) followBtn.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor_666));
            Drawable ic = getResources().getDrawable(R.drawable.user_followed_btn);
            ic.setBounds(0, 0, ic.getMinimumWidth(), ic.getMinimumHeight());
            ((TextView) followBtn.getChildAt(0)).setCompoundDrawables(ic, null, null, null);

        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void initUserInfo(OtherInfoResult result, int followNum, int fansNum) {

        if (result.getInfo() == null) {
            return;
        }
        ImageLoader.getInstance().displayImage(result.getInfo().getUphoto(), userHeadImage);

        userFollowText.setText(getResources().getString(R.string.user_follow) + " " + followNum);

        userFansText.setText(getResources().getString(R.string.user_fans) + " " + fansNum);

        if (StringUtil.isNotBlank(result.getInfo().getUalias())) {
            userNameText.setText(result.getInfo().getUalias());
        }
        if (StringUtil.isNotBlank(result.getInfo().getCompny())) {
            userCompanyText.setText(result.getInfo().getCompny());
        }
        if (StringUtil.isNotBlank(result.getInfo().getLocatn())) {
            userCityText.setText(result.getInfo().getLocatn());
        }
        if (StringUtil.isNotBlank(result.getInfo().getPostin())) {
            userOccupationText.setText(result.getInfo().getPostin());
        }
        if (StringUtil.isNotBlank(result.getInfo().getUisvip())) {
            if (result.getInfo().getUisvip().equals(UserInfo.ISVIP_CODE)) {
                userAuthenticationText.setBackgroundResource(R.drawable.user_unauthenticated);
                userAuthenticationText.setTextColor(getResources().getColor(R.color.white));
                userAuthenticationText.setText(R.string.user_unauthenticated);
            } else {
                userAuthenticationText.setBackgroundResource(R.drawable.user_authenticated);
                userAuthenticationText.setTextColor(getResources().getColor(R.color.theme));
                userAuthenticationText.setText(R.string.user_authenticated);
            }
        }
        String[] tags = getUserTags(result.getInfo().getTags());
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
        if (StringUtil.isNotBlank(result.getIsorder())) {
            if (result.getIsorder() == 0) {

                followBtn.setClickable(true);
                ((TextView) followBtn.getChildAt(0)).setText("关注");
                ((TextView) followBtn.getChildAt(0)).setTextColor(getResources().getColor(R.color.red));
                Drawable ic = getResources().getDrawable(R.drawable.user_follow_btn);
                ic.setBounds(0, 0, ic.getMinimumWidth(), ic.getMinimumHeight());
                ((TextView) followBtn.getChildAt(0)).setCompoundDrawables(ic, null, null, null);
                followBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userManager.followOtherInfo(UserFansDetailsActivity.this, getIntent().getStringExtra("otherid"), "" + getIntent().getStringExtra("userid"), activityHandler, FOLLOW_OTHER);
                        dialogShow(R.string.data_downloading);
                    }
                });

            } else if (result.getIsorder() == 1) {

                followBtn.setClickable(false);
                ((TextView) followBtn.getChildAt(0)).setText("已关注");
                ((TextView) followBtn.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor_666));
                Drawable ic = getResources().getDrawable(R.drawable.user_followed_btn);
                ic.setBounds(0, 0, ic.getMinimumWidth(), ic.getMinimumHeight());
                ((TextView) followBtn.getChildAt(0)).setCompoundDrawables(ic, null, null, null);

            }
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

        userManager.queryOtherInfo(this, getIntent().getStringExtra("otherid"), "" + getIntent().getStringExtra("userid"), activityHandler, OTHER_DATA);
        dialogShow(R.string.data_downloading);
    }

    private void showMenuRigth() {
        if (menuRightPoupWindow == null) {
            gapRigth = (int) getResources().getDimension(R.dimen.content_10dp);
            RelativeLayout conentView = (RelativeLayout) mInflater.inflate(R.layout.dialog_userfans_detail, null);
            TextView info = (TextView) conentView.findViewById(R.id.info);
            TextView act = (TextView) conentView.findViewById(R.id.act);
            TextView offer = (TextView) conentView.findViewById(R.id.offer);

            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuRightPoupWindow.dismiss();
                    setCurrentViewPager(INVESTMENT_NEWS);
                }
            });
            act.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuRightPoupWindow.dismiss();
                    setCurrentViewPager(INVESTMENT_ACT);

                }
            });
            offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuRightPoupWindow.dismiss();
                    setCurrentViewPager(INVESTMENT_OFFER);

                }
            });
            menuRightPoupWindow = new FilterView(this, conentView, R.style.AnimationPreviewRigth);
        }
        menuRightPoupWindow.showPopupWindow(fragmentTitle, 0, -gapRigth);
    }

    public void setCurrentViewPager(int currentPager) {
        this.currentPager = currentPager;
        switch (currentPager) {
            case INVESTMENT_NEWS:
                fragmentTitle.setText("资讯");
                break;
            case INVESTMENT_ACT:
                fragmentTitle.setText("活动");
                break;
            case INVESTMENT_OFFER:
                fragmentTitle.setText("悬赏");
                break;

        }
        viewPager.setCurrentItem(currentPager);
    }
}
