package com.touyan.investment.activity;

import android.content.Context;
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
import android.view.View.OnClickListener;
import android.widget.*;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.DateUtil;
import com.core.util.FileDataHelper;
import com.core.util.StringUtil;
import com.joooonho.SelectableRoundedImageView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.rey.material.widget.CheckBox;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.App;
import com.touyan.investment.Constant;
import com.touyan.investment.R;
import com.touyan.investment.bean.message.CreateGroupParam;
import com.touyan.investment.bean.qiniu.QiniuUploadBean;
import com.touyan.investment.bean.qiniu.QiniuUploadResult;
import com.touyan.investment.manager.MessageManager;
import com.touyan.investment.manager.QiniuManager;
import com.touyan.investment.mview.BottomView;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CreateGroupActivity extends AbsActivity implements OnClickListener {

    //七牛相关
    private QiniuManager qiniuManager = new QiniuManager();//从自己服务器获取token接口的

    private QiniuUploadBean uploadFile = new QiniuUploadBean();;//上传七牛数据 文件路径

    private QiniuUploadResult qiniuUploadResult;//七牛 token

    private MessageManager messageManager =new MessageManager();

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


    private final static int MODIFY_DATA = 0;

    private Context context;
    private SelectableRoundedImageView head;
    private EditText groupname_et;
    private String groupname;
    private CheckBox visible_cb;
    private BottomView mBottomView;
    private CreateGroupParam param = new CreateGroupParam();
    @Override
    public void EInit() {
        super.EInit();
        findView();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_creategroup;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediateStrID(R.string.create_group);
        setToolbarRightVisbility(View.VISIBLE, View.VISIBLE);
        setToolbarRightStrID(R.string.create);
        setToolbarRightOnClick(this);
    }

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            CommonResponse resposne=(CommonResponse) msg.obj;
            switch (msg.what) {
                case LOAD_QINIU://获取七牛token返回
                    if (resposne.isSuccess()) {
                        qiniuUploadResult = (QiniuUploadResult) resposne.getData();
                        loadImageQiniu();//上传图片
                    } else {
                        dialogDismiss();
                        CommonUtil.showToast(resposne.getErrorTip());
                    }
                    break;
                case MODIFY_DATA:
                    dialogDismiss();
                    if (resposne.isSuccess()) {
                        //String groupid = (String)resposne.getData();
                        CommonUtil.showToast("创建成功！");
                    } else {
                        CommonUtil.showToast(resposne.getErrorTip());
                    }

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.head) {
            selectPict();
        } else if (id == R.id.toolbar_right_btn) {
            isCancelled = false;
            groupname = groupname_et.getText().toString();
            if (!StringUtil.isEmpty(groupname)){

                if(uploadFile.getPath()!=null){
                    getQiniuTokenOrRe();
                }else {
                    upMyserver();
                }
            }else {
                CommonUtil.showToast("请填写群名称");
            }

            dialogShow(R.string.data_uploading, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    isCancelled = true;
                }
            });
        }
    }

    private void findView() {
        context = CreateGroupActivity.this;
        head = (SelectableRoundedImageView)findViewById(R.id.head);
        groupname_et = (EditText)findViewById(R.id.groupname_et);
        visible_cb = (CheckBox)findViewById(R.id.visible_cb);

        head.setOnClickListener(this);
        param.setVisble("1");
        visible_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    param.setVisble("1");
                }else{
                    param.setVisble("0");
                }
            }
        });
    }

    /**
     * 上传带图片的info
     * 1. 获取七牛token
     * 2. 使用token上传图片至七牛
     * 3. 成功后上传资讯数据至业务服务器
     */
    private void getQiniuTokenOrRe() {
        qiniuManager.qiniuUpload(this, activityHandler, LOAD_QINIU);
    }

    /**
     * 上传带图片 至七牛
     */
    private void loadImageQiniu() {
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
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(path, key, qiniuUploadResult.getUptoken(),
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            upMyserver();
                        } else {
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

    private void upMyserver(){
        if(uploadFile.getName()!=null){
            param.setGphoto(uploadFile.QINIU_URL+uploadFile.getName());
        }else {
            param.setGphoto(null);
        }
        param.setGroupname(groupname);
        param.setDesc("server create group");
        param.setIsPublic(true);
        param.setOwner(App.getInstance().getgUserInfo().getServno());
        messageManager.createGroups(CreateGroupActivity.this,param, activityHandler, MODIFY_DATA);
    }

    private void selectPict() {
        mBottomView = new BottomView(CreateGroupActivity.this, R.style.BottomViewTheme_Defalut, R.layout.bottom_view);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    head.setImageBitmap(bitmap);
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

}
