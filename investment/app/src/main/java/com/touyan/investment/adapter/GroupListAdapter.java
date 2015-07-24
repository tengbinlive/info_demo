package com.touyan.investment.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringMatcher;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.activity.UserFansDetailsActivity;
import com.touyan.investment.bean.user.Subscriber;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.UserManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> listTag = null;
    private List<String> objects;
    public GroupListAdapter(LayoutInflater mInflater, List<String> objects, List<String> tags) {
        //super(mInflater.getContext(), 0, objects);
        this.listTag = tags;
        this.objects = objects;
        this.mInflater = mInflater;
    }

    @Override
    public boolean isEnabled(int position) {
        if(listTag.contains(getItem(position))){
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public int getCount() {
        return null == objects ? 0 : objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(listTag.contains(getItem(position))){
            view = mInflater.inflate(R.layout.item_gung_group_tag, null);
            TextView textView = (TextView) view.findViewById(R.id.group_list_item_text);
            textView.setText(objects.get(position));
        }else{
            view = mInflater.inflate(R.layout.item_gung_group, null);
            TextView textView = (TextView) view.findViewById(R.id.group_name);
            textView.setText(objects.get(position));
        }
        return view;
    }
}