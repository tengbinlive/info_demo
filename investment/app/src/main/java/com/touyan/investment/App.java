package com.touyan.investment;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import cn.sharesdk.framework.ShareSDK;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.core.CrashHandler;
import com.core.enums.ConfigKeyEnum;
import com.core.manager.ConfigManager;
import com.core.openapi.OpenApi;
import com.core.util.*;
import com.core.util.NetworkUtil.NetworkClassEnum;
import com.dao.DaoMaster;
import com.dao.DaoMaster.OpenHelper;
import com.dao.DaoSession;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.event.OnContactUpdataEvent;
import com.touyan.investment.event.OnGroupsUpdataEvent;
import com.touyan.investment.helper.SharedPreferencesHelper;
import com.touyan.investment.hx.HXCacheUtils;
import com.touyan.investment.hx.HXChatManagerInit;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;

/**
 * App运行时上下文.
 * <p/>
 * 约定: 1)Constant类里保存系统安装之后就一直保持不变的常量;
 * 2)App类里保存系统启动后可变的变量,变量的值一般在系统初始化时保存,和状态相关的量在过程中可变;
 * 3)SharedPeferences对象持久化App里部分的变量, 供App初始化时读取, 其他类统一读取App里的变量,
 * 不访问SharedPerferences, 如果以后更换持久化的方式,例如DB,则仅修改App类就可以.
 *
 * @author bin.teng
 */
