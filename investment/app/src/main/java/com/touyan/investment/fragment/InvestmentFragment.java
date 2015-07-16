package com.touyan.investment.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.activity.*;
import com.touyan.investment.adapter.InvestmentPagerAdapter;
import com.touyan.investment.mview.FilterView;

import java.util.ArrayList;

public class InvestmentFragment extends AbsFragment {

    private LayoutInflater mInflater;

    private ViewPager viewPager;
    private InvestmentPagerAdapter adapter;

    private TextView menuLeft;
    private RelativeLayout menuRight;

    private FilterView menuLeftPoupWindow;
    private FilterView menuRightPoupWindow;
    private int gapLeft;
    private int gapRigth;

    private final static int INVESTMENT_NEWS = 0;//资讯
    private final static int INVESTMENT_ACT = INVESTMENT_NEWS + 1;//活动
    private final static int INVESTMENT_OFFER = INVESTMENT_ACT + 1;//悬赏

    private int currentPager = INVESTMENT_NEWS;

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = getActivity().getLayoutInflater();
        View viewGroup = mInflater.inflate(R.layout.fragment_investment, container, false);
        init(getChildFragmentManager(), viewGroup);
        initActionBar(viewGroup);
        return viewGroup;
    }

    // 初始化资源
    private void init(FragmentManager fm, View viewGroup) {

        ArrayList<AbsFragment> fragments = new ArrayList<AbsFragment>();
        fragments.add(new InvInfoFragment());
        fragments.add(new InvActFragment());
        fragments.add(new InvOfferFragment());

        adapter = new InvestmentPagerAdapter(fm, fragments);
        viewPager = (ViewPager) viewGroup.findViewById(R.id.view_pager);

        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) viewGroup.findViewById(R.id.viewpager_tab);
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                LinearLayout custom_ly = (LinearLayout) mInflater.inflate(R.layout.tab_investment_icon, container, false);
                switch (position) {
                    case INVESTMENT_NEWS:
                        setIconInfo(custom_ly, R.string.investment_info, R.string.investment_info_es);
                        break;
                    case INVESTMENT_ACT:
                        setIconInfo(custom_ly, R.string.investment_act, R.string.investment_act_es);
                        break;
                    case INVESTMENT_OFFER:
                        setIconInfo(custom_ly, R.string.investment_offer, R.string.investment_offer_es);
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
                return custom_ly;
            }
        });

        viewPagerTab.setViewPager(viewPager);

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPager = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void setIconInfo(ViewGroup custom_ly, int stringid, int es_stringid) {
        TextView title = (TextView) custom_ly.findViewById(R.id.title);
        TextView title_es = (TextView) custom_ly.findViewById(R.id.title_es);
        title.setText(stringid);
        title_es.setText(es_stringid);
    }

    @Override
    public void scrollToTop() {
        initActionBar(getView());
    }

    public void initActionBar(View viewGroup) {
        menuLeft = (TextView) viewGroup.findViewById(R.id.toolbar_left_btn);
        TextView toolbar_right_tv = (TextView) viewGroup.findViewById(R.id.toolbar_right_tv);
        TextView toolbar_intermediate_tv = (TextView) viewGroup.findViewById(R.id.toolbar_intermediate_tv);
        menuRight = (RelativeLayout) viewGroup.findViewById(R.id.toolbar_right_btn);

        toolbar_intermediate_tv.setText(R.string.main_investment);
        menuRight.setVisibility(View.VISIBLE);
        toolbar_right_tv.setVisibility(View.VISIBLE);
        setToolbarMenuAnchor(menuLeft, R.drawable.menu_normal, AbsActivity.LEFT);
        setToolbarMenuAnchor(toolbar_right_tv, R.drawable.publish_normal, AbsActivity.RIGHT);
        menuLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showMenuLeft();
            }
        });

        menuRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                toActivity();
            }
        });
    }

    private void toActivity() {
        if (currentPager == INVESTMENT_NEWS) {
            toReleaseInfo();
        } else if (currentPager == INVESTMENT_ACT) {
            showMenuRigth();
        } else if (currentPager == INVESTMENT_OFFER) {
            toOfferRelease();
        }
    }

    private void toInfoAttention() {
        Intent mIntent = new Intent(getActivity(), InfoAttentionActivity.class);
        getActivity().startActivity(mIntent);
        getActivity().overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void toOfferRelease() {
        Intent mIntent = new Intent(getActivity(), OfferReleaseActivity.class);
        getActivity().startActivity(mIntent);
        getActivity().overridePendingTransition(R.anim.push_translate_in_top, 0);
    }

    private void toReleaseInfo() {
        Intent mIntent = new Intent(getActivity(), InfoReleaseActivity.class);
        getActivity().startActivity(mIntent);
        getActivity().overridePendingTransition(R.anim.push_translate_in_top, 0);
    }

    private void toReleaseRoadshow() {
        Intent mIntent = new Intent(getActivity(), ActReleaseRoadshowActivity.class);
        getActivity().startActivity(mIntent);
        getActivity().overridePendingTransition(R.anim.push_translate_in_top, 0);
    }

    private void toReleaseProduct() {
        Intent mIntent = new Intent(getActivity(), ActReleaseProductActivity.class);
        getActivity().startActivity(mIntent);
        getActivity().overridePendingTransition(R.anim.push_translate_in_top, 0);
    }


    private void showMenuLeft() {
        if (menuLeftPoupWindow == null) {
            gapLeft = (int) getResources().getDimension(R.dimen.content_10dp);
            RelativeLayout conentView = (RelativeLayout) mInflater.inflate(R.layout.dialog_inv_info_menu, null);
            TextView attention_tv = (TextView) conentView.findViewById(R.id.attention_tv);
            TextView product_tv = (TextView) conentView.findViewById(R.id.product_tv);
            TextView roadshow_tv = (TextView) conentView.findViewById(R.id.roadshow_tv);
            attention_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuLeftPoupWindow.dismiss();
                    toInfoAttention();
                }
            });
            product_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuLeftPoupWindow.dismiss();
                    toInfoAttention();
                }
            });
            roadshow_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuLeftPoupWindow.dismiss();
                    toInfoAttention();
                }
            });
            menuLeftPoupWindow = new FilterView(getActivity(), conentView, R.style.AnimationPreviewLeft);
        }
        menuLeftPoupWindow.showPopupWindow(menuLeft, 0, -gapLeft);
    }

    private void showMenuRigth() {
        if (menuRightPoupWindow == null) {
            gapRigth = (int) getResources().getDimension(R.dimen.content_10dp);
            RelativeLayout conentView = (RelativeLayout) mInflater.inflate(R.layout.dialog_inv_act_menu, null);
            TextView product_tv = (TextView) conentView.findViewById(R.id.product_tv);
            TextView roadshow_tv = (TextView) conentView.findViewById(R.id.roadshow_tv);
            product_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuRightPoupWindow.dismiss();
                    toReleaseProduct();
                }
            });
            roadshow_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuRightPoupWindow.dismiss();
                    toReleaseRoadshow();
                }
            });
            menuRightPoupWindow = new FilterView(getActivity(), conentView, R.style.AnimationPreviewRigth);
        }
        menuRightPoupWindow.showPopupWindow(menuRight, 0, -gapRigth);
    }

    public void setToolbarMenuAnchor(TextView view, int iconid, int anchor) {
        Drawable drawable = getResources().getDrawable(iconid);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (anchor == AbsActivity.TOP) {
            view.setCompoundDrawables(null, drawable, null, null);
        } else if (anchor == AbsActivity.BOTTOM) {
            view.setCompoundDrawables(null, null, null, drawable);
        } else if (anchor == AbsActivity.LEFT) {
            view.setCompoundDrawables(drawable, null, null, null);
        } else if (anchor == AbsActivity.RIGHT) {
            view.setCompoundDrawables(null, null, drawable, null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getChildFragmentManager().getFragments().get(currentPager).onActivityResult(requestCode, resultCode, data);
    }
}
