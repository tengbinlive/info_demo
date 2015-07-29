
package com.touyan.investment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.core.util.DateUtil;
import com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem.ExpandableListItemAdapter;
import com.touyan.investment.R;
import com.touyan.investment.bean.message.TopMessageList;
import com.touyan.investment.bean.message.TopMessages;

import java.util.ArrayList;

public class MyExpandableListItemAdapter extends ExpandableListItemAdapter<Integer> {

    private LayoutInflater mInflater;
    private final Context mContext;
    private ArrayList<TopMessageList> mlist;

    public MyExpandableListItemAdapter(final Context context, ArrayList<TopMessageList> list) {
        super(context, R.layout.activity_expandablelistitem_card, R.id.activity_expandablelistitem_card_title, R.id.activity_expandablelistitem_card_content);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.mlist = list;

        if (null != mlist) {
            for (int i = 0; i < mlist.size(); i++) {
                add(i);
            }
        }
    }

    @NonNull
    @Override
    public View getTitleView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        TextView tv = (TextView) convertView;
        if (tv == null) {
            tv = new TextView(mContext);
            tv.setTextColor(mContext.getResources().getColor(R.color.textcolor_222));
        }
        tv.setText(mlist.get(position).getGroupDetal().getGroupname());
        return tv;
    }

    @NonNull
    @Override
    public View getContentView(final int position, View convertView, @NonNull final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_top, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TopMessageList topMessage = mlist.get(position);
        ArrayList<TopMessages> arrayList = topMessage.getTopMessages();
        holder.mainlayout.removeAllViews();
        for (TopMessages topMessages : arrayList) {
            LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.item_top_message, holder.mainlayout, false);
            ((TextView) (layout.findViewById(R.id.contents))).setText(topMessages.getMtitle());
            String dateStr = DateUtil.ConverToString(topMessages.getToptim(), DateUtil.YYYY_MM_DD_HH_MM_SS);
            ((TextView) (layout.findViewById(R.id.date))).setText(dateStr);
            holder.mainlayout.addView(layout);
        }
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_top.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.mainlayout)
        LinearLayout mainlayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}