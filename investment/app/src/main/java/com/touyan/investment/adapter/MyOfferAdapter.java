package com.touyan.investment.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.core.util.DateUtil;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.R;
import com.touyan.investment.activity.MeActActivity;
import com.touyan.investment.activity.MeOfferRewardActivity;
import com.touyan.investment.bean.main.InvOfferBean;
import com.touyan.investment.bean.user.UserInfo;

import java.util.ArrayList;

public class MyOfferAdapter extends EditerAdapter {

    private LayoutInflater mInflater;

    private ArrayList<InvOfferBean> list;

    private Context mContext;


    public MyOfferAdapter(Context context, ArrayList<InvOfferBean> _list) {
        super((Activity)context);
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        setCheckBoexListener(new OnCheckBoexListener() {
            @Override
            public void OnCheckBoexListener(View view, int index) {
                if (getCurrentState() != EditerAdapter.STATE_EDIT) {
                    if (checkedItemList.size() > 0) {
                        setCurrentState(EditerAdapter.STATE_REMOVE);
                        ((MeOfferRewardActivity) mContext).changeEditState(EditerAdapter.STATE_REMOVE);
                        notifyDataSetChanged();
                    } else {
                        setCurrentState(EditerAdapter.STATE_COMPLETE);
                        ((MeOfferRewardActivity) mContext).changeEditState(EditerAdapter.STATE_COMPLETE);
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

    public void refresh(ArrayList<InvOfferBean> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getChildView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_inv_offer, null);
            holder = new ViewHolder();
            holder.head = (SelectableRoundedImageView) convertView.findViewById(R.id.head);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.contents = (TextView) convertView.findViewById(R.id.contents);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            holder.reward_money = (TextView) convertView.findViewById(R.id.reward_money);
            holder.review_people_num = (TextView) convertView.findViewById(R.id.review_people_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InvOfferBean bean = list.get(position);
        UserInfo userInfo = bean.getUser();
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), holder.head);
        holder.name.setText(userInfo.getUalias() + " 问：");
        holder.contents.setText(bean.getContnt());
        String dateStr = DateUtil.ConverToString(bean.getPubstm(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        holder.date.setText(dateStr);
        Double amount  = bean.getAmount();
        if(amount!=null) {
            holder.reward_money.setText("￥ " + bean.getAmount() + " 金币");
        }
        holder.review_people_num.setText(bean.getReplyCount() + "个人回答");

        if (bean.getAdoptcount() > 0) {
            holder.status.setVisibility(View.VISIBLE);
        } else {
            holder.status.setVisibility(View.GONE);
        }

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
            idList.add(this.list.get(checkedItemList.get(i)).getRewdid());
        }
        return idList;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//    }

    class ViewHolder {
        SelectableRoundedImageView head;
        TextView name;
        TextView contents;
        TextView date;
        TextView status;
        TextView reward_money;
        TextView review_people_num;
    }

}