package com.touyan.investment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.core.util.CommonUtil;
import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.R;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class MayKnowAdapter extends BaseAdapter {

    private static final int LOAD_SIGN = 0x01;//报名

    private InvestmentManager manager = new InvestmentManager();

    private LayoutInflater mInflater;

    private ArrayList<UserInfo> list;

    private Context mContext;

    public MayKnowAdapter(Context context, ArrayList<UserInfo> _list) {
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

    public void refresh(ArrayList<UserInfo> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_mayknow, parent, false);
            holder = new ViewHolder();
            holder.head = (SelectableRoundedImageView) convertView.findViewById(R.id.head);
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.add_tv = (TextView) convertView.findViewById(R.id.add_tv);
            holder.add_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        EMContactManager.getInstance().addContact(list.get(position).getServno(), "");
                        CommonUtil.showToast("添加好友请求已发送");
                    } catch (EaseMobException e) {

                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getUphoto(), holder.head);
        holder.name_tv.setText(list.get(position).getUalias());
        return convertView;
    }

    class ViewHolder {
        SelectableRoundedImageView head;
        TextView name_tv;
        TextView add_tv;
    }

}