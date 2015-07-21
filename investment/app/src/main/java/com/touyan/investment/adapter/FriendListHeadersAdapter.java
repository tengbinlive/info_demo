package com.touyan.investment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.R;
import com.touyan.investment.bean.user.Subscriber;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.UserManager;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import java.util.ArrayList;

public class FriendListHeadersAdapter extends BaseSwipeAdapter implements StickyListHeadersAdapter, SectionIndexer {

    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final static int DELETE_SERVER = 0;

    private final static int DELETE_LOCAL = 1;

    private LayoutInflater mInflater;

    private ArrayList<Subscriber> list;

    private Context mContext;

    private int keyIndex;

    private ArrayList<Integer> deleteArray = new ArrayList<>();

    private UserManager manager = new UserManager();

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
                case DELETE_SERVER:
                    loadedDelete((CommonResponse) msg.obj);
                    break;
                case DELETE_LOCAL:
                    int index = 0;
                    for (int position : deleteArray) {
                        deleteData(position);
                        deleteArray.remove(index);
                        index++;
                    }
                    notifyDataSetChanged();
                    break;
            }
        }
    };

    /**
     * @param resposne
     */
    private void loadedDelete(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            CommonUtil.showToast(mContext, "取消成功");
        } else {
            CommonUtil.showToast(mContext, resposne.getErrorTip());
        }
    }

    private void deleteData(int index) {
        int size = list.size();
        if (index < size && index >= 0) {
            Subscriber subscriber = list.get(index);
            list.remove(index);
            manager.cancelUserFollow(mContext, subscriber.getScrino(), activityHandler, DELETE_SERVER);
        }
    }

    public FriendListHeadersAdapter(Context context, ArrayList<Subscriber> _list) {
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
        holder.deleteLayout = (RelativeLayout) convertView.findViewById(R.id.delete_layout);
        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAllItems();
                int index = (Integer) view.getTag();
                deleteArray.add(index);
                activityHandler.sendEmptyMessageDelayed(DELETE_LOCAL, 300);
            }
        });
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public void fillValues(int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Subscriber subscriber = list.get(position);
        holder.deleteLayout.setTag(position);
        UserInfo userInfo = subscriber.getUser();
        holder.name.setText(userInfo.getUalias());
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), holder.head);
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

    public void refresh(ArrayList<Subscriber> _list) {
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