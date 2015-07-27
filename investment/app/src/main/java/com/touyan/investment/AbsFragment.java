package com.touyan.investment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.core.util.StringUtil;
import com.gitonway.lee.niftymodaldialogeffects.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.NiftyDialogBuilder;
import com.touyan.investment.event.AnyEventType;
import de.greenrobot.event.EventBus;

public abstract class AbsFragment extends Fragment {

    protected final String TAG = AbsFragment.class.getSimpleName();

    public static final int RECODE_RELEASE = 4;//发布资讯返回代码

    public final static String KEY = "KEY";

    public NiftyDialogBuilder dialogBuilder;

    public abstract boolean onBackPressed();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public void onEvent(AnyEventType event) {
        //接收消息
    }

    @Override
    public void onDestroy() {
        dialogDismiss();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void dialogShow() {
        dialogDismiss();
        dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
        dialogBuilder.withDuration(700) // def
                .isCancelableOnTouchOutside(false) // def | isCancelable(true)
                .withEffect(Effectstype.Fadein) // def Effectstype.Slidetop
                .setCustomView(R.layout.loading_view, getActivity()); // .setCustomView(View
        activityHandler.sendEmptyMessage(DIALOGSHOW);

    }

    public void dialogShow(String title) {
        dialogDismiss();
        LinearLayout convertView = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.loading_view, null);
        TextView dialog_confirm_content = (TextView) convertView.findViewById(R.id.dialog_confirm_content);
        dialog_confirm_content.setText(title);
        dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());
        dialogBuilder.withDuration(700) // def
                .isCancelableOnTouchOutside(false) // def | isCancelable(true)
                .withEffect(Effectstype.Fadein) // def Effectstype.Slidetop
                .setCustomView(convertView, getActivity()); // .setCustomView(View
        activityHandler.sendEmptyMessage(DIALOGSHOW);

    }

    public void dialogDismiss() {
        if (null != dialogBuilder && dialogBuilder.isShowing()) {
            activityHandler.sendEmptyMessage(DIALOGDISMISS);
        }
    }

    public void showConfirmDialog(Activity activity, String content, String leftText, View.OnClickListener leftEvent, String rightText, View.OnClickListener rightEvent) {
        dialogDismiss();
        LinearLayout linearLayout = new LinearLayout(activity);
        activity.getLayoutInflater().inflate(R.layout.dialog_confirm, linearLayout);

        TextView contentTv = (TextView) linearLayout.findViewById(R.id.dialog_confirm_content);
        if (StringUtil.isBlank(content)) {
            contentTv.setVisibility(View.GONE);
        } else {
            contentTv.setVisibility(View.VISIBLE);
            contentTv.setText(content);
        }

        TextView closeLeft = (TextView) linearLayout.findViewById(R.id.dialog_confirm_closeLeft);
        if (StringUtil.isBlank(leftText)) {
            closeLeft.setVisibility(View.GONE);
        } else {
            closeLeft.setVisibility(View.VISIBLE);
            closeLeft.setText(leftText);
        }

        TextView closeRight = (TextView) linearLayout.findViewById(R.id.dialog_confirm_closeRight);
        if (StringUtil.isBlank(rightText)) {
            closeRight.setVisibility(View.GONE);
        } else {
            closeRight.setVisibility(View.VISIBLE);
            closeRight.setText(rightText);
        }

        if (StringUtil.isNotBlank(leftText) && StringUtil.isNotBlank(rightText)) {
            linearLayout.findViewById(R.id.dialog_confirm_point).setVisibility(View.VISIBLE);
        } else {
            linearLayout.findViewById(R.id.dialog_confirm_point).setVisibility(View.GONE);
        }

        if (leftEvent != null) {
            closeLeft.setOnClickListener(leftEvent);
        }
        if (rightEvent != null) {
            closeRight.setOnClickListener(rightEvent);
        }
        dialogBuilder = NiftyDialogBuilder.getInstance(activity);
        dialogBuilder.withDuration(700) // def
                .isCancelableOnTouchOutside(false) // def | isCancelable(true)
                .withEffect(Effectstype.Fadein) // def Effectstype.Slidetop
                .setCustomView(linearLayout, activity).show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public abstract void scrollToTop();

    private final  static int DIALOGSHOW = 1;
    private final  static int DIALOGDISMISS = 0;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DIALOGSHOW:
                    dialogBuilder.show();
                    break;
                case DIALOGDISMISS:
                    dialogBuilder.dismiss();
                    break;
                default:
                    break;
            }
        }
    };
}
