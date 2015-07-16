package com.touyan.investment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.touyan.investment.R;
import com.touyan.investment.activity.InfoRewardActivity;

public class MoneyAdapter extends BaseAdapter implements View.OnClickListener {

    private LayoutInflater mInflater;

    private String[] list;

    private InfoRewardActivity mContext;

    private int select  = 0 ;

    private int blackColos;

    private int whiteColos;

    public MoneyAdapter(InfoRewardActivity context, String[] _list) {
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        blackColos = context.getResources().getColor(R.color.textcolor_222);
        whiteColos = context.getResources().getColor(R.color.white);
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_inv_info_reward_money, null);
            holder = new ViewHolder();
            holder.money_bt = (Button) convertView.findViewById(R.id.money_bt);
            holder.money_bt.setOnClickListener(this);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(select == position){
            holder.money_bt.setSelected(true);
            holder.money_bt.setTextColor(whiteColos);
        }else{
            holder.money_bt.setSelected(false);
            holder.money_bt.setTextColor(blackColos);
        }
        holder.money_bt.setTag(R.id.item_position, position);
        holder.money_bt.setText(list[position]);
        return convertView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.money_bt) {
            int position = (Integer) view.getTag(R.id.item_position);
            select = position;
            mContext.setGridViewSelect(position);
            view.setSelected(true);
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        Button money_bt;
    }


}