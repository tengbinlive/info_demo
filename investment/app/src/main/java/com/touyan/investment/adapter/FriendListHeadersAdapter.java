package com.touyan.investment.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
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
 * Created by Administrator on 2015/7/23.
 */
public class FriendListHeadersAdapter extends BaseSwipeAdapter implements StickyListHeadersAdapter, SectionIndexer {
    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    private LayoutInflater mInflater;

    private ArrayList<UserInfo> list;

    private Activity mContext;

    private int keyIndex;

    private ArrayList<Integer> deleteArray = new ArrayList<>();

    private UserManager manager = new UserManager();


    private void deleteData(int index) {
        int size = list.size();
        if (index < size && index >= 0) {
            UserInfo subscriber = list.get(index);
            list.remove(index);
        }
    }

    public FriendListHeadersAdapter(Activity context, ArrayList<UserInfo> _list) {
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View convertView;
        convertView = mInflater.inflate(R.layout.item_friend, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.head = (SelectableRoundedImageView) convertView.findViewById(R.id.head);
        holder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                UserFansDetailsActivity.toOthersDetail(mContext, App.getInstance().getgUserInfo().getServno(), list.get(position).getServno());
            }
        });
        holder.deleteLayout = (RelativeLayout) convertView.findViewById(R.id.delete_layout);
        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAllItems();
                int index = (Integer) view.getTag();
                deleteArray.add(index);
                try {
                    EMContactManager.getInstance().deleteContact(list.get(index).getServno());

                } catch (EaseMobException e) {
                    e.printStackTrace();
                }
                ((AbsActivity) mContext).dialogShow();
            }
        });
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public void fillValues(int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        UserInfo subscriber = list.get(position);
        holder.deleteLayout.setTag(position);

        holder.name.setText(subscriber.getUalias());
        holder.head.setTag(position);
        ImageLoader.getInstance().displayImage(subscriber.getUphoto(), holder.head);
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
        RelativeLayout deleteLayout;
    }

    class HeaderViewHolder {
        TextView text;
    }
}
