package com.touyan.investment.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.StringMatcher;
import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.activity.UserFansDetailsActivity;
import com.touyan.investment.bean.message.ContactFriend;
import com.touyan.investment.manager.MessageManager;
import com.touyan.investment.manager.UserManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/28.
 */
public class ContactFriendListHeadersAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {
    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final static int LOAD_DATA = 0;

    private LayoutInflater mInflater;

    private ArrayList<ContactFriend> list;

    private Activity mContext;

    private int keyIndex;


    private MessageManager manager = new MessageManager();

    /**
     * 这里包含了调用数据请求的相关接口之后, 应该执行的回调方法, 根据msg.what来判断.
     */
    @SuppressLint("HandlerLeak")
    private Handler activityHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                // 返回数据处理
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj);
                    break;

            }
        }
    };

    /**
     * @param resposne
     */
    private void loadData(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            CommonUtil.showToast(mContext, "邀请已发送");
        } else {
            CommonUtil.showToast(mContext, resposne.getErrorTip());
        }
    }

    public ContactFriendListHeadersAdapter(Activity context, ArrayList<ContactFriend> _list) {
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
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
                    switch (list.get(position).getRole()) {
                        case 0:

                            break;
                        case 1:
                            try {
                                EMContactManager.getInstance().addContact(list.get(position).getServno(), "");
                                CommonUtil.showToast("添加好友请求已发送");
                            } catch (EaseMobException e) {

                            }
                            break;
                        case 2:
                            manager.inviteContactFriends(mContext, list.get(position).getServno(), activityHandler, LOAD_DATA);
                            break;
                    }

                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ContactFriend subscriber = list.get(position);
        switch (subscriber.getRole()) {
            case 0:
                holder.add.setText("已添加");
                holder.add.setTextColor(mContext.getResources().getColor(R.color.textcolor_888));
                holder.add.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                break;
            case 1:
                holder.add.setText("添加");
                holder.add.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.add.setBackgroundResource(R.drawable.bg_round_red);

                break;
            case 2:
                holder.add.setText("邀请");
                holder.add.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.add.setBackgroundResource(R.drawable.bg_round_red);
                break;
        }

        holder.name.setText(subscriber.getUserinfo().getUalias());
        ImageLoader.getInstance().displayImage(subscriber.getUserinfo().getUphoto(), holder.head);
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
        ContactFriend bean = list.get(position);
        holder.text.setText(bean.getUserinfo().getNameSort());
        return convertView;
    }

    @Override
    public long getHeaderId(final int position) {
        ContactFriend bean = list.get(position);
        keyIndex = mSections.indexOf(bean.getUserinfo().getNameSort());
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
        return list.get(position).getUserinfo().getNameSort();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void refresh(ArrayList<ContactFriend> _list) {
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
