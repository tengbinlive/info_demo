package com.touyan.investment.activity;


import android.app.Activity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.*;

import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import com.core.CommonResponse;
import com.core.util.CommonUtil;

import com.core.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.Constant;
import com.touyan.investment.R;
import com.touyan.investment.bean.user.ModifyUserInfoResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.BottomView;

import java.util.ArrayList;

/**
 * Created by zxh on 2015/7/13.
 */
public class ModifyUserInfoActivity extends AbsActivity implements View.OnClickListener {

    private final static int MODIFY_DATA = 0;


    /* 头像名称 */
    private Uri headImageUri = Uri.parse(Constant.Dir.HEAD_IMAGE_TEMP);//The Uri to store the big bitmap

    /* 请求码 */
    private final static int REQUESTCODE_USERTAG = 0;

    private static final int IMAGE_REQUEST_CODE = 2;
    private static final int CAMERA_REQUEST_CODE = 3;
    private static final int RESULT_REQUEST_CODE = 4;


    private UserInfo userInfo;

    private ImageView userHeadImage;
    private EditText userNameEdit;
    private EditText userCompanyEdit;
    private EditText userCityEdit;
    private EditText userOccupationEdit;

    private LinearLayout userHeadBtn;
    private LinearLayout userTagBtn;

    private StringBuffer tagsStr = null;

    private BottomView mBottomView;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialogDismiss();
            switch (msg.what) {
                case MODIFY_DATA:
                    loadModifyUserInfoData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };


