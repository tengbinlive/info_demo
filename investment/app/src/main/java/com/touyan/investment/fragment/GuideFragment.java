package com.touyan.investment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.activity.LoginActivity;

public class GuideFragment extends AbsFragment {

    private final static String KEY = "GUIDEID";

    private LayoutInflater mInflater;

    private int mGuideid;

    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     */
    public static GuideFragment newInstance(int guideid) {
        GuideFragment f = new GuideFragment();
        Bundle args = new Bundle();
        args.putInt(KEY, guideid);
        f.setArguments(args);
        return f;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = getActivity().getLayoutInflater();
        return mInflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGuideid = getArguments().getInt(KEY);
        init();
    }

    // 初始化资源
    private void init() {
        ImageView background_iv = (ImageView) getView().findViewById(R.id.background_iv);
        ImageView guide_bt = (ImageView) getView().findViewById(R.id.guide_bt);
        background_iv.setImageResource(mGuideid);
        if (mGuideid == R.drawable.guide_04) {
            guide_bt.setVisibility(View.VISIBLE);
            guide_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toLogining();
                }
            });
        }
    }

    private void toLogining() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void scrollToTop() {
    }

}
