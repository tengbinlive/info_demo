package com.touyan.investment.activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.DateUtil;
import com.core.util.FileDataHelper;
import com.core.util.StringUtil;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.Constant;
import com.touyan.investment.R;
import com.touyan.investment.bean.qiniu.QiniuUploadBean;
import com.touyan.investment.bean.qiniu.QiniuUploadResult;
import com.touyan.investment.bean.user.ModifyUserInfoResult;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.manager.QiniuManager;
import com.touyan.investment.manager.UserManager;
import com.touyan.investment.mview.BottomView;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by zxh on 2015/7/13.
 */
public class ModifyUserInfoActivity extends AbsActivity implements View.OnClickListener {

    //七牛相关
    private QiniuManager qiniuManager = new QiniuManager();//七牛业务

    private QiniuUploadBean uploadFile;//上传七牛数据

    private QiniuUploadResult qiniuUploadResult;//七牛 token

    private UploadManager uploadManager = new UploadManager();

    private static final int LOAD_QINIU = 0x01;//七牛 token 处理

    private boolean isCancelled = false; //true 停止上传

    private static final String STR_PATH = "path";

    private static final String STR_CLIPRATIO = "clipRatio";

    /**
     * 本地图片选取标志
     */
    private static final int FLAG_CHOOSE_IMG = 0x11;
    /**
     * 截取结束标志
     */
    private static final int FLAG_MODIFY_FINISH = 0x7;
    /**
     * 相机标志
     */
    private static final int FLAG_CHOOSE_CAMERA = 0x17;

    /**
     * 图片地址
     */
    private Uri imageUri;

    private double clipRatio = 1.0;

    /////////
    UserManager userManager = new UserManager();

    private StringBuffer pictueHead = new StringBuffer();

    private final static int UPLOAD_HEAD = 0x18; //上传头像

    private final static int MODIFY_DATA = 0;

    /* 请求码 */
    private final static int REQUESTCODE_USERTAG = 0;

    private boolean userInfoUpload = false;
    private boolean headInfoUpload = false;

    private UserInfo userInfo;

    private SelectableRoundedImageView userHeadImage;
    private EditText userNameEdit;
    private EditText userCompanyEdit;
    private EditText userCityEdit;
    private TextView userOccupationTv;

    private LinearLayout userHeadBtn;
    private LinearLayout userTagBtn;

    private StringBuffer tagsStr = null;

    private BottomView mBottomView;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MODIFY_DATA:
                    loadModifyUserInfoData((CommonResponse) msg.obj);
                    break;
                case LOAD_QINIU:
                    loadQiniu((CommonResponse) msg.obj);
                    break;
                case UPLOAD_HEAD:
                    loadHead((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadQiniu(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            qiniuUploadResult = (QiniuUploadResult) resposne.getData();
            loadImageQiniu();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadHead(CommonResponse resposne) {
        if (!userInfoUpload) {
            dialogDismiss();
        }
        headInfoUpload = false;
        if (resposne.isSuccess()) {
            App.getInstance().getgUserInfo().setUphoto(pictueHead.toString());
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
        finishActivity();
    }

    /**
     * 上传带图片的info
     * 1. 获取七牛token
     * 2. 使用token上传图片至七牛
     * 3. 成功后上传资讯数据至业务服务器
     */
    private void getQiniuTokenOrRe() {
        if (null == uploadFile) {
            return;
        }
        headInfoUpload = true;
        qiniuManager.qiniuUpload(this, activityHandler, LOAD_QINIU);
    }

    /**
     * 上传带图片 至七牛
     */
    private void loadImageQiniu() {
        //没有上传成功过的文件 进行上传
        if (!uploadFile.isUpload()) {
            String path = uploadFile.getPath();
            //生成图片上传名称
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYYMMDDHHMMSS);
            Random random = new Random();
            String num = "";
            for (int i = 0; i < 4; i++) {
                num += random.nextInt(10);
            }
            String key = "user_" + App.getInstance().getgUserInfo().getServno() + "_" + sdf.format(date) + "_" + num + ".jpg";
            uploadFile.setName(key);
            uploadManager.put(path, key, qiniuUploadResult.getUptoken(),
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                uploadFile.setIsUpload(true);
                                if (!isCancelled) {
                                    uploadHead();
                                } else {
                                    headInfoUpload = false;
                                }
                            } else {
                                headInfoUpload = false;
                                uploadFile.setIsUpload(false);
                                CommonUtil.showToast("头像上传失败啦~");
                            }
                        }
                    }, new UploadOptions(null, null, false, null,//让 UpCancellationSignal#isCancelled() 方法返回 true ，以停止上传
                            new UpCancellationSignal() {
                                public boolean isCancelled() {
                                    return isCancelled;
                                }
                            }));
        }
    }

    private void uploadHead() {
        //拼接上传图片名称
        pictueHead = new StringBuffer();
        pictueHead.append(QiniuUploadBean.QINIU_URL).append(uploadFile.getName());
        userManager.uploadHead(this, pictueHead.toString(), activityHandler, UPLOAD_HEAD);
    }

    private void loadModifyUserInfoData(CommonResponse resposne) {
        userInfoUpload = false;
        dialogDismiss();
        if (resposne.isSuccess()) {
            ModifyUserInfoResult result = (ModifyUserInfoResult) resposne.getData();
            App.getInstance().setgUserInfo(result.getUser());
            finishActivity();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void finishActivity() {
        if (!userInfoUpload && !headInfoUpload) {
            dialogDismiss();
            scrollToFinishActivity();
        } else if (!userInfoUpload && headInfoUpload) {
            dialogShow("正在上传头像");
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

    private void findView() {
        userHeadImage = (SelectableRoundedImageView) findViewById(R.id.user_head);
        userNameEdit = (EditText) findViewById(R.id.user_name);
        userCompanyEdit = (EditText) findViewById(R.id.user_company);
        userCityEdit = (EditText) findViewById(R.id.user_city);
        userOccupationTv = (TextView) findViewById(R.id.user_occupation);

        userHeadBtn = (LinearLayout) findViewById(R.id.user_head_btn);
        userTagBtn = (LinearLayout) findViewById(R.id.user_tag_btn);

    }

    private void initBtnListener() {
        userHeadBtn.setOnClickListener(this);
        userTagBtn.setOnClickListener(this);
        userOccupationTv.setOnClickListener(this);
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
            userOccupationTv.setHint(userInfo.getPostin());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right_btn:
                getQiniuTokenOrRe();
                userInfoUpload = true;
                userInfo = App.getInstance().getgUserInfo();
                if (StringUtil.isBlank(userNameEdit.getText().toString()) && StringUtil.isBlank(userInfo.getUalias())) {
                    CommonUtil.showToast("请填写姓名");
                } else if (StringUtil.isBlank(userOccupationTv.getText().toString()) && StringUtil.isBlank(userInfo.getPostin())) {
                    CommonUtil.showToast("请填写职位");
                } else if (StringUtil.isBlank(userCompanyEdit.getText().toString()) && StringUtil.isBlank(userInfo.getCompny())) {
                    CommonUtil.showToast("请填写公司");
                } else if (StringUtil.isBlank(userCityEdit.getText().toString()) && StringUtil.isBlank(userInfo.getLocatn())) {
                    CommonUtil.showToast("请填写地区");
                } else {
                    userManager.modifyUserInfo(ModifyUserInfoActivity.this,
                            userInfo.getServno(),
                            StringUtil.isNotBlank(userNameEdit.getText().toString()) ? userNameEdit.getText().toString() : userInfo.getUalias(),
                            StringUtil.isNotBlank(pictueHead.toString())?pictueHead.toString():userInfo.getUphoto(),
                            StringUtil.isNotBlank(userCityEdit.getText().toString()) ? userCityEdit.getText().toString() : userInfo.getLocatn(),
                            userInfo.getInrank(),
                            StringUtil.isNotBlank(userCompanyEdit.getText().toString()) ? userCompanyEdit.getText().toString() : userInfo.getCompny(),
                            StringUtil.isNotBlank(userOccupationTv.getText().toString()) ? userOccupationTv.getText().toString() : userInfo.getPostin(),
                            userInfo.getTeleph(),
                            userInfo.getRscope(),
                            userInfo.getUisvip(),
                            tagsStr != null ? tagsStr.toString() : userInfo.getTags(),
                            null,
                            null,
                            activityHandler,
                            MODIFY_DATA);
                    dialogShow(R.string.carrying, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            isCancelled = true;
                        }
                    });
                }

                break;
            case R.id.user_head_btn:
                selectPict();
                break;
            case R.id.user_occupation:
                selectPostin();
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
            if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
                processGalleryIMG(data);
            } else if (requestCode == FLAG_CHOOSE_CAMERA && resultCode == RESULT_OK) {
                processCamera();
            } else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {
                if (data != null) {
                    final String path = data.getStringExtra(STR_PATH);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    userHeadImage.setImageBitmap(bitmap);
                    uploadFile = new QiniuUploadBean();
                    uploadFile.setPath(path);
                }
            }
        }
    }

