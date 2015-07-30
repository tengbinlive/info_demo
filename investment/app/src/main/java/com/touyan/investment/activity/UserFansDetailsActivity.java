package com.touyan.investment.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.adapter.InvestmentPagerAdapter;
import com.touyan.investment.bean.user.OtherInfoResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.fragment.MeActivityFragment;
import com.touyan.investment.fragment.MeInfoFragment;
import com.touyan.investment.fragment.MeOfferRewFragment;
import com.touyan.investment.helper.Util;
import com.touyan.investment.hx.HXCacheUtils;
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
    private final static int ADD_FRIEND = 2;


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
    private RelativeLayout addFriendBtn;

    private TextView fragmentTitle;
    private NoScrollViewPager viewPager;

    private InvestmentPagerAdapter adapter;
    private ArrayList<AbsFragment> fragments;
    private final static int INVESTMENT_NEWS = 0;//资讯
    private final static int INVESTMENT_ACT = INVESTMENT_NEWS + 1;//活动
    private final static int INVESTMENT_OFFER = INVESTMENT_ACT + 1;//悬赏

    private FilterView menuRightPoupWindow;
    private int gapRigth;

    private String userid;
    private String otherid;

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
                case ADD_FRIEND:
                    try {
                        EMContactManager.getInstance().addContact(otherid, "");
                        CommonUtil.showToast("添加好友请求已发送");
                    } catch (EaseMobException e) {
                        e.printStackTrace();
                    } break;
                default:
                    break;
            }
        }
    };

    @Override
    public void EInit() {
        userid = getIntent().getStringExtra("userid");
        otherid = getIntent().getStringExtra("otherid");
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
        addFriendBtn = (RelativeLayout) findViewById(R.id.add_friend_btn);
        boolean isFriend = HXCacheUtils.getInstance().getFriendsHashMap().containsKey(otherid);
        if(!isFriend&&!userid.equals(otherid)){
            addFriendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message mg = new Message();
                    mg.what = ADD_FRIEND;
                    activityHandler.sendMessage(mg);
                }
            });
        }else{
            ((TextView) addFriendBtn.getChildAt(0)).setText("聊天");
            Drawable ic = getResources().getDrawable(R.drawable.user_chat);
            ic.setBounds(0, 0, ic.getMinimumWidth(), ic.getMinimumHeight());
            ((TextView) addFriendBtn.getChildAt(0)).setCompoundDrawables(ic, null, null, null);
        }
    }

    private void initViewPager(FragmentManager fm) {
        fragments = new ArrayList<AbsFragment>();
        fragments.add(MeInfoFragment.newsInstance(MeInfoFragment.REWARD_MYORIGINAL,otherid));
        fragments.add(MeActivityFragment.newsInstance(MeActivityFragment.REWARD_MYRELEASE,otherid));
        fragments.add(new MeOfferRewFragment(otherid));

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
                        if (userid.equals(otherid)) {
                            CommonUtil.showToast("不能关注自己");
                            return;
                        }
                        userManager.followOtherInfo(UserFansDetailsActivity.this, otherid, userid, activityHandler, FOLLOW_OTHER);
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
        userManager.queryOtherInfo(this, otherid, userid, activityHandler, OTHER_DATA);
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
                    Util.setTextViewDrawaleAnchor(UserFansDetailsActivity.this, fragmentTitle, R.drawable.arrow_red_bottom, Util.RIGHT);
                    setCurrentViewPager(INVESTMENT_NEWS);
                }
            });
            act.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuRightPoupWindow.dismiss();
                    Util.setTextViewDrawaleAnchor(UserFansDetailsActivity.this, fragmentTitle, R.drawable.arrow_red_bottom, Util.RIGHT);
                    setCurrentViewPager(INVESTMENT_ACT);

                }
            });
            offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuRightPoupWindow.dismiss();
                    Util.setTextViewDrawaleAnchor(UserFansDetailsActivity.this, fragmentTitle, R.drawable.arrow_red_bottom, Util.RIGHT);
                    setCurrentViewPager(INVESTMENT_OFFER);

                }
            });
            menuRightPoupWindow = new FilterView(this, conentView, R.style.AnimationPreviewRigth);

        }
        Util.setTextViewDrawaleAnchor(UserFansDetailsActivity.this, fragmentTitle, R.drawable.arrow_red_top, Util.RIGHT);
        menuRightPoupWindow.showPopupWindow(fragmentTitle, 0, -gapRigth);
    }

    public void setCurrentViewPager(int currentPager) {
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

    public static void toOthersDetail(Activity formAct, String userid, String otherid) {
        Intent intent = new Intent(formAct, UserFansDetailsActivity.class);
        intent.putExtra("userid", userid);
        intent.putExtra("otherid", otherid);
        formAct.startActivity(intent);
        formAct.overridePendingTransition(R.anim.push_translate_in_right, 0);
    }
}
