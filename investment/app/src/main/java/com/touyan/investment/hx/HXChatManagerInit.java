package com.touyan.investment.hx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.core.util.StringUtil;
import com.dao.*;
import com.easemob.EMConnectionListener;
import com.easemob.chat.*;
import com.easemob.exceptions.EaseMobException;
import com.touyan.investment.App;
import com.touyan.investment.Constant;
import com.touyan.investment.bean.message.InviteMessage;
import com.touyan.investment.bean.user.User;
import com.touyan.investment.event.*;
import com.touyan.investment.helper.BeanCopyHelper;
import com.touyan.investment.helper.SharedPreferencesHelper;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 环信监听初始
 *
 * @author bin.teng
 */
public class HXChatManagerInit {

    private final static String TAG = HXChatManagerInit.class.getSimpleName();

    //是否需要同步数据
    public boolean isSyncingDatas = false;
    //是否同步成功  群组
    public boolean isSyncingGroups = false;
    //是否同步成功  好友
    public boolean isSyncingUsers = false;
    //是否同步成功  会话
    public boolean isSyncingContact = false;

    private boolean isSyncingContactsWithServer = false;
    private boolean isSyncingUserWithServer = false;
    private boolean isSyncingGroupsWithServer = false;

    public static HXChatManagerInit instance;

    public int unreadNoticeCount;

    private HXNotifier notifier;

    private Context mContext;

    private NewMessageBroadcastReceiver msgReceiver;

    public static HXChatManagerInit getInstance() {
        if (null == instance) {
            instance = new HXChatManagerInit();
        }
        return instance;
    }

    public void initEMChat(Context context) {
        mContext = context;

        unreadNoticeCount = SharedPreferencesHelper.getPreferInt(App.getInstance(), Constant.SHARED_PREFERENCES_DB_UNREADNOTICECOUNT, 0);

        EMChat.getInstance().init(context);
        /**
         * debugMode == true 时为打开，sdk 会在log里输入调试信息
         * @param debugMode
         * 在做代码混淆的时候需要设置成false
         */
        EMChat.getInstance().setDebugMode(Constant.DEBUG);//在做打包混淆时，要关闭debug模式，如果未被关闭，则会出现程序无法运行问题

        notifier = createNotifier();
        notifier.init(context);

        notifier.setNotificationInfoProvider(getNotificationListener());

        // 获取到EMChatOptions对象
        EMChatOptions options = EMChatManager.getInstance().getChatOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);
        // 默认环信是不维护好友关系列表的，如果app依赖环信的好友关系，把这个属性设置为true
        options.setUseRoster(true);

        EMChatManager.getInstance().getChatOptions().setUseRoster(true);//如果使用环信的好友体系需要先设置

        //只有注册了广播才能接收到新消息，目前离线消息，在线消息都是走接收消息的广播（离线消息目前无法监听，在登录以后，接收消息广播会执行一次拿到所有的离线消息）
        msgReceiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        mContext.registerReceiver(msgReceiver, intentFilter);

        //如果用到已读的回执需要把这个flag设置成true
        options.setRequireAck(true);

        // 设置从db初始化加载时, 每个conversation需要加载msg的个数
        options.setNumberOfMessagesLoaded(15);
        IntentFilter ackMessageIntentFilter = new IntentFilter(EMChatManager.getInstance().getAckMessageBroadcastAction());
        ackMessageIntentFilter.setPriority(3);
        mContext.registerReceiver(ackMessageReceiver, ackMessageIntentFilter);

        //监听联系人的变化等
        EMContactManager.getInstance().setContactListener(new MyContactListener());

        //注册一个监听连接状态的listener
        EMChatManager.getInstance().addConnectionListener(new MyConnectionListener());

        //注册群聊相关的listener
        EMGroupManager.getInstance().addGroupChangeListener(new MyGroupChangeListener());

        //设置notification点击listener
        options.setOnNotificationClickListener(new OnNotificationClickListener() {

            @Override
            public Intent onNotificationClick(EMMessage message) {
//                Intent intent = new Intent(applicationContext, ChatActivity.class);
//                EMMessage.ChatType chatType = message.getChatType();
//                if (chatType == EMMessage.ChatType.Chat) { //单聊信息
//                    intent.putExtra("userId", message.getFrom());
//                    intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
//                } else { //群聊信息
//                    //message.getTo()为群聊id
//                    intent.putExtra("groupId", message.getTo());
//                    intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
//                }
//                return intent;
                return null;
            }
        });