    private void loadModifyUserInfoData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            ModifyUserInfoResult result = (ModifyUserInfoResult) resposne.getData();
            App.getInstance().setgUserInfo(result.getUser());
            scrollToFinishActivity();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
        findView();
        initBtnListener();
        initUserInfoViews();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_modify_userinfo;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarRightStrID(R.string.login_fulfil);
        setToolbarIntermediateStrID(R.string.modify_userinfo_toolbar_title);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRightOnClick(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findView() {
        userHeadImage = (ImageView) findViewById(R.id.user_head);
        userNameEdit = (EditText) findViewById(R.id.user_name);
        userCompanyEdit = (EditText) findViewById(R.id.user_company);
        userCityEdit = (EditText) findViewById(R.id.user_city);
        userOccupationEdit = (EditText) findViewById(R.id.user_occupation);

        userHeadBtn = (LinearLayout) findViewById(R.id.user_head_btn);
        userTagBtn = (LinearLayout) findViewById(R.id.user_tag_btn);


    }

    private void initBtnListener() {
        userHeadBtn.setOnClickListener(this);
        userTagBtn.setOnClickListener(this);
    }

    private void initUserInfoViews() {
        userInfo = App.getInstance().getgUserInfo();
        ImageLoader.getInstance().displayImage(userInfo.getUphoto(), userHeadImage);
        if (StringUtil.isNotBlank(userInfo.getUalias())) {
            userNameEdit.setHint(userInfo.getUalias());
        }
        if (StringUtil.isNotBlank(userInfo.getCompny())) {
            userCompanyEdit.setHint(userInfo.getCompny());
        }
        if (StringUtil.isNotBlank(userInfo.getLocatn())) {
            userCityEdit.setHint(userInfo.getLocatn());
        }
        if (StringUtil.isNotBlank(userInfo.getPostin())) {
            userOccupationEdit.setHint(userInfo.getPostin());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_btn:
                userInfo = App.getInstance().getgUserInfo();
                UserManager userManager = new UserManager();
                userManager.modifyUserInfo(ModifyUserInfoActivity.this,
                        userInfo.getServno(),
                        StringUtil.isNotBlank(userNameEdit.getText().toString()) ? userNameEdit.getText().toString() : userInfo.getUalias(),
                        userInfo.getUphoto(),
                        StringUtil.isNotBlank(userCityEdit.getText().toString()) ? userCityEdit.getText().toString() : userInfo.getLocatn(),
                        userInfo.getInrank(),
                        StringUtil.isNotBlank(userCompanyEdit.getText().toString()) ? userCompanyEdit.getText().toString() : userInfo.getCompny(),
                        StringUtil.isNotBlank(userOccupationEdit.getText().toString()) ? userOccupationEdit.getText().toString() : userInfo.getPostin(),
                        userInfo.getTeleph(),
                        userInfo.getRscope(),
                        userInfo.getUisvip(),
                        tagsStr != null ? tagsStr.toString() : userInfo.getTags(),
                        null,
                        null,
                        activityHandler,
                        MODIFY_DATA);
                dialogShow(R.string.data_uploading);
                break;
            case R.id.user_head_btn:
                selectPict();
                break;
            case R.id.user_tag_btn:
                toActivityForResult(ModifyUserInfoActivity.this, ModifyUserTagActivity.class, REQUESTCODE_USERTAG);
                break;
        }
    }

    private void toActivityForResult(Activity activity, Class activityClass, int requestCode) {
        Intent intent = new Intent(activity, activityClass);
        startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.push_translate_in_right, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE_USERTAG && data != null) {
            ArrayList<Integer> userTagItemList = data.getIntegerArrayListExtra(KEY);
            if (userTagItemList == null) {
                return;
            }

            String[] userTagArray = getResources().getStringArray(R.array.user_tag_array);
            tagsStr = new StringBuffer();
            for (int i = 0; i < userTagItemList.size(); i++) {
                tagsStr.append(userTagArray[userTagItemList.get(i)]).append("/");
            }
        }
        //结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case IMAGE_REQUEST_CODE:

                    startPhotoZoom(headImageUri);
                    break;
                case CAMERA_REQUEST_CODE:
                    if (hasSdcard()) {
                        startPhotoZoom(headImageUri);
                    } else {
                        CommonUtil.showToast("未找到存储卡，无法存储照片！");
                    }
                    break;
                case RESULT_REQUEST_CODE:

                    getImageToView(headImageUri);
                    break;
            }
        } else {
            CommonUtil.showToast("操作取消");
        }
    }

    private void selectPict() {
        if (mBottomView != null) {
            mBottomView.showBottomView(true);
            return;
        }
        mBottomView = new BottomView(ModifyUserInfoActivity.this, R.style.BottomViewTheme_Defalut, R.layout.bottom_view);
        mBottomView.setAnimation(R.style.BottomToTopAnim);

        TextView top = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_1);
        TextView shareFriend = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_2);
        TextView shareGroup = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_3);
        TextView cancel = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_cancel);
        View divider1 = (View) mBottomView.getView().findViewById(R.id.divider1);

        top.setVisibility(View.GONE);
        divider1.setVisibility(View.GONE);

        shareFriend.setText("拍照");
        shareGroup.setText("从相册获取");

        ShareButtonOnClickListener listener = new ShareButtonOnClickListener();
        shareFriend.setOnClickListener(listener);
        shareGroup.setOnClickListener(listener);
        cancel.setOnClickListener(listener);

        mBottomView.showBottomView(true);
    }

    class ShareButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bottom_tv_2:

                    Intent intentFromCapture = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    if (hasSdcard()) {
                        intentFromCapture.putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                headImageUri);
                    }
                    startActivityForResult(intentFromCapture,
                            CAMERA_REQUEST_CODE);
                    break;
                case R.id.bottom_tv_3:

                    Intent intentFromGallery = new Intent();
                    intentFromGallery.setType("image/*"); // 设置文件类型
                    intentFromGallery
                            .setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);

                    break;
                case R.id.bottom_tv_cancel:
                    mBottomView.dismissBottomView();
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);//输出是X方向的比例
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);//输出X方向的像素
        intent.putExtra("outputY", 300);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);//设置为不返回数据

        startActivityForResult(intent, 2);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param uri
     */
    private void getImageToView(Uri uri) {

        try {
            Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            Drawable drawable = new BitmapDrawable(getResources(), photo);
            userHeadImage.setImageDrawable(drawable);
        } catch (Exception e) {
            CommonUtil.showToast("头像读取失败");
        }

    }

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

}
