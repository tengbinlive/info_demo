package com.touyan.investment.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.core.util.CommonUtil;
import com.core.util.DateUtil;
import com.core.util.StringUtil;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.R;
import com.touyan.investment.bean.message.InviteMessage;

import java.util.ArrayList;

public class NoticeAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private ArrayList<InviteMessage> list;

    private Context mContext;

    private int blackColos;

    private final static int AGREED = 0x01;
    private final static int REFUSE = 0x02;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            InviteMessage message;
            switch (what) {
                case AGREED:
                     message = list.get(msg.arg1);
                    try {
                        CommonUtil.showToast("已同意");
                        EMChatManager.getInstance().acceptInvitation(message.getFrom());
                    } catch (EaseMobException e) {
                        e.printStackTrace();
                    }
                    break;
                case REFUSE:
                     message = list.get(msg.arg1);
                    try {
                        CommonUtil.showToast("已拒绝");
                        EMChatManager.getInstance().refuseInvitation(message.getFrom());
                    } catch (EaseMobException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public NoticeAdapter(Context context, ArrayList<InviteMessage> _list) {
        this.list = _list;
        mContext = context;
        blackColos = mContext.getResources().getColor(R.color.black);
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

    public void refresh(ArrayList<InviteMessage> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_notice, parent, false);
            holder = new ViewHolder(convertView);
            holder.statusNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    Message mg = new Message();
                    mg.what = AGREED;
                    mg.arg1 = position;
                    activityHandler.sendMessage(mg);
                }
            });

            holder.statusYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    Message mg = new Message();
                    mg.what = REFUSE;
                    mg.arg1 = position;
                    activityHandler.sendMessage(mg);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InviteMessage message = list.get(position);
        ImageLoader.getInstance().displayImage(message.getHeadphoto(), holder.head);
        String groupName = message.getGroupName();
        String name = StringUtil.isNotBlank(groupName) ? groupName : message.getFrom();
        holder.name.setText(name);
        String dateStr = DateUtil.getDateString(message.getTime(), DateUtil.MM_DD_HH_MM);
        holder.date.setText(dateStr);
        holder.value.setText(message.getReason());
        InviteMessage.InviteMesageStatus status = message.getStatus();
        if (status == InviteMessage.InviteMesageStatus.BEINVITEED) {
            setStatusButton(holder, "接收", "拒绝", false);
        } else if (status == InviteMessage.InviteMesageStatus.BEAGREED) {
            setStatusButton(holder, "对方同意", null, true);
        } else if (status == InviteMessage.InviteMesageStatus.BEREFUSED) {
            setStatusButton(holder, "被拒绝", null, true);
        } else if (status == InviteMessage.InviteMesageStatus.AGREED) {
            setStatusButton(holder, "已同意", null, true);
        } else if (status == InviteMessage.InviteMesageStatus.REFUSED) {
            setStatusButton(holder, "已拒绝", null, true);
        } else if (status == InviteMessage.InviteMesageStatus.BEAPPLYED) {
            setStatusButton(holder, "同意", "拒绝", false);
        }else if (status == InviteMessage.InviteMesageStatus.OTHER) {
            setStatusButton(holder, "", "", false);
        }

        holder.statusNo.setTag(position);
        holder.statusYes.setTag(position);
        return convertView;
    }

    /**
     * 设置状态按钮
     *
     * @param holder
     * @param yesStr
     * @param noStr
     * @param isCarry true表示完成状态，false表示按钮状态
     */
    private void setStatusButton(ViewHolder holder, String yesStr, String noStr, boolean isCarry) {
        if (StringUtil.isNotBlank(yesStr)) {
            holder.statusYes.setVisibility(View.VISIBLE);
            holder.statusNo.setText(yesStr);
        } else {
            holder.statusYes.setVisibility(View.GONE);
        }
        if (StringUtil.isNotBlank(noStr)) {
            holder.statusNo.setVisibility(View.VISIBLE);
            holder.statusNo.setText(noStr);
        } else {
            holder.statusNo.setVisibility(View.GONE);
        }
        if (!isCarry) {
            holder.statusYes.setBackgroundResource(R.drawable.bg_round_red);
            holder.statusYes.setVisibility(View.VISIBLE);
        } else {
            holder.statusYes.setVisibility(View.GONE);
            holder.statusYes.setTextColor(blackColos);
            holder.statusYes.setBackground(null);
        }
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_notice.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.head)
        SelectableRoundedImageView head;
        @Bind(R.id.status_yes)
        TextView statusYes;
        @Bind(R.id.status_no)
        TextView statusNo;
        @Bind(R.id.status_layout)
        LinearLayout statusLayout;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.value)
        TextView value;
        @Bind(R.id.date)
        TextView date;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}