public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    private ArrayList<Activity> activities = new ArrayList<Activity>();

    // 账号在别处登录
    public static boolean isConflict = false;
    // 账号被移除
    public static boolean isCurrentAccountRemoved = false;

    private static App instance;

    private UserInfo gUserInfo;

    private DisplayImageOptions optionsHead;

    private DisplayImageOptions optionsImage;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;

    private BroadcastReceiver connectionReceiver;

    private static IWXAPI wxApi;

    /**
     * 获得本类的一个实例
     */
    public static App getInstance() {
        return instance;
    }

    public DisplayImageOptions getOptionsImage() {
        return optionsImage;
    }

    public void setOptionsImage(DisplayImageOptions optionsImage) {
        this.optionsImage = optionsImage;
    }

    public DisplayImageOptions getOptionsHead() {
        return optionsHead;
    }

    public void setOptionsHead(DisplayImageOptions optionsHead) {
        this.optionsHead = optionsHead;
    }

    public UserInfo getgUserInfo() {
        if (gUserInfo == null) {
            String userinfo = SharedPreferencesHelper.getString(this, Constant.LoginUser.SHARED_PREFERENCES_USER, "");
            gUserInfo = JSON.parseObject(userinfo, new TypeReference<UserInfo>() {
            });
        }
        return gUserInfo;
    }

    public void setgUserInfo(UserInfo gUserInfo) {
        this.gUserInfo = gUserInfo;
    }

    /**
     * 取得DaoMaster
     */
    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            OpenHelper helper = new DaoMaster.DevOpenHelper(App.getInstance(), Constant.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     */
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 获得微信API对象.
     *
     * @return 微信API对象
     */
    public static IWXAPI getWXApi() {
        if (wxApi == null) {
            wxApi = WXAPIFactory.createWXAPI(App.getInstance(), null);
            wxApi.registerApp(Constant.WeiXin.APP_ID);
        }
        return wxApi;
    }

    //---------以下变量存储在APP(内存)中----------//

    /**
     * 当前网络状态
     */
    private static NetworkClassEnum currentNetworkStatus = NetworkClassEnum.UNKNOWN;


    /**
     * @return 返回当前网络状态枚举类(例如: 未知 / 2G/ 3G / 4G / wifi)
     */
    public static NetworkClassEnum getCurrentNetworkStatus() {
        return currentNetworkStatus;
    }

    /**
     * @param currentNetworkStatus 当前网络状态枚举类
     */
    public static void setCurrentNetworkStatus(NetworkClassEnum currentNetworkStatus) {
        App.currentNetworkStatus = currentNetworkStatus;
    }

    public static boolean isNetworkAvailable() {
        return !NetworkClassEnum.UNKNOWN.equals(currentNetworkStatus);
    }

    //---------以下变量由ConfigManager管理----------//

    /**
     * @return 返回硬件设备编号
     */
    public static String getDeviceId() {
        return ConfigManager.getConfigAsString(ConfigKeyEnum.DEVICE_ID);
    }

    /**
     * @return 返回手机型号
     */
    public static String getMobileType() {
        return ConfigManager.getConfigAsString(ConfigKeyEnum.MOBILE_TYPE);
    }

    /**
     * @return 返回IMEI
     */
    public static String getIMEI() {
        return ConfigManager.getConfigAsString(ConfigKeyEnum.IMEI);
    }

    /**
     * @return 返回屏幕 宽
     */
    public static int getScreenWidth() {
        return ConfigManager.getConfigAsInt(ConfigKeyEnum.SCREEN_WIDTH);
    }

    /**
     * @return 返回屏幕 高
     */
    public static int getScreenHeight() {
        return ConfigManager.getConfigAsInt(ConfigKeyEnum.SCREEN_HEIGHT);
    }

    /**
     * @return 返回APP版本名称
     */
    public static String getAppVersionName() {
        return ConfigManager.getConfigAsString(ConfigKeyEnum.APP_VERSION_NAME);
    }

    /**
     * @return 是否第一次启动(某版本)
     */
    public static boolean isFirstLunch() {
        return ConfigManager.getConfigAsBoolean(ConfigKeyEnum.IS_FIRST_LUNCH);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 多进程情况只初始化一次
        if (ProcessUtil.isCurMainProcess(getApplicationContext())) {

            if (Constant.DEBUG) {
                CommonUtil.showToast(this, "测试环境");
            }

            // 初始化日志类,如果不是调试状态则不输出日志
            Log.init(Constant.DEBUG);
            Log.i(TAG, "成功初始化LOG日志.");

            // 注册crashHandler
            if (!Constant.DEBUG) {
                CrashHandler crashHandler = CrashHandler.getInstance();
                crashHandler.init(this);
                Log.i(TAG, "成功初始化CrashHandler.");
            }

            // 初始化APP相关目录
            FileDataHelper.initDirectory();
            Log.i(TAG, "成功初始化APP相关目录.");

            // 保存当前网络状态(在每次网络通信时可能需要判断当前网络状态)
            setCurrentNetworkStatus(NetworkUtil.getCurrentNextworkState(this));
            Log.i(TAG, "保存当前网络状态:" + getCurrentNetworkStatus());
            //注册网络状态监听广播
            newConnectionReceiver();

            // 初始化OpenAPI
            OpenApi.init(Constant.DEBUG); // 设置OpenAPI的调试状态和App的Contant同步

            // 系统配置业务.
            ConfigManager.init(this);

            //初始环信
            HXChatManagerInit.getInstance().initEMChat(this);

            initUniversalImageLoader();

        }
    }

    //创建并注册网络状态监听广播
    private void newConnectionReceiver() {
        connectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setCurrentNetworkStatus(NetworkUtil.getCurrentNextworkState(context));
                if(!NetworkUtil.isLowMode(currentNetworkStatus)){
                    if(HXChatManagerInit.getInstance().isSyncingDatas){
                        if(!HXChatManagerInit.getInstance().isSyncingGroups){
                            HXChatManagerInit.getInstance().asyncFetchGroupsFromServer();
                            ArrayList<String> arrayList = new ArrayList<>(HXCacheUtils.getInstance().getGroupsHashMap().keySet());
                            EventBus.getDefault().post(new OnGroupsUpdataEvent(arrayList));
                        }
                        if(!HXChatManagerInit.getInstance().isSyncingUsers){
                            HXChatManagerInit.getInstance().asyncFetchUserFromServer();
                            ArrayList<String> arrayList = new ArrayList<>(HXCacheUtils.getInstance().getFriendsHashMap().keySet());
                            EventBus.getDefault().post(new OnContactUpdataEvent(arrayList));
                        }
                        if(!HXChatManagerInit.getInstance().isSyncingContact){
                            HXChatManagerInit.getInstance().asyncFetchContactsFromServer();
                        }
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, intentFilter);
    }

    //销毁网络状态监听广播
    private void unConnectionReceiver() {
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
            connectionReceiver = null;
        }
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void deleteActivity(Activity activity) {
        activities.remove(activity);
    }


    //finish
    public void exit() {
        for (int i = activities.size(); i > 0; i--) {
            int index = i - 1;
            Activity activity = activities.get(index);
            activity.finish();
        }
        activities.clear();
    }

    public void accountExit(){
        EMChatManager.getInstance().endCall();
        EMChatManager.getInstance().logout();
        SharedPreferencesHelper.setString(this, Constant.LoginUser.SHARED_PREFERENCES_PASSWORD, "");
    }

    /**
     * 设置图片加载配置
     */
    private void initUniversalImageLoader() {

//        "http://site.com/image.png" // from Web
//        "file:///mnt/sdcard/image.png" // from SD card
//        "file:///mnt/sdcard/video.mp4" // from SD card (video thumbnail)
//        "content://media/external/images/media/13" // from content provider
//        "content://media/external/video/media/13" // from content provider (video thumbnail)
//        "assets://image.png" // from assets
//        "drawable://" + R.drawable.img // from drawables (non-9patch images)

        optionsHead = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_head) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_head) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_head) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
//                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 构建完成

        optionsImage = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_image) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_image) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_image) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
//                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 构建完成

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                .diskCacheFileCount(100)  // 可以缓存的文件数量
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(optionsHead)
                .writeDebugLogs()
                .build(); //开始构建
        ImageLoader.getInstance().init(config);
    }


    @Override
    public void onTerminate() {
        unConnectionReceiver();
        ShareSDK.stopSDK();
        EMChatManager.getInstance().endCall();
        EMChatManager.getInstance().logout();
        HXChatManagerInit.getInstance().onDestroy();
        super.onTerminate();
    }
}
