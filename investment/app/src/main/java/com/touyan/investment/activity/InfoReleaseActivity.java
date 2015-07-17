package com.touyan.investment.activity;

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
import com.core.util.FileDataHelper;
import com.core.util.StringUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.Constant;
import com.touyan.investment.R;
import com.touyan.investment.adapter.InfoReleaseGridAdapter;
import com.touyan.investment.mview.EditTextWithDelete;
import com.touyan.investment.mview.MGridView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InfoReleaseActivity extends AbsActivity {

    private static final int LOAD_DATA = 0x01;//加载数据处理

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

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case LOAD_DATA:
                    loadData((CommonResponse) msg.obj, what);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(CommonResponse resposne, int what) {
        dialogDismiss();
        if (resposne.isSuccess()) {

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
        String origFileName = "osc_" + timeStamp + ".png";
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
        if (null == upLoadImages || upLoadImages.size() <= 0) {
            gridView.setVisibility(View.GONE);
        } else {
            gridView.setVisibility(View.VISIBLE);
        }
    }

    private void releaseInfo() {
        if (isCheck()) {
            dialogShow(R.string.reviewing);
        }
    }

    private boolean isCheck() {
        String title = release_info_title.getText().toString();
        if (StringUtil.isBlank(title)) {
            release_info_title.requestFocus();
            CommonUtil.showToast("标题没有哦~");
            return false;
        }

        String contents = release_info_value.getText().toString();
        if (StringUtil.isBlank(contents)) {
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


}
