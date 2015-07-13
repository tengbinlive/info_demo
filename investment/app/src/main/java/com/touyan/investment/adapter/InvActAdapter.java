package com.touyan.investment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.InvInfoResult;

import java.util.ArrayList;

public class InvActAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private ArrayList<InvInfoResult> list;

    private Context mContext;


    public InvActAdapter(Context context, ArrayList<InvInfoResult> _list) {
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

    public void refresh(ArrayList<InvInfoResult> _list) {
        list = _list;
        notifyDataSetChanged();
    }

//    private void updateView(int itemIndex){
//        intvisiblePosition = yourListView.getFirstVisiblePosition();
//        Viewv = yourListView.getChildAt(itemIndex - visiblePosition);
//        ViewHolder viewHolder =(ViewHolder)v.getTag();
//        if(viewHolder!= null){
//        }
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_inv_act, null);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    class ViewHolder {
    }

}