package com.touyan.investment.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.core.util.StringMatcher;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.activity.UserFansDetailsActivity;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.UserManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/27.
 */
public class AddFriendListHeadersAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {
    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private LayoutInflater mInflater;

    private ArrayList<UserInfo> list;

    private Activity mContext;

    private int keyIndex;


    private UserManager manager = new UserManager();


    public AddFriendListHeadersAdapter(Activity context, ArrayList<UserInfo> _list) {
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_add_friend, viewGroup, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.head = (SelectableRoundedImageView) convertView.findViewById(R.id.head);
            holder.add = (TextView) convertView.findViewById(R.id.add);
            holder.head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    UserFansDetailsActivity.toOthersDetail(mContext, App.getInstance().getgUserInfo().getServno(), list.get(position).getServno());
                }
            });
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserInfo subscriber = list.get(position);
        holder.add.setTag(position);
        holder.name.setText(subscriber.getUalias());
        holder.head.setTag(position);
        ImageLoader.getInstance().displayImage(subscriber.getUphoto(), holder.head);
        return convertView;
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
        UserInfo bean = list.get(position);
        holder.text.setText(bean.getNameSort());
        return convertView;
    }

    @Override
    public long getHeaderId(final int position) {
        UserInfo bean = list.get(position);
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

    public void refresh(ArrayList<UserInfo> _list) {
        list = _list;
        notifyDataSetChanged();
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
        TextView add;
    }

    class HeaderViewHolder {
        TextView text;
    }
}
