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
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.DateUtil;
import com.core.util.StringUtil;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.R;
import com.touyan.investment.bean.message.InviteMessage;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class NoticeAdapter extends BaseAdapter {

    private static final int LOAD = 0x01;

    private InvestmentManager manager = new InvestmentManager();

    private LayoutInflater mInflater;

    private ArrayList<InviteMessage> list;

    private Context mContext;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD:
                    loadData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            notifyDataSetChanged();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    public NoticeAdapter(Context context, ArrayList<InviteMessage> _list) {
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

    public void refresh(ArrayList<InviteMessage> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_notice, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InviteMessage message = list.get(position);
        ImageLoader.getInstance().displayImage(message.getHeadphoto(), holder.head);
        String groupName = message.getGroupName();
        String name = StringUtil.isNotBlank(groupName) ? groupName : message.getFrom();
        holder.name.setText(name);
        String dateStr = DateUtil.getDateString(message.getTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        holder.date.setText(dateStr);
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
        }
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
        holder.statusYes.setText(yesStr);
        if (StringUtil.isNotBlank(noStr)) {
            holder.statusNo.setVisibility(View.VISIBLE);
            holder.statusNo.setText(noStr);
        } else {
            holder.statusNo.setVisibility(View.GONE);
        }
        if (!isCarry) {
            holder.statusYes.setBackgroundResource(R.drawable.bg_round_red);
            holder.statusYes.setEnabled(true);
        } else {
            holder.statusYes.setEnabled(false);
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
        @Bind(R.id.date)
        TextView date;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}