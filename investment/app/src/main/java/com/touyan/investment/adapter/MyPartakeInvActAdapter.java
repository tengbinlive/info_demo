package com.touyan.investment.adapter;

import android.app.Activity;
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
import com.touyan.investment.App;
import com.touyan.investment.R;
import com.touyan.investment.activity.MeActActivity;
import com.touyan.investment.bean.main.InvActBean;
import com.touyan.investment.bean.main.MyPartakeInvActBean;
import com.touyan.investment.helper.Util;
import com.touyan.investment.manager.InvestmentManager;

import java.util.ArrayList;

public class MyPartakeInvActAdapter extends EditerAdapter {

    private static final int LOAD_SIGN = 0x01;//报名

    private InvestmentManager manager = new InvestmentManager();

    private LayoutInflater mInflater;

    private ArrayList<MyPartakeInvActBean> list;

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
            list.get(currentIndex).getActivity().setIsJoin(MyPartakeInvActBean.STATUS_BY);
            notifyDataSetChanged();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    public MyPartakeInvActAdapter(Context context, ArrayList<MyPartakeInvActBean> _list) {

        super((Activity)context);
        this.list = _list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        setCheckBoexListener(new OnCheckBoexListener() {
            @Override
            public void OnCheckBoexListener(View view, int index) {
                if (getCurrentState() != EditerAdapter.STATE_EDIT) {
                    if (checkedItemList.size() > 0) {
                        setCurrentState(EditerAdapter.STATE_REMOVE);
                        ((MeActActivity) mContext).changeEditState(EditerAdapter.STATE_REMOVE);
                        notifyDataSetChanged();
                    } else {
                        setCurrentState(EditerAdapter.STATE_COMPLETE);
                        ((MeActActivity) mContext).changeEditState(EditerAdapter.STATE_COMPLETE);
                        notifyDataSetChanged();
                    }
                }
            }
        });
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

    public void refresh(ArrayList<MyPartakeInvActBean> _list) {
        list = _list;
        notifyDataSetChanged();
    }

    @Override
    public View getChildView(int position, View convertView, ViewGroup parent) {
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
                    currentIndex = (Integer) view.getTag();
                    getSign(currentIndex);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyPartakeInvActBean bean = list.get(position);

        if(bean.getActivity().getPubsid().equals(App.getInstance().getgUserInfo().getServno())) {
            holder.sign_tv.setVisibility(View.GONE);
            setSignStatus(holder.sign_tv, bean.getActivity().getIsJoin());
        }else{
            holder.sign_tv.setVisibility(View.VISIBLE);
            setSignStatus(holder.sign_tv, bean.getActivity().getIsJoin());
        }

        String type = bean.getActivity().getActvtp();

        setImageIcon(holder.head, type);

        holder.sign_tv.setTag(position);

        setContent(holder.contents_tv, type, bean.getActivity().getByloct() , bean.getActivity().getAtitle());

        return convertView;
    }

    @Override
    public int getCheckBoxLayout() {
        return R.id.checkbox_layout;
    }

    @Override
    public ArrayList<String> getIdList() {
        ArrayList<String> idList = new ArrayList<String>();
        for (int i = 0; i < checkedItemList.size(); i++) {
            idList.add(this.list.get(checkedItemList.get(i)).getActvid());
        }
        return idList;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//    }

    private void setSignStatus(TextView view, String status) {
        if (InvActBean.STATUS_NOT_PARTICIPATE.equals(status)||InvActBean.STATUS_NO_BY.equals(status)) {
            view.setText(R.string.not_participate);
            Util.setTextViewDrawaleAnchor(mContext, view, R.drawable.act_unsign, Util.TOP);
        } else {
            Util.setTextViewDrawaleAnchor(mContext, view, R.drawable.act_sign, Util.TOP);
            view.setText(R.string.by);
        }
    }

    private void getSign(int index) {
        MyPartakeInvActBean bean = list.get(index);
        String status = bean.getActivity().getIsJoin();
        if(InvActBean.STATUS_AUDIT.equals(status)){
            CommonUtil.showToast(R.string.by);
            return;
        }
        manager.actSign(mContext, bean.getActvid(), "" + bean.getActivity().getCharge(), activityHandler, LOAD_SIGN);
    }

    private void setImageIcon(ImageView head, String type) {
        if (InvActBean.TYPE_ROADSHOW.equals(type)) {
            head.setImageResource(R.drawable.act_roadshow);
        } else {
            head.setImageResource(R.drawable.act_product);
        }
    }

    private void setContent(TextView view, String type, String loact,String content) {
        if (InvActBean.TYPE_ROADSHOW.equals(type)) {
            view.setText(loact+ " " +content);
            Util.setTextViewDrawaleAnchor(mContext, view, R.drawable.act_location, Util.LEFT);
        } else {
            view.setText(content);
            Util.setTextViewDrawaleAnchor(mContext, view, 0, 0);
        }
    }

    class ViewHolder {
        ImageView head;
        TextView contents_tv;
        TextView sign_tv;
    }

}