package com.touyan.investment;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import com.core.CrashHandler;
import com.core.enums.ConfigKeyEnum;
import com.core.manager.ConfigManager;
import com.core.openapi.OpenApi;
import com.core.util.*;
import com.core.util.NetworkUtil.NetworkClassEnum;
import com.dao.DaoMaster;
import com.dao.DaoMaster.OpenHelper;
import com.dao.DaoSession;
import com.touyan.investment.bean.user.UserInfo;

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

    private static App instance;

    private UserInfo gUserInfo;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;

    private BroadcastReceiver connectionReceiver;

    /**
     * 获得本类的一个实例
     */
    public static App getInstance() {
        return instance;
    }


    public UserInfo getgUserInfo() {
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

        }
    }

    //创建并注册网络状态监听广播
    private void newConnectionReceiver() {
        connectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setCurrentNetworkStatus(NetworkUtil.getCurrentNextworkState(context));
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

    @Override
    public void onTerminate() {
        unConnectionReceiver();
        super.onTerminate();
    }
}
