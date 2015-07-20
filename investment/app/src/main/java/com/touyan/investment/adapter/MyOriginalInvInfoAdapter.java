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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsDetailActivity;
import com.touyan.investment.R;
import com.touyan.investment.activity.InfoDetailActivity;
import com.touyan.investment.activity.InfoRewardActivity;
import com.touyan.investment.activity.MeInfoActivity;
import com.touyan.investment.activity.UserCollectActivity;
import com.touyan.investment.bean.main.InvInfoBean;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.fragment.MeInfoFragment;
import com.touyan.investment.mview.BottomView;
import com.touyan.investment.mview.MGridView;

import java.util.ArrayList;

public class MyOriginalInvInfoAdapter extends EditerAdapter implements View.OnClickListener {

    private LayoutInflater mInflater;

    private ArrayList<InvInfoBean> list;

    private Activity mContext;

    private MeInfoFragment fragment;

    private BottomView mBottomView;

    public MyOriginalInvInfoAdapter(MeInfoFragment fragment, ArrayList<InvInfoBean> _list) {
        super(fragment.getActivity());
        this.list = _list;
        this.fragment = fragment;
        mContext = this.fragment.getActivity();
        mInflater = LayoutInflater.from(mContext);
        setCheckBoexListener(new OnCheckBoexListener() {
            @Override
            public void OnCheckBoexListener(View view, int index) {
                if (getCurrentState() != EditerAdapter.STATE_EDIT) {
                    if (checkedItemList.size() > 0) {
                        setCurrentState(EditerAdapter.STATE_REMOVE);
                        ((MeInfoActivity) mContext).changeEditState(EditerAdapter.STATE_REMOVE);
                        notifyDataSetChanged();
                    } else {
                        setCurrentState(EditerAdapter.STATE_COMPLETE);
                        ((MeInfoActivity) mContext).changeEditState(EditerAdapter.STATE_COMPLETE);
                        notifyDataSetChanged();
                    }
                }
            }
        });
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
    public View getChildView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
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
            holder.head = (SelectableRoundedImageView) convertView.findViewById(R.id.head);
            holder.gridview = (MGridView) convertView.findViewById(R.id.gridview);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.value = (TextView) convertView.findViewById(R.id.value);
            holder.share_tv = (TextView) convertView.findViewById(R.id.share_tv);
            holder.review_tv = (TextView) convertView.findViewById(R.id.review_tv);
            holder.reward_tv = (TextView) convertView.findViewById(R.id.reward_tv);
            holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    MGridView gridView = (MGridView) view.getParent();
                    int position = (Integer) gridView.getTag(R.id.item_position);
                    toInfoDetail(-1, position);
                }
            });
            holder.share_ib.setOnClickListener(this);
            holder.review_ib.setOnClickListener(this);
            holder.reward_ib.setOnClickListener(this);
            holder.info_ly.setOnClickListener(this);
            holder.share_ly.setOnClickListener(this);
            holder.review_ly.setOnClickListener(this);
            holder.reward_ly.setOnClickListener(this);
            InfoGridAdapter gridAdapter = new InfoGridAdapter(mContext, null);
            holder.gridview.setAdapter(gridAdapter);
            convertView.setTag(R.id.item_holder,holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.item_holder);
        }
        InvInfoBean infoBean = list.get(position);
        UserInfo userInfo = infoBean.getUser();
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), holder.head);
        holder.name.setText(userInfo.getUalias());
        String dateStr = DateUtil.ConverToString(infoBean.getPubstm(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        holder.date.setText(dateStr);
        holder.name.setText(userInfo.getUalias());
        holder.title.setText(infoBean.getItitle());
        holder.value.setText("\t\t\t" + infoBean.getContnt());
        holder.share_tv.setText(""+infoBean.getTransNum());
        holder.review_tv.setText(""+infoBean.getReplyNum());
        Double rewardsAmount = infoBean.getRewardsAmount();
        if(rewardsAmount!=null) {
            holder.reward_tv.setText("" +rewardsAmount);
        }

        String[] photos = StringUtil.split(infoBean.getPictue(), ",");
        if (photos == null || photos.length <= 0) {
            holder.gridview.setVisibility(View.GONE);
        } else {
            holder.gridview.setVisibility(View.VISIBLE);
            ((InfoGridAdapter) (holder.gridview.getAdapter())).refresh(photos);
        }

        holder.info_ly.setTag(R.id.item_position,position);
        holder.gridview.setTag(R.id.item_position,position);
        holder.review_ly.setTag(R.id.item_position,position);
        holder.reward_ib.setTag(R.id.item_position,position);
        holder.review_ib.setTag(R.id.item_position,position);
        holder.share_ib.setTag(R.id.item_position,position);
        holder.reward_ly.setTag(R.id.item_position,position);
        holder.reward_ly.setTag(R.id.item_position,position);
        return convertView;
    }

    @Override
    public int getCheckBoxLayout() {
        return R.id.checkbox_layout;
    }

    @Override
    public ArrayList<String> getIdList() {
        ArrayList<String> idList = new ArrayList<String>();
        for (int i = 0; i < checkedItemList.size(); i++) {
            idList.add(this.list.get(checkedItemList.get(i)).getInfoid());
        }
        return idList;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//      return null;
//    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int position;
        if (id == R.id.share_ib || id == R.id.share_ly) {
            position = (Integer) view.getTag(R.id.item_position);
            selectShare(position);
        } else if (id == R.id.review_ib || id == R.id.review_ly) {
            position = (Integer) view.getTag(R.id.item_position);
            toInfoDetail(-1, position);
        } else if (id == R.id.reward_ib || id == R.id.reward_ly) {
            position = (Integer) view.getTag(R.id.item_position);
            toInfoReward(position);
        } else if (id == R.id.info_ly) {
            position = (Integer) view.getTag(R.id.item_position);
            toInfoDetail(-1, position);
        }
    }

    private void toInfoDetail(int index, int position) {
        this.fragment.currentItemIndex = position;
        Intent mIntent = new Intent(mContext, InfoDetailActivity.class);
        mIntent.putExtra(InfoDetailActivity.KEY, index);
        mIntent.putExtra(InfoDetailActivity.KEY_DETAIL, list.get(position));
        fragment.startActivityForResult(mIntent, AbsDetailActivity.REQUSETCODE);
        mContext.overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    private void toInfoReward(int position) {
        this.fragment.currentItemIndex = position;
        Intent mIntent = new Intent(mContext, InfoRewardActivity.class);
        mIntent.putExtra(InfoRewardActivity.KEY, list.get(position));
        fragment.startActivityForResult(mIntent, AbsDetailActivity.REQUSETCODE);
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

    private void selectShare(int position) {
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