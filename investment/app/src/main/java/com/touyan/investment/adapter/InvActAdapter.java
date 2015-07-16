package com.touyan.investment.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.InvActBean;
import com.touyan.investment.fragment.InvActFragment;
import com.touyan.investment.helper.Util;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class InvActAdapter extends BaseAdapter {

    private static final int LOAD_SIGN = 0x01;//报名

    private InvestmentManager manager = new InvestmentManager();

    private LayoutInflater mInflater;

    private ArrayList<InvActBean> list;

    private Context mContext;

    private int currentIndex;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_SIGN:
                    loadDataSign((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadDataSign(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            list.get(currentIndex).setIsJoin(InvActBean.STATUS_BY);
            notifyDataSetChanged();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    public InvActAdapter(Context context, ArrayList<InvActBean> _list) {
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

    public void refresh(ArrayList<InvActBean> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_inv_act, null);
            holder = new ViewHolder();
            holder.head = (ImageView) convertView.findViewById(R.id.head);
            holder.contents_tv = (TextView) convertView.findViewById(R.id.contents_tv);
            holder.sign_tv = (TextView) convertView.findViewById(R.id.sign_tv);
            holder.sign_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentIndex = (Integer)view.getTag();
                    getSign(currentIndex);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InvActBean bean = list.get(position);

        setSignStatus(holder.sign_tv, bean.getIsJoin());

        String type = bean.getActvtp();

        setImageIcon(holder.head, type);

        holder.sign_tv.setTag(position);

        setContent(holder.contents_tv, type, bean.getAtitle());

        return convertView;
    }

    private void setSignStatus(TextView view, String status) {
        if (InvActBean.STATUS_AUDIT.equals(status)) {
            view.setText(R.string.audit);
            Util.setTextViewDrawaleAnchor(mContext, view, R.drawable.act_unsign, Util.TOP);
        } else if (InvActBean.STATUS_NOT_PARTICIPATE.equals(status)) {
            view.setText(R.string.not_participate);
            Util.setTextViewDrawaleAnchor(mContext, view, R.drawable.act_unsign, Util.TOP);
        } else if (InvActBean.STATUS_NO_BY.equals(status)) {
            view.setText(R.string.no_by);
            Util.setTextViewDrawaleAnchor(mContext, view, R.drawable.act_unsign, Util.TOP);
        } else if (InvActBean.STATUS_BY.equals(status)) {
            Util.setTextViewDrawaleAnchor(mContext, view, R.drawable.act_sign, Util.TOP);
            view.setText(R.string.by);
        }
    }

    private void getSign(int index) {
        InvActBean bean = list.get(index);
        manager.actSign(mContext, bean.getActvid(), "" + bean.getCharge(), activityHandler, LOAD_SIGN);
    }

    private void setImageIcon(ImageView head, String type) {
        if (InvActBean.TYPE_ROADSHOW.equals(type)) {
            head.setImageResource(R.drawable.act_roadshow);
        } else {
            head.setImageResource(R.drawable.act_product);
        }
    }

    private void setContent(TextView view, String type, String content) {
        view.setText(content);
        if (InvActBean.TYPE_ROADSHOW.equals(type)) {
            Util.setTextViewDrawaleAnchor(mContext, view, R.drawable.act_location, Util.LEFT);
        } else {
            Util.setTextViewDrawaleAnchor(mContext, view, 0, 0);
        }
    }

    private boolean isSignCilc(String status) {
        if (InvActBean.STATUS_NOT_PARTICIPATE.equals(status)) return true;
        return false;
    }

    class ViewHolder {
        ImageView head;
        TextView contents_tv;
        TextView sign_tv;
    }

}