        //注：最后要通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了
        EMChat.getInstance().setAppInited();
    }


    /**
     * subclass can override this api to return the customer notifier
     *
     * @return
     */
    protected HXNotifier createNotifier() {
        return new HXNotifier();
    }

    protected HXNotifier.HXNotificationInfoProvider getNotificationListener() {
        return null;
    }

    private class NewMessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 注销广播
            abortBroadcast();

            // 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
            String msgId = intent.getStringExtra("msgid");
            //发送方
            String username = intent.getStringExtra("from");
            // 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
            EMMessage message = EMChatManager.getInstance().getMessage(msgId);
            // 如果是群聊消息，获取到group id
            if (message.getChatType() == EMMessage.ChatType.GroupChat) {
                username = message.getTo();
            }
            if (!username.equals(App.getInstance().getgUserInfo().getServno())) {
                // 消息不是发给当前会话，
                EventBus.getDefault().post(new NewMessageEvent());
            }
        }
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            asyncFetchContactsFromServer();
            dbDataProcess();
            asyUnreadNotice();
        }

        @Override
        public void onDisconnected(final int error) {
            EventBus.getDefault().post(new ConnectionEventType(error));
        }
    }

    //同步会话
    public void asyncFetchContactsFromServer() {
        if (isSyncingContactsWithServer) {
            return;
        }

        isSyncingContactsWithServer = true;

        new Thread() {
            @Override
            public void run() {
                List<String> usernames = null;
                try {
                    usernames = EMContactManager.getInstance().getContactUserNames();
                    // in case that logout already before server returns, we should return immediately
                    if (!EMChat.getInstance().isLoggedIn()) {
                        return;
                    }
                    isSyncingContact = true;
                    isSyncingContactsWithServer = false;
                    EventBus.getDefault().post(new ContactsListEventType(usernames));
                } catch (EaseMobException e) {
                    isSyncingContact = false;
                    isSyncingContactsWithServer = false;
                    e.printStackTrace();
                }

            }
        }.start();
    }


    private void asyUnreadNotice() {
        InviteMessageDao inviteMessageDao = App.getDaoSession().getInviteMessageDao();
        List<InviteMessageDO> dos = inviteMessageDao.queryBuilder().list();
        HashMap<String, InviteMessage> tempHashMap = new HashMap<>();
        if (null != dos) {
            for (InviteMessageDO messageDO : dos) {
                InviteMessage inviteMessage = BeanCopyHelper.cast2InviteMessage(messageDO);
                tempHashMap.put(inviteMessage.getFrom(), inviteMessage);
            }
            HXCacheUtils.getInstance().setInviteMessageHashMap(tempHashMap);
        }
    }


    public void asyncData() {
        asyncFetchGroupsFromServer();
        asyncFetchUserFromServer();
    }

    private void dbDataProcess() {
        long dbtmep = SharedPreferencesHelper.getLong(App.getInstance(), Constant.SHARED_PREFERENCES_DB_TIME, -1);
        if (dbtmep < 0) {
            isSyncingDatas = true;
            SharedPreferencesHelper.setLong(App.getInstance(), Constant.SHARED_PREFERENCES_DB_TIME, System.currentTimeMillis());
            clearConfig();
            HXCacheUtils.getInstance().resetData();
            asyncData();
        } else {
            long currenttime = System.currentTimeMillis();
            boolean tempoffer = (currenttime - dbtmep) > Constant.DB_TIME;
            if (tempoffer) {
                isSyncingDatas = true;
                SharedPreferencesHelper.setLong(App.getInstance(), Constant.SHARED_PREFERENCES_DB_TIME, System.currentTimeMillis());
                clearConfig();
                HXCacheUtils.getInstance().resetData();
                asyncData();
            } else {
                isSyncingDatas = false;
                // 从数据库中加载好友&群列表
                UserDao userDao = App.getDaoSession().getUserDao();

                List<UserDO> users = userDao.queryBuilder().list();

                HashMap<String, User> friendsHashMap = new HashMap<>();

                HashMap<String, User> groupsHashMap = new HashMap<>();

                for (UserDO userdo : users) {
                    User user = new User();
                    user.setAvatar(userdo.getAvatar());
                    user.setHeader(userdo.getHeader());
                    user.setUnreadMsgCount(userdo.getUnreadMsgCount());
                    String type = userdo.getType();
                    user.setType(type);
                    if (User.TYPE_FRIENDS.equals(type)) {
                        friendsHashMap.put(userdo.getAvatar(), user);
                    } else {
                        groupsHashMap.put(userdo.getAvatar(), user);
                    }
                }

                HXCacheUtils.getInstance().setFriendsHashMap(friendsHashMap);
                HXCacheUtils.getInstance().setGroupsHashMap(groupsHashMap);

            }
        }
    }

    /**
     * 清除缓存
     */
    public static void clearConfig() {
        DaoSession daoSession = App.getDaoSession();
        daoSession.getUserInfoDao().deleteAll();
        daoSession.getGroupDetalDao().deleteAll();
        daoSession.getUserDao().deleteAll();
    }

    //同步群组

    /**
     * 同步操作，从服务器获取群组列表
     *
     * @throws EaseMobException
     */
    public synchronized void asyncFetchGroupsFromServer() {
        if (isSyncingGroupsWithServer) {
            return;
        }

        isSyncingGroupsWithServer = true;

        new Thread() {
            @Override
            public void run() {
                List<EMGroup> groups;
                try {
                    groups = EMGroupManager.getInstance().getGroupsFromServer();

                    // in case that logout already before server returns, we should return immediately
                    if (!EMChat.getInstance().isLoggedIn()) {
                        return;
                    }
                    isSyncingGroups = true;
                    isSyncingGroupsWithServer = false;
                    saveGroupList(groups);
                } catch (EaseMobException e) {
                    isSyncingGroups = false;
                    isSyncingGroupsWithServer = false;
                }

            }
        }.start();
    }


    //本地&内存 保存群组

    private void saveGroupList(List<EMGroup> groups) {
        if (null != groups && groups.size() > 0) {
            DaoSession daoSession = App.getDaoSession();
            List<UserDO> userDOs = new ArrayList<>();
            HashMap<String, User> groupsHashMap = new HashMap<>();
            for (EMGroup group : groups) {
                UserDO userdo = new UserDO();
                User user = new User();
                userdo.setType(User.TYPE_GROUPS);
                user.setType(User.TYPE_GROUPS);
                String username = group.getGroupId();
                userdo.setAvatar(username);
                user.setAvatar(username);
                userDOs.add(userdo);
                groupsHashMap.put(username, user);
            }
            HXCacheUtils.getInstance().getGroupsHashMap().putAll(groupsHashMap);
            daoSession.getUserDao().insertInTx(userDOs);
        }
    }

    private void saveGroupList(EMGroup group) {
        if (null != group) {
            DaoSession daoSession = App.getDaoSession();
            UserDO userdo = new UserDO();
            User user = new User();
            userdo.setType(User.TYPE_GROUPS);
            user.setType(User.TYPE_GROUPS);
            String username = group.getGroupId();
            userdo.setAvatar(username);
            user.setAvatar(username);
            HXCacheUtils.getInstance().getGroupsHashMap().put(username, user);
            daoSession.getUserDao().insert(userdo);
        }
    }

    //本地&内存 删除群组
    private void removeGroupList(List<EMGroup> groups) {
        if (null != groups && groups.size() > 0) {
            final DaoSession daoSession = App.getDaoSession();
            final List<String> id = new ArrayList<>();
            for (EMGroup group : groups) {
                String groupid = group.getGroupId();
                id.add(groupid);
                HXCacheUtils.getInstance().getGroupsHashMap().remove(groupid);
            }
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    WhereCondition wc = UserDao.Properties.Avatar.in(id);
                    List<UserDO> chatEntityList = daoSession.getUserDao().queryBuilder().where(wc).list();
                    daoSession.getUserDao().deleteInTx(chatEntityList);
                }
            });
        }
    }


    /**
     * 同步操作，从服务器获取好友列表
     *
     * @throws EaseMobException
     */
    public synchronized void asyncFetchUserFromServer() {
        if (isSyncingUserWithServer) {
            return;
        }

        isSyncingUserWithServer = true;

        new Thread() {
            @Override
            public void run() {
                List<String> usernames;
                try {

                    usernames = EMContactManager.getInstance().getContactUserNames();

                    // in case that logout already before server returns, we should return immediately
                    if (!EMChat.getInstance().isLoggedIn()) {
                        return;
                    }
                    isSyncingUsers = true;
                    isSyncingUserWithServer = false;

                    saveUserList(usernames);

                } catch (EaseMobException e) {
                    isSyncingUsers = false;
                    isSyncingUserWithServer = false;
                }

            }
        }.start();
    }


    //本地&内存 保存用户
    private void saveUserList(List<String> usernames) {
        if (null != usernames && usernames.size() > 0) {
            DaoSession daoSession = App.getDaoSession();
            List<UserDO> userDOs = new ArrayList<>();
            HashMap<String, User> friendsHashMap = new HashMap<>();
            for (String username : usernames) {
                UserDO userdo = new UserDO();
                User user = new User();
                userdo.setType(User.TYPE_FRIENDS);
                user.setType(User.TYPE_FRIENDS);
                userdo.setAvatar(username);
                user.setAvatar(username);
                userDOs.add(userdo);
                friendsHashMap.put(username, user);
            }
            HXCacheUtils.getInstance().getFriendsHashMap().putAll(friendsHashMap);
            ArrayList<String> arrayList = new ArrayList<>(HXCacheUtils.getInstance().getFriendsHashMap().keySet());
            EventBus.getDefault().post(new OnContactUpdataEvent(arrayList));
            daoSession.getUserDao().insertInTx(userDOs);
        }
    }

    private void saveUserList(String username) {
        if (StringUtil.isNotBlank(username)) {
            DaoSession daoSession = App.getDaoSession();
            UserDO userdo = new UserDO();
            User user = new User();
            userdo.setType(User.TYPE_FRIENDS);
            user.setType(User.TYPE_FRIENDS);
            userdo.setAvatar(username);
            user.setAvatar(username);
            HXCacheUtils.getInstance().getFriendsHashMap().put(username, user);
            ArrayList<String> arrayList = new ArrayList<>(HXCacheUtils.getInstance().getFriendsHashMap().keySet());
            EventBus.getDefault().post(new OnContactUpdataEvent(arrayList));
            daoSession.getUserDao().insert(userdo);
        }
    }

    //本地&内存 删除用户
    private void removeUserList(final List<String> usernames) {
        if (null != usernames && usernames.size() > 0) {
            final DaoSession daoSession = App.getDaoSession();
            for (String username : usernames) {
                HXCacheUtils.getInstance().getFriendsHashMap().remove(username);
            }
            ArrayList<String> arrayList = new ArrayList<>(HXCacheUtils.getInstance().getFriendsHashMap().keySet());
            EventBus.getDefault().post(new OnContactUpdataEvent(arrayList));
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    WhereCondition wc = UserDao.Properties.Avatar.in(usernames);
                    List<UserDO> chatEntityList = daoSession.getUserDao().queryBuilder().where(wc).list();
                    daoSession.getUserDao().deleteInTx(chatEntityList);
                }
            });
        }
    }


    private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            abortBroadcast();
            String msgid = intent.getStringExtra("msgid");
            String from = intent.getStringExtra("from");
            EMConversation conversation = EMChatManager.getInstance().getConversation(from);
            if (conversation != null) {
                // 把message设为已读
                EMMessage msg = conversation.getMessage(msgid);
                if (msg != null) {
                    msg.isAcked = true;
                }
            }

        }
    };

    private class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(List<String> usernameList) {
            // 保存增加的联系人
        }

        @Override
        public void onContactDeleted(final List<String> usernameList) {
            // 被删除
            Log.d(TAG, "您删除了好友" + usernameList.get(0));
            removeUserList(usernameList);
        }

        @Override
        public void onContactInvited(String username, String reason) {
            // 接到邀请的消息，如果不处理(同意或拒绝)，掉线后，服务器会自动再发过来，所以客户端不要重复提醒
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            Log.d(TAG, username + "接到邀请的消息");
            msg.setReason(reason);
            msg.setUnreadCount(1);
            msg.setStatus(InviteMessage.InviteMesageStatus.BEINVITEED);
            notifyNewIviteMessage(msg, false);
        }

        @Override
        public void onContactAgreed(String username) {
            //同意好友请求
            saveUserList(username);
            // 自己封装的javabean
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            msg.setUnreadCount(1);
            Log.d(TAG, username + "同意了你的好友请求");
            msg.setReason("同意了你的好友请求");
            msg.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
            notifyNewIviteMessage(msg);

        }

        @Override
        public void onContactRefused(String username) {
            // 拒绝好友请求
            // 自己封装的javabean
            InviteMessage msg = new InviteMessage();
            msg.setFrom(username);
            msg.setTime(System.currentTimeMillis());
            Log.d(TAG, username + "拒绝了你的好友请求");
            msg.setReason("拒绝了你的好友请求");
            msg.setUnreadCount(1);
            msg.setStatus(InviteMessage.InviteMesageStatus.BEREFUSED);
            notifyNewIviteMessage(msg);
        }


    }

    /**
     * 保存提示新消息
     * <p/>
     * isSave 是否是需要储存的数据  true 为需要储存数据 默认true
     *
     * @param msg
     */
    private void notifyNewIviteMessage(InviteMessage msg, boolean isSave) {
        unreadNoticeCount++;
        if (isSave) {
            saveInviteMsg(msg);
            SharedPreferencesHelper.setPreferInt(App.getInstance(), Constant.SHARED_PREFERENCES_DB_UNREADNOTICECOUNT, unreadNoticeCount);
        }
        // 提示有新消息
        notifier.viberateAndPlayTone(null);
        // 刷新bottom bar消息未读数 & 通知未读通知
        EventBus.getDefault().post(new NewMessageEvent());

    }

    private void notifyNewIviteMessage(InviteMessage msg) {
        notifyNewIviteMessage(msg, true);
    }

    /**
     * 保存邀请等msg
     *
     * @param msg
     */
    private void saveInviteMsg(InviteMessage msg) {
        if (null != msg) {
            DaoSession daoSession = App.getDaoSession();
            String title = msg.getFrom();
            InviteMessageDO inviteMessageDO = BeanCopyHelper.cast2InviteMessageDO(msg);
            HXCacheUtils.getInstance().getInviteMessageHashMap().put(title, msg);
            daoSession.getInviteMessageDao().insert(inviteMessageDO);
        }
    }

    private class MyGroupChangeListener implements GroupChangeListener {

        @Override
        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {

            //收到加入群聊的邀请

            boolean hasGroup = false;
            for (EMGroup group : EMGroupManager.getInstance().getAllGroups()) {
                if (group.getGroupId().equals(groupId)) {
                    hasGroup = true;
                    break;
                }
            }
            if (!hasGroup)
                return;

            // 被邀请
            EMMessage msg = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
            msg.setChatType(EMMessage.ChatType.GroupChat);
            msg.setFrom(inviter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new TextMessageBody(inviter + "邀请你加入了群聊"));
            // 保存邀请消息
            EMChatManager.getInstance().saveMessage(msg);
            // 提醒新消息
            EMNotifier.getInstance(App.getInstance()).notifyOnNewMsg();

            // 刷新bottom bar消息未读数 & 通知未读通知
            EventBus.getDefault().post(new NewMessageEvent());
        }

        @Override
        public void onInvitationAccpted(String groupId, String inviter,
                                        String reason) {
            //群聊邀请被接受
        }

        @Override
        public void onInvitationDeclined(String groupId, String invitee,
                                         String reason) {
            //群聊邀请被拒绝
        }

        @Override
        public void onUserRemoved(String groupId, String groupName) {
            //当前用户被管理员移除出群聊

        }

        @Override
        public void onGroupDestroy(String groupId, String groupName) {
            //群聊被创建者解散
            // 提示用户群被解散

        }

        @Override
        public void onApplicationReceived(String groupId, String groupName, String applyer, String reason) {
            // 用户申请加入群聊，收到加群申请

        }

        @Override
        public void onApplicationAccept(String groupId, String groupName, String accepter) {
            // 加群申请被同意
            EMMessage msg = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
            msg.setChatType(EMMessage.ChatType.GroupChat);
            msg.setFrom(accepter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new TextMessageBody(accepter + "同意了你的群聊申请"));
            // 保存同意消息
            EMChatManager.getInstance().saveMessage(msg);
            // 提醒新消息
            EMNotifier.getInstance(App.getInstance()).notifyOnNewMsg();

            // 刷新bottom bar消息未读数 & 通知未读通知
            EventBus.getDefault().post(new NewMessageEvent());
        }

        @Override
        public void onApplicationDeclined(String groupId, String groupName, String decliner, String reason) {
            // 加群申请被拒绝
        }

    }

    public void onDestroy() {
        if (null != msgReceiver) {
            mContext.unregisterReceiver(msgReceiver);
        }
        if (null != ackMessageReceiver) {
            mContext.unregisterReceiver(ackMessageReceiver);
        }
    }

}
