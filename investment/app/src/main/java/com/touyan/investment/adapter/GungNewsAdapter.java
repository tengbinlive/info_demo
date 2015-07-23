package com.touyan.investment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.easemob.chat.EMMessage;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.R;

import java.util.List;

public class GungNewsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private List<EMMessage> list;

    public GungNewsAdapter(Context context, List<EMMessage> _list) {
        this.list = _list;
        mInflater = LayoutInflater.from(context);
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

    public void refresh(List<EMMessage> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_recommend_news, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), holder.head);
//        holder.head
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_recommend_news.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class ViewHolder {
        @Bind(R.id.head)
        SelectableRoundedImageView head;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.value)
        TextView value;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}