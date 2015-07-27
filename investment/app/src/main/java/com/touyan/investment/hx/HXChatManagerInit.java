package com.touyan.investment.hx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.easemob.EMConnectionListener;
import com.easemob.chat.*;
import com.easemob.exceptions.EaseMobException;
import com.touyan.investment.App;
import com.touyan.investment.Constant;
import com.touyan.investment.event.ConnectionEventType;
import com.touyan.investment.event.ContactsListEventType;
import com.touyan.investment.event.GroupsListEventType;
import com.touyan.investment.event.OnContactDeletedEvent;
import de.greenrobot.event.EventBus;

import java.util.List;
import java.util.UUID;

/**
 * 环信监听初始
 *
 * @author bin.teng
 */
public class HXChatManagerInit {

    //是否同步成功  群组
    public boolean isSyncingGroups = false;
    //是否同步成功  会话
    public boolean isSyncingContact = false;

    private boolean isSyncingContactsWithServer = false;
    private boolean isSyncingGroupsWithServer = false;

    public static HXChatManagerInit instance;

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

        EMChat.getInstance().init(context);
        /**
         * debugMode == true 时为打开，sdk 会在log里输入调试信息
         * @param debugMode
         * 在做代码混淆的时候需要设置成false
         */
        EMChat.getInstance().setDebugMode(Constant.DEBUG);//在做打包混淆时，要关闭debug模式，如果未被关闭，则会出现程序无法运行问题

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
            EMConversation conversation = EMChatManager.getInstance().getConversation(username);
            // 如果是群聊消息，获取到group id
            if (message.getChatType() == EMMessage.ChatType.GroupChat) {
                username = message.getTo();
            }
            if (!username.equals(App.getInstance().getgUserInfo().getServno())) {
                // 消息不是发给当前会话，
                EventBus.getDefault().post(conversation);
            }
        }
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            asyncFetchContactsFromServer();
            asyncFetchGroupsFromServer();
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

    //同步群组

    /**
     * 同步操作，从服务器获取群组列表
     * 该方法会记录更新状态，可以通过isSyncingGroupsFromServer获取是否正在更新
     * 和HXPreferenceUtils.getInstance().getSettingSyncGroupsFinished()获取是否更新已经完成
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
                    EventBus.getDefault().post(new GroupsListEventType(groups));

                } catch (EaseMobException e) {
                    isSyncingGroups = false;
                    isSyncingGroupsWithServer = false;
                }

            }
        }.start();
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
            EventBus.getDefault().post(new OnContactDeletedEvent(usernameList));
        }

        @Override
        public void onContactInvited(String username, String reason) {
            // 接到邀请的消息，如果不处理(同意或拒绝)，掉线后，服务器会自动再发过来，所以客户端不要重复提醒

        }

        @Override
        public void onContactAgreed(String username) {
            //同意好友请求
        }

        @Override
        public void onContactRefused(String username) {
            // 拒绝好友请求

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
