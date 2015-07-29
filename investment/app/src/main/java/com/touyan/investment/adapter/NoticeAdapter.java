package com.touyan.investment.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.DateUtil;
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
            convertView = mInflater.inflate(R.layout.item_add_friend, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        InviteMessage message = list.get(position);
//        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), holder.head);
//        holder.name.setText(userInfo.getUalias());
//        String dateStr = DateUtil.ConverToString(infoBean.getPubstm(), DateUtil.YYYY_MM_DD_HH_MM_SS);


        return convertView;
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_add_friend.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */

    class ViewHolder {
        @Bind(R.id.head)
        SelectableRoundedImageView head;
        @Bind(R.id.add)
        TextView add;
        @Bind(R.id.name)
        TextView name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}