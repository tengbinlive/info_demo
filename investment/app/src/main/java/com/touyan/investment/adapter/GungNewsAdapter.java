package com.touyan.investment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.R;
import com.touyan.investment.bean.message.ConversationBean;
import com.touyan.investment.bean.message.GroupDetal;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.hx.HXDateUtils;
import com.touyan.investment.hx.HXUtil;
import com.touyan.investment.hx.HXSmileUtils;

import java.util.ArrayList;
import java.util.Date;

public class GungNewsAdapter extends BaseSwipeAdapter {

    private LayoutInflater mInflater;

    private ArrayList<ConversationBean> list;
    private Context context;

    public GungNewsAdapter(Context context, ArrayList<ConversationBean> _list) {
        this.list = _list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
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

    public void refresh(ArrayList<ConversationBean> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View convertView = mInflater.inflate(R.layout.item_recommend_news, null);
        ViewHolder holder = new ViewHolder(convertView);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public void fillValues(int position, View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ConversationBean bean = list.get(position);
        EMConversation conversation = bean.getConversation();
        EMMessage lastMessage = conversation.getLastMessage();
        Object object = bean.getObject();
        holder.value.setText(HXSmileUtils.getSmiledText(context, HXUtil.getMessageDigest(lastMessage, context)),
                TextView.BufferType.SPANNABLE);
        holder.date.setText(HXDateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
        if (null != object) {
            if (conversation.isGroup()) {
                GroupDetal groupDetal = (GroupDetal) object;
                holder.group_subscript.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(groupDetal.getGphoto(), holder.head);
                holder.name.setText(groupDetal.getGroupname());
            } else {
                UserInfo userInfo = (UserInfo) object;
                holder.group_subscript.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage(userInfo.getUphoto(), holder.head);
                holder.name.setText(userInfo.getUalias());
            }
        }
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_recommend_news.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class ViewHolder {
        @Bind(R.id.head)
        SelectableRoundedImageView head;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.value)
        TextView value;
        @Bind(R.id.group_subscript)
        ImageView group_subscript;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}