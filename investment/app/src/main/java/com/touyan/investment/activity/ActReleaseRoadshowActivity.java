package com.touyan.investment.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.DateUtil;
import com.core.util.StringUtil;
import com.ogaclejapan.arclayout.ArcLayout;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.AbsFragment;
import com.touyan.investment.R;
import com.touyan.investment.bean.main.InvActDetailResult;
import com.touyan.investment.bean.main.InvReleaseActParam;
import com.touyan.investment.helper.AnimatorUtils;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.mview.ClipRevealFrame;
import com.touyan.investment.mview.EditTextWithDelete;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActReleaseRoadshowActivity extends AbsActivity implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private InvestmentManager investmentManager = new InvestmentManager();

    private EditText release_value_et;

    private EditText people_et;

    private TextView date_value_tv;

    private TextView city_value_tv;

    private TextView city_title_tv;

    private EditTextWithDelete value_ly;

    private static final int LOAD_RELEASE = 0x01;//发布

    private Calendar endDate;

    /**
     * 城市选择
     */
    private ClipRevealFrame menuLayout;

    private View centerItem;

    private ArcLayout arcLayout;

    private boolean isCityShow;

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
        initCityMenu();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_act_release_roadshow;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft(0, R.string.cancel);
        setToolbarIntermediateStrID(R.string.release_act_roadshow);
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

        people_et = (EditText) findViewById(R.id.people_et);

        //产品名称
        LinearLayout titleLy = (LinearLayout) findViewById(R.id.title_ly);
        TextView release_title_tv = (TextView) titleLy.findViewById(R.id.release_title_tv);
        release_value_et = (EditText) titleLy.findViewById(R.id.release_value_et);
        release_title_tv.setText(R.string.act_release_roadshow_title);
        release_value_et.setHint(R.string.act_release_roadshow_title_hint);

        //时间
        date_value_tv = (TextView) findViewById(R.id.date_value_tv);
        date_value_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        //城市
        city_value_tv = (TextView) findViewById(R.id.city_value_tv);
        city_title_tv = (TextView) findViewById(R.id.city_title_tv);
        city_value_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleShowCityMenu(city_title_tv);
            }
        });

        //详情
        value_ly = (EditTextWithDelete) findViewById(R.id.value_ly);
        value_ly.setHint(R.string.act_release_roadshow_value_hint);

    }

    private void initCityMenu() {
        menuLayout = (ClipRevealFrame) findViewById(R.id.menu_layout);
        centerItem = findViewById(R.id.center_item);
        arcLayout = (ArcLayout) findViewById(R.id.arc_layout);

        menuLayout.setOnClickListener(this);
        centerItem.setOnClickListener(this);
        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu_layout) {
            toggleShowCityMenu(city_title_tv);
            return;
        }
        if (v instanceof Button) {
            String cityStr = ((Button) v).getText().toString();
            city_value_tv.setText(cityStr);
            toggleShowCityMenu(city_title_tv);
        }

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

        String cityStr = city_value_tv.getText().toString();
        if (StringUtil.isBlank(cityStr)) {
            toggleShowCityMenu(city_title_tv);
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
        param.setActvtp(InvReleaseActParam.TYPE_ROADSHOW);
        param.setIspubl(InvReleaseActParam.PUBLIC_YES);
        String peopleStr = people_et.getText().toString();
        if (StringUtil.isNotBlank(peopleStr)) {
            param.setPintno(Integer.parseInt(peopleStr));
        }
        param.setByloct(cityStr);
        param.setAdress("");
        String endTimeStr = DateUtil.ConverToString(endDate.getTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        param.setStartm(endTimeStr);
        param.setEndtim(endTimeStr);
        param.setContnt(contentStr);
        param.setAtitle(titleStr);
        investmentManager.releaseAct(this, param, activityHandler, LOAD_RELEASE);

    }

    /**
     * 城市选择
     */
    private void toggleShowCityMenu(View v) {
        int x = menuLayout.getLeft() + menuLayout.getRight();
        int y = 0 ;
        float radiusOf = 1f * v.getWidth() / 2f;
        float radiusFromToRoot = (float) Math.hypot(
                Math.max(x, menuLayout.getWidth() - x),
                Math.max(y, menuLayout.getHeight() - y));

        if (!isCityShow) {
            isCityShow = true;
            showMenu(x, y, radiusOf, radiusFromToRoot);
        } else {
            isCityShow = false;
            hideMenu(x, y, radiusFromToRoot, radiusOf);
        }
    }

    /**
     * 时间选择
     */
    private void showDateDialog() {
        Calendar now = Calendar.getInstance();
        Date time = now.getTime();
        long currentTime = time.getTime();
        time.setTime(currentTime + 24 * 60 * 60);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ActReleaseRoadshowActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        now.setTime(time);
        dpd.setMinDate(now);
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    private void showTimeDialog() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                ActReleaseRoadshowActivity.this,
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

    private void showMenu(int cx, int cy, float startRadius, float endRadius) {
        menuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        Animator revealAnim = createCircularReveal(menuLayout, cx, cy, startRadius, endRadius);
        revealAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        revealAnim.setDuration(200);

        animList.add(revealAnim);
        animList.add(createShowItemAnimator(centerItem));

        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.playSequentially(animList);
        animSet.start();
    }

    private void hideMenu(int cx, int cy, float startRadius, float endRadius) {
        List<Animator> animList = new ArrayList<>();

        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        animList.add(createHideItemAnimator(centerItem));

        Animator revealAnim = createCircularReveal(menuLayout, cx, cy, startRadius, endRadius);
        revealAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        revealAnim.setDuration(200);
        revealAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
            }
        });

        animList.add(revealAnim);

        AnimatorSet animSet = new AnimatorSet();
        animSet.playSequentially(animList);
        animSet.start();

    }

    private Animator createShowItemAnimator(View item) {
        float dx = centerItem.getX() - item.getX();
        float dy = centerItem.getY() - item.getY();

        item.setScaleX(0f);
        item.setScaleY(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.scaleX(0f, 1f),
                AnimatorUtils.scaleY(0f, 1f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(50);
        return anim;
    }

    private Animator createHideItemAnimator(final View item) {
        final float dx = centerItem.getX() - item.getX();
        final float dy = centerItem.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.scaleX(1f, 0f),
                AnimatorUtils.scaleY(1f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.setInterpolator(new DecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });
        anim.setDuration(50);
        return anim;
    }

    private Animator createCircularReveal(final ClipRevealFrame view, int x, int y, float startRadius,
                                          float endRadius) {
        final Animator reveal;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            reveal = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, endRadius);
        } else {
            view.setClipOutLines(true);
            view.setClipCenter(x, y);
            reveal = ObjectAnimator.ofFloat(view, "ClipRadius", startRadius, endRadius);
            reveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setClipOutLines(false);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        return reveal;
    }

    @Override
    public void onBackPressed() {
        if (isCityShow) {
            toggleShowCityMenu(city_title_tv);
            return;
        }
        super.onBackPressed();
    }
}
