package com.touyan.investment.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.DateUtil;
import com.core.util.StringUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.InvActDetailResult;
import com.touyan.investment.bean.main.InvReleaseActParam;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.EditTextWithDelete;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class ActReleaseProductActivity extends AbsActivity implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private InvestmentManager investmentManager = new InvestmentManager();

    private boolean isPublic;

    private EditText release_value_et;

    private TextView date_value_tv;

    private EditTextWithDelete value_ly;

    private static final int LOAD_RELEASE = 0x01;//发布

    private Calendar endDate;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_RELEASE:
                    loadRelease((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadRelease(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            InvActDetailResult releaseResult = (InvActDetailResult) resposne.getData();
            Intent intent = new Intent();
            intent.putExtra(KEY, releaseResult.getDetail());
            setResult(AbsFragment.RECODE_RELEASE, intent);
            CommonUtil.showToast("发布成功");
            finish();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }


    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_act_release_product;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft(0, R.string.cancel);
        setToolbarIntermediateStrID(R.string.release_act_product);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRightStrID(R.string.release);
        setToolbarRightOnClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseInfo();
            }
        });
    }

    private void findView() {
        endDate = Calendar.getInstance();
        SwitchButton sw_bt = (SwitchButton) findViewById(R.id.public_sw_bt);
        sw_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isPublic = b;
            }
        });
        sw_bt.setChecked(true);

        //产品名称
        LinearLayout titleLy = (LinearLayout) findViewById(R.id.title_ly);
        TextView release_title_tv = (TextView) titleLy.findViewById(R.id.release_title_tv);
        release_value_et = (EditText) titleLy.findViewById(R.id.release_value_et);
        release_title_tv.setText(R.string.act_release_product_title);
        release_value_et.setHint(R.string.act_release_product_title_hint);

        //时间
        date_value_tv = (TextView) findViewById(R.id.date_value_tv);
        date_value_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        //详情
        value_ly = (EditTextWithDelete) findViewById(R.id.value_ly);
        value_ly.setHint(R.string.act_release_product_value_hint);

    }

    private void releaseInfo() {
        String titleStr = release_value_et.getText().toString();
        if (StringUtil.isBlank(titleStr)) {
            release_value_et.requestFocus();
            CommonUtil.showToast("标题没有哦~");
            return;
        }

        String dateStr = date_value_tv.getText().toString();
        if (StringUtil.isBlank(dateStr)) {
            showDateDialog();
            value_ly.requestFocus();
            return;
        }

        String contentStr = value_ly.getText().toString();
        if (StringUtil.isBlank(contentStr)) {
            value_ly.requestFocus();
            CommonUtil.showToast("说点什么吧~");
            return;
        }
        dialogShow(R.string.carrying);
        InvReleaseActParam param = new InvReleaseActParam();
        param.setActvtp(InvReleaseActParam.TYPE_ACT);
        param.setIspubl(isPublic ? InvReleaseActParam.PUBLIC_YES : InvReleaseActParam.PUBLIC_NO);
        param.setByloct("");
        param.setAdress("");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String startTimeStr = DateUtil.ConverToString(curDate, DateUtil.YYYY_MM_DD_HH_MM_SS);
        String endTimeStr = DateUtil.ConverToString(endDate.getTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        param.setStartm(startTimeStr);
        param.setEndtim(endTimeStr);
        param.setContnt(contentStr);
        param.setAtitle(titleStr);
        investmentManager.releaseAct(this, param, activityHandler, LOAD_RELEASE);

    }

    private void showDateDialog() {
        Calendar now = Calendar.getInstance();
        Date time = now.getTime();
        long currentTime = time.getTime();
        time.setTime(currentTime + 24 * 60 * 60);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ActReleaseProductActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        now.setTime(time);
        dpd.setMinDate(now);
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    /**
     * 时间选择
     */
    private void showTimeDialog() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                ActReleaseProductActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
//        String minuteString = minute < 10 ? "0"+minute : ""+minute;
//        String time = "You picked the following time: "+hourString+"h"+minuteString;
        endDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        endDate.set(Calendar.MINUTE, minute);
        String dateStr = DateUtil.ConverToString(endDate.getTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        date_value_tv.setText(dateStr);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        String date = "You picked the following date: "+dayOfMonth+"/"+monthOfYear + "/"+year;
        endDate.set(year, monthOfYear, dayOfMonth);
        showTimeDialog();
    }

}