    private void processGalleryIMG(Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                Cursor cursor = getContentResolver().query(uri,
                        new String[]{MediaStore.Images.Media.DATA},
                        null, null, null);
                if (null == cursor) {
                    CommonUtil.showToast("图片没找到哦");
                    return;
                }
                cursor.moveToFirst();
                String path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();

                Intent intent = new Intent(this, AuthClipPictureActivity.class);
                intent.putExtra(STR_PATH, path);
                intent.putExtra(STR_CLIPRATIO, clipRatio);
                startActivityForResult(intent, FLAG_MODIFY_FINISH);
            } else {
                Intent intent = new Intent(this, AuthClipPictureActivity.class);
                intent.putExtra(STR_PATH, uri.getPath());
                intent.putExtra(STR_CLIPRATIO, clipRatio);
                startActivityForResult(intent, FLAG_MODIFY_FINISH);
            }
        }
    }

    private void processCamera() {
        Intent intent = new Intent(this, AuthClipPictureActivity.class);
        intent.putExtra(STR_PATH, imageUri.getPath());
        intent.putExtra(STR_CLIPRATIO, clipRatio);
        startActivityForResult(intent, FLAG_MODIFY_FINISH);
    }

    private void selectPict() {
        mBottomView = new BottomView(ModifyUserInfoActivity.this, R.style.BottomViewTheme_Defalut, R.layout.bottom_view);
        mBottomView.setAnimation(R.style.BottomToTopAnim);

        TextView top = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_1);
        TextView shareFriend = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_2);
        TextView shareGroup = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_3);
        TextView cancel = (TextView) mBottomView.getView().findViewById(R.id.bottom_tv_cancel);
        View divider1 = mBottomView.getView().findViewById(R.id.divider1);

        top.setVisibility(View.GONE);
        divider1.setVisibility(View.GONE);

        shareFriend.setText("拍照");
        shareGroup.setText("从相册获取");

        PictButtonOnClickListener listener = new PictButtonOnClickListener();
        shareFriend.setOnClickListener(listener);
        shareGroup.setOnClickListener(listener);
        cancel.setOnClickListener(listener);

        mBottomView.showBottomView(true);
    }

    class PictButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bottom_tv_2:
                    getImageFromCamera();
                    mBottomView.dismissBottomView();
                    break;
                case R.id.bottom_tv_3:
                    getImageFromGallery();
                    mBottomView.dismissBottomView();
                    break;
                case R.id.bottom_tv_cancel:
                    mBottomView.dismissBottomView();
                    break;
            }
        }
    }

    private void selectPostin() {
        mBottomView = new BottomView(this, R.style.BottomViewTheme_Defalut, R.layout.bottom_role_view);
        mBottomView.setAnimation(R.style.BottomToTopAnim);

        PostinButtonOnClickListener listener = new PostinButtonOnClickListener();
        mBottomView.getView().findViewById(R.id.bottom_tv_2).setOnClickListener(listener);
        mBottomView.getView().findViewById(R.id.bottom_tv_3).setOnClickListener(listener);
        mBottomView.getView().findViewById(R.id.bottom_tv_4).setOnClickListener(listener);
        mBottomView.getView().findViewById(R.id.bottom_tv_5).setOnClickListener(listener);
        mBottomView.getView().findViewById(R.id.bottom_tv_cancel).setOnClickListener(listener);

        mBottomView.showBottomView(true);
    }

    class PostinButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bottom_tv_2:
                case R.id.bottom_tv_3:
                case R.id.bottom_tv_4:
                case R.id.bottom_tv_5:
                    String roleStr = ((TextView) v).getText().toString();
                    userOccupationTv.setText(roleStr);
                    mBottomView.dismissBottomView();
                    break;
                case R.id.bottom_tv_cancel:
                    mBottomView.dismissBottomView();
                    break;
            }
        }
    }

    /**
     * 相册
     */
    private void getImageFromGallery() {
        imageStyle();

        Intent intentFromGallery = new Intent();
        intentFromGallery.setAction(Intent.ACTION_PICK);
        intentFromGallery.setType("image/*");
        startActivityForResult(intentFromGallery, FLAG_CHOOSE_IMG);
    }

    /**
     * 相机获取图片
     */
    private void getImageFromCamera() {
        imageStyle();
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (FileDataHelper.hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }
        startActivityForResult(intentFromCapture, FLAG_CHOOSE_CAMERA);

    }

    private void imageStyle() {
        // 照片命名
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String origFileName = "osc_" + timeStamp + ".jpg";
        String path = FileDataHelper.getFilePath(Constant.Dir.IMAGE_TEMP);
        imageUri = Uri.fromFile(new File(path, origFileName));
    }

}
