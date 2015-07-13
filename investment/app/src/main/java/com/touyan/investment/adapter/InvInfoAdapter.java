package com.touyan.investment.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.core.util.DateUtil;
import com.core.util.StringUtil;
import com.joooonho.SelectableRoundedImageView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.touyan.investment.R;
import com.touyan.investment.activity.InfoDetailActivity;
import com.touyan.investment.activity.InfoRewardActivity;
import com.touyan.investment.bean.main.InvInfoBean;
import com.touyan.investment.bean.main.InvInfoResult;
import com.touyan.investment.bean.main.InvInfoUserInfo;
import com.touyan.investment.mview.BottomView;
import com.touyan.investment.mview.MGridView;

import java.util.ArrayList;

public class InvInfoAdapter extends BaseAdapter implements View.OnClickListener {

    private LayoutInflater mInflater;

    private ArrayList<InvInfoBean> list;

    private Activity mContext;

    private BottomView mBottomView;

    public InvInfoAdapter(Activity context, ArrayList<InvInfoBean> _list) {
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void refresh(ArrayList<InvInfoBean> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_inv_info, null);
            holder = new ViewHolder();
            holder.info_ly = (LinearLayout) convertView.findViewById(R.id.info_ly);
            holder.share_ly = (LinearLayout) convertView.findViewById(R.id.share_ly);
            holder.review_ly = (LinearLayout) convertView.findViewById(R.id.review_ly);
            holder.reward_ly = (LinearLayout) convertView.findViewById(R.id.reward_ly);
            holder.share_ib = (ImageButton) convertView.findViewById(R.id.share_ib);
            holder.review_ib = (ImageButton) convertView.findViewById(R.id.review_ib);
            holder.reward_ib = (ImageButton) convertView.findViewById(R.id.reward_ib);
            holder.gridview = (MGridView) convertView.findViewById(R.id.gridview);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.value = (TextView) convertView.findViewById(R.id.value);
            holder.share_tv = (TextView) convertView.findViewById(R.id.share_tv);
            holder.review_tv = (TextView) convertView.findViewById(R.id.review_tv);
            holder.reward_tv = (TextView) convertView.findViewById(R.id.reward_tv);
            holder.share_ib.setOnClickListener(this);
            holder.review_ib.setOnClickListener(this);
            holder.reward_ib.setOnClickListener(this);
            holder.info_ly.setOnClickListener(this);
            holder.share_ly.setOnClickListener(this);
            holder.review_ly.setOnClickListener(this);
            holder.reward_ly.setOnClickListener(this);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        InvInfoBean infoBean = list.get(position);
        InvInfoUserInfo userInfo = infoBean.getUser();
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), holder.head);
        holder.name.setText(userInfo.getUalias());
        String dateStr = DateUtil.ConverToString(infoBean.getPubstm(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        holder.date.setText(dateStr);
        holder.name.setText(userInfo.getUalias());
        holder.title.setText(infoBean.getItitle());
        holder.value.setText(infoBean.getContnt());
        holder.share_tv.setText(infoBean.getTransNum());
        holder.review_tv.setText(infoBean.getReplyNum());
        holder.reward_tv.setText(infoBean.getRewardsAmount());

        String[] photos = StringUtil.split(infoBean.getPubsid(),",");
        InfoGridAdapter gridAdapter = new InfoGridAdapter(mContext,photos);
        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(gridAdapter);
        animationAdapter.setAbsListView(holder.gridview);
        holder.gridview.setAdapter(animationAdapter);
        holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return convertView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.share_ib || id == R.id.share_ly) {
            selectPict();
        } else if (id == R.id.review_ib || id == R.id.review_ly) {
            toInfoDetail(1);
        } else if (id == R.id.reward_ib || id == R.id.reward_ly) {
            toInfoReward();
        } else if (id == R.id.info_ly) {
            toInfoDetail(-1);
        }
    }

    private void toInfoDetail(int index) {
        Intent mIntent = new Intent(mContext, InfoDetailActivity.class);
        mIntent.putExtra(InfoDetailActivity.KEY, index);
        mContext.startActivity(mIntent);
        mContext.overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void toInfoReward() {
        Intent mIntent = new Intent(mContext, InfoRewardActivity.class);
        mContext.startActivity(mIntent);
        mContext.overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    class ViewHolder {
        SelectableRoundedImageView head;
        MGridView gridview;
        TextView name;
        TextView date;
        TextView title;
        TextView value;
        TextView share_tv;
        TextView review_tv;
        TextView reward_tv;
        LinearLayout info_ly;
        LinearLayout share_ly;
        LinearLayout review_ly;
        LinearLayout reward_ly;
        ImageButton share_ib;
        ImageButton review_ib;
        ImageButton reward_ib;
    }

    private void selectPict() {
        if (mBottomView != null) {
            mBottomView.showBottomView(true);
            return;
        }
        mBottomView = new BottomView(mContext, R.style.BottomViewTheme_Defalut, R.layout.bottom_view);
        mBottomView.setAnimation(R.style.BottomToTopAnim);
        TextView shareFriend = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_2);
        TextView shareGroup = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_3);
        TextView cancel = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_cancel);

        ShareButtonOnClickListener listener = new ShareButtonOnClickListener();
        shareFriend.setOnClickListener(listener);
        shareGroup.setOnClickListener(listener);
        cancel.setOnClickListener(listener);

        mBottomView.showBottomView(true);
    }

    class ShareButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bottom_tv_2:
                case R.id.bottom_tv_3:
                    break;
                case R.id.bottom_tv_cancel:
                    mBottomView.dismissBottomView();
                    break;
            }
        }
    }

}