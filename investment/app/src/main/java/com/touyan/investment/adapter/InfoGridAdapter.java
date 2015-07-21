package com.touyan.investment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.App;
import com.touyan.investment.R;

public class InfoGridAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private String[] list;

    public InfoGridAdapter(Context context, String[] _list) {
        this.list = _list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void refresh(String[] _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_inv_info_image, null);
            holder = new ViewHolder();
            holder.imageview = (SelectableRoundedImageView) convertView.findViewById(R.id.imageview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list[position], holder.imageview, App.getInstance().getOptionsImage());
        return convertView;
    }

    class ViewHolder {
        SelectableRoundedImageView imageview;
    }

}