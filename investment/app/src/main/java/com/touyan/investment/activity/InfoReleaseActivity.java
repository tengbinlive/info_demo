package com.touyan.investment.activity;

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
import com.kyleduo.switchbutton.SwitchButton;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.touyan.investment.*;
import com.touyan.investment.adapter.InfoReleaseGridAdapter;
import com.touyan.investment.bean.main.InvInfoBean;
import com.touyan.investment.bean.main.InvInfoReleaseResult;
import com.touyan.investment.bean.main.InvReleaseInfoParam;
import com.touyan.investment.bean.qiniu.QiniuUploadBean;
import com.touyan.investment.bean.qiniu.QiniuUploadResult;
import com.touyan.investment.manager.InvestmentManager;
import com.touyan.investment.manager.QiniuManager;
import com.touyan.investment.mview.EditTextWithDelete;
import com.touyan.investment.mview.MGridView;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class InfoReleaseActivity extends AbsActivity {

    private InvestmentManager investmentManager = new InvestmentManager();

    //七牛相关
    private QiniuManager qiniuManager = new QiniuManager();//七牛业务

    private ArrayList<QiniuUploadBean> uploadFile = new ArrayList<QiniuUploadBean>();//上传七牛数据

    private QiniuUploadResult qiniuUploadResult;//七牛 token

    private UploadManager uploadManager = new UploadManager();

    private static final int LOAD_QINIU = 0x01;//七牛 token 处理

    private int uploadQiniuIndex = 0;

    private boolean isCancelled = false; //true 停止上传

    /////////

    private static final int LOAD_RELEASE_INFO = 0x02;//发布资讯

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

    private EditTextWithDelete release_info_title;

    private EditTextWithDelete release_info_value;

    private EditText fee_et;

    private MGridView gridView;

    private ImageView gallery_iv;

    private ImageView camera_iv;

    private TextView image_title;

    private SwitchButton toll_sw_bt;

    private SwitchButton public_sw_bt;

    private InfoReleaseGridAdapter mAdapter;

    private ArrayList<Bitmap> upLoadImages;

    private boolean isPublicInfo;

    private String titleInfo;

    private String contentInfo;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_QINIU:
                    loadQiniu((CommonResponse) msg.obj);
                    break;
                case LOAD_RELEASE_INFO:
                    loadReleaseInfo((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadReleaseInfo(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            String path = FileDataHelper.getFilePath(Constant.Dir.IMAGE_TEMP);
            FileDataHelper.deleteFiles(FileDataHelper.getFilePath(path));
            InvInfoReleaseResult releaseResult = (InvInfoReleaseResult) resposne.getData();
            InvInfoBean info = releaseResult.getInfo();
            info.setUser(App.getInstance().getgUserInfo());
            info.setUser(App.getInstance().getgUserInfo());
            Intent intent = new Intent();
            intent.putExtra(KEY, info);
            setResult(AbsFragment.RECODE_RELEASE, intent);
            CommonUtil.showToast("发布成功");
            finish();
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
    }

    private void loadQiniu(CommonResponse resposne) {
        if (resposne.isSuccess()) {
            qiniuUploadResult = (QiniuUploadResult) resposne.getData();
            loadImageQiniu();
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
        return R.layout.activity_info_release;
    }

    @Override
    public void initActionBar() {
        setToolbarLeft(0, R.string.cancel);
        setToolbarIntermediateStrID(R.string.release_info);
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
        gallery_iv = (ImageView) findViewById(R.id.gallery_iv);
        camera_iv = (ImageView) findViewById(R.id.camera_iv);
        gridView = (MGridView) findViewById(R.id.gridview);
        image_title = (TextView) findViewById(R.id.image_title);
        release_info_title = (EditTextWithDelete) findViewById(R.id.release_info_title);
        release_info_value = (EditTextWithDelete) findViewById(R.id.release_info_value);
        fee_et = (EditText) findViewById(R.id.fee_et);
        toll_sw_bt = (SwitchButton) findViewById(R.id.toll_sw_bt);
        public_sw_bt = (SwitchButton) findViewById(R.id.public_sw_bt);
        final RelativeLayout toll_ly = (RelativeLayout) findViewById(R.id.toll_ly);
        toll_sw_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    toll_ly.setVisibility(View.VISIBLE);
                } else {
                    toll_ly.setVisibility(View.GONE);
                    fee_et.setText("");
                }
            }
        });

        public_sw_bt.setChecked(true);

        public_sw_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isPublicInfo = b;
            }
        });

        gallery_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromGallery();
            }
        });

        camera_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromCamera();
            }
        });

        initGridView();
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

    private void initGridView() {
        upLoadImages = new ArrayList<Bitmap>();

        mAdapter = new InfoReleaseGridAdapter(this, upLoadImages);

        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                upLoadImages.remove(i);
                mAdapter.refresh(upLoadImages);
                updateGridView();
            }
        });
    }

    private void updateGridView() {
        int size = null == upLoadImages ? 0 : upLoadImages.size();
        if (size <= 0) {
            gridView.setVisibility(View.GONE);
        } else {
            gridView.setVisibility(View.VISIBLE);
        }
        image_title.setText("添加图片\n(" + size + "/9)");
    }

    private void releaseInfo() {
        if (isCheck()) {
            dialogShow(R.string.carrying, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    isCancelled = true;
                }
            });
            int imagePathSize = uploadFile.size();
            if (imagePathSize > 0) {
                getQiniuTokenOrRe();
            } else {
                uploadRelease();
            }
        }
    }

    private boolean isCheck() {
        titleInfo = release_info_title.getText().toString();
        if (StringUtil.isBlank(titleInfo)) {
            release_info_title.requestFocus();
            CommonUtil.showToast("标题没有哦~");
            return false;
        }

        contentInfo = release_info_value.getText().toString();
        if (StringUtil.isBlank(contentInfo)) {
            release_info_value.requestFocus();
            CommonUtil.showToast("说点什么吧~");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
            processGalleryIMG(data);
        } else if (requestCode == FLAG_CHOOSE_CAMERA && resultCode == RESULT_OK) {
            processCamera();
        } else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {
            if (data != null) {
                final String path = data.getStringExtra(STR_PATH);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                QiniuUploadBean bean = new QiniuUploadBean();
                bean.setPath(path);
                uploadFile.add(bean);
                upLoadImages.add(bitmap);
                updateGridView();
                mAdapter.refresh(upLoadImages);
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
        uploadQiniuIndex = 0;
        final int uploadNum = uploadFile.size();
        for (final QiniuUploadBean bean : uploadFile) {
            //没有上传成功过的文件 进行上传
            if (!bean.isUpload()) {
                String path = bean.getPath();
                //生成图片上传名称
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYYMMDDHHMMSS);
                Random random = new Random();
                String num = "";
                for (int i = 0; i < 4; i++) {
                    num += random.nextInt(10);
                }
                String key = "info_" + App.getInstance().getgUserInfo().getServno() + "_" + sdf.format(date) + "_" + num + ".jpg";
                bean.setName(key);
                uploadManager.put(path, key, qiniuUploadResult.getUptoken(),
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    bean.setIsUpload(true);
                                    if (uploadQiniuIndex == uploadNum && !isCancelled) {
                                        uploadRelease();
                                    }
                                } else {
                                    bean.setIsUpload(false);
                                    dialogDismiss();
                                    CommonUtil.showToast("发表失败啦~");
                                }
                            }
                        }, new UploadOptions(null, null, false, null,//让 UpCancellationSignal#isCancelled() 方法返回 true ，以停止上传
                                new UpCancellationSignal() {
                                    public boolean isCancelled() {
                                        return isCancelled;
                                    }
                                }));
            }
            uploadQiniuIndex++;
        }
    }

    /**
     * 上传无图片的info
     */
    private void uploadRelease() {
        String feeStr = fee_et.getText().toString();
        InvReleaseInfoParam param = new InvReleaseInfoParam();
        param.setItitle(titleInfo);
        param.setContnt(contentInfo);
        if (StringUtil.isNotBlank(feeStr)) {
            param.setCharge(Double.valueOf(feeStr));
        }

        //拼接上传图片名称
        StringBuffer pictue = new StringBuffer();
        for (final QiniuUploadBean bean : uploadFile) {
            String imageName = bean.getName();
            pictue.append(QiniuUploadBean.QINIU_URL).append(imageName).append(",");
        }
        if (StringUtil.isNotBlank(pictue.toString())) {
            param.setPictue(pictue.toString());
        }

        param.setIspubl(isPublicInfo ? InvReleaseInfoParam.PUBLIC_YES : InvReleaseInfoParam.PUBLIC_NO);
        investmentManager.releaseInfo(this, param, activityHandler, LOAD_RELEASE_INFO);
    }

}
