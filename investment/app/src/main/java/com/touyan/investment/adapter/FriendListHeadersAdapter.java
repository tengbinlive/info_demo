package com.touyan.investment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.core.util.StringMatcher;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.R;
import com.touyan.investment.bean.user.Subscriber;
import com.touyan.investment.bean.user.UserInfo;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import java.util.ArrayList;

public class FriendListHeadersAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {

    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private LayoutInflater mInflater;

    private ArrayList<Subscriber> list;

    private Context mContext;

    private int keyIndex;

    public FriendListHeadersAdapter(Context context, ArrayList<Subscriber> _list) {
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getHeaderView(final int position, View convertView, final ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_friend_header, parent, false);
            holder = new HeaderViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        Subscriber bean = list.get(position);
        holder.text.setText(bean.getNameSort());
        return convertView;
    }

    @Override
    public long getHeaderId(final int position) {
        Subscriber bean = list.get(position);
        keyIndex = mSections.indexOf(bean.getNameSort());
        if (keyIndex < 0) {
            keyIndex = 0;
        }
        return keyIndex;
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public String getItemInitials(int position) {
        return list.get(position).getNameSort();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_friend, viewGroup, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.head = (SelectableRoundedImageView) convertView.findViewById(R.id.head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Subscriber subscriber = list.get(i);
        UserInfo userInfo = subscriber.getUser();
        holder.name.setText(userInfo.getUalias());
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), holder.head);
        return convertView;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(getItemInitials(j).charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(getItemInitials(j).charAt(0)), String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

    class ViewHolder {
        SelectableRoundedImageView head;
        TextView name;
    }

    class HeaderViewHolder {
        TextView text;
    }
}