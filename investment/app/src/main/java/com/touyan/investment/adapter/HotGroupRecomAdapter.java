package com.touyan.investment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.R;
import com.touyan.investment.bean.message.GroupDetail;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class HotGroupRecomAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private ArrayList<GroupDetail> list;

    private Context mContext;

    public HotGroupRecomAdapter(Context context, ArrayList<GroupDetail> _list) {
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

    public void refresh(ArrayList<GroupDetail> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_hotgroup, parent, false);
            holder = new ViewHolder();
            holder.head = (SelectableRoundedImageView) convertView.findViewById(R.id.head);
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.group_member = (TextView) convertView.findViewById(R.id.group_member);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getGphoto(), holder.head);
        holder.name_tv.setText(list.get(position).getGroupname());
        holder.group_member.setText(""+list.get(position).getMemnum());
        return convertView;
    }

    class ViewHolder {
        SelectableRoundedImageView head;
        TextView name_tv;
        TextView group_member;
    }

}