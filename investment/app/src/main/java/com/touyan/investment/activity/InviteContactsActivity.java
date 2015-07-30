package com.touyan.investment.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.core.util.Log;
import com.core.util.StringUtil;
import com.handmark.pulltorefresh.PullToRefreshBase;
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;
import com.nineoldandroids.animation.ValueAnimator;
import com.touyan.investment.AbsActivity;
import com.touyan.investment.R;
import com.touyan.investment.adapter.ContactFriendListHeadersAdapter;
import com.touyan.investment.bean.message.ContactFriend;
import com.touyan.investment.bean.message.QueryContactFriendsResult;
import com.touyan.investment.bean.user.Subscriber;
import com.touyan.investment.bean.user.UserInfo;
import com.touyan.investment.helper.ContactFriendComp;
import com.touyan.investment.manager.MessageManager;
import com.touyan.investment.mview.IndexableListView;
import com.touyan.investment.mview.PullToRefreshIndexableListView;

import java.util.*;

/**
 * Created by Administrator on 2015/7/27.
 */
public class InviteContactsActivity extends AbsActivity {

    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int PHONES_NUMBER_INDEX = 1;
    private static final int PHONES_PHOTO_ID_INDEX = 2;
    private static final int PHONES_CONTACT_ID_INDEX = 3;

    private static final int FRIEND_DATA = 0x03;//加载数据处理

    private float EDITEXT_OFFER; //搜索 动画偏移量


    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.ptflistview)
    PullToRefreshIndexableListView ptflistview;
    @Bind(R.id.loading_message)
    TextView loadingMessage;
    @Bind(R.id.ll_listEmpty)
    LinearLayout llListEmpty;

    IndexableListView listview;

    private Comparator cmp = new ContactFriendComp();

    private ArrayList<ContactFriend> contacts;

    private ArrayList<ContactFriend> contactSearch;

    private ArrayList<ContactFriend> mContacts;

    private ArrayList<String> usernames = null;

    private ContactFriendListHeadersAdapter mAdapter;

    private final OvershootInterpolator mInterpolator = new OvershootInterpolator();

    private boolean isShowCancel = false;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {

                case FRIEND_DATA:
                    loadFriendData((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadFriendData(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            QueryContactFriendsResult result = (QueryContactFriendsResult) resposne.getData();
            contacts = result.getRelton();

            for (int i = 0; i < mContacts.size(); i++) {
                if (contacts.contains(mContacts.get(i))) {
                    if (contacts.get(contacts.indexOf(mContacts.get(i))).getUserinfo() == null) {
                        contacts.set(contacts.indexOf(mContacts.get(i)), mContacts.get(i));
                    } else {
                        continue;
                    }
                }
            }

            hanziSequence();
            mAdapter.refresh(contacts);
        } else {
            CommonUtil.showToast(resposne.getErrorTip());
        }
        ptflistview.onRefreshComplete();
    }

    private void hanziSequence() {
        if (contacts == null) {
            return;
        }
        Collections.sort(contacts, cmp);
        initListView();
    }

    @OnTextChanged(R.id.search_et)
    void onTextChanged(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            searchData(charSequence.toString());
        } else {
            mAdapter.refresh(contacts);
        }

    }

    private void searchData(String sear) {
        contactSearch = new ArrayList<>();
        for (ContactFriend bean : contacts) {
            String name = bean.getUserinfo().getUalias();
            boolean is = name.length() > sear.length();
            if (is) {
                if (name.contains(sear)) {
                    contactSearch.add(bean);
                }
            } else {
                if (sear.contains(name)) {
                    contactSearch.add(bean);
                }
            }
        }
        mAdapter.refresh(contactSearch);
    }

    @OnFocusChange(R.id.search_et)
    void onFocusChanged(boolean focused) {
        if (focused) {
            if (!isShowCancel) {
                editTextAni(true);
                isShowCancel = true;
            }
        } else {

        }
    }

    // 设置输入框的动画
    private void editTextAni(final boolean is) {
        ValueAnimator animation = ValueAnimator.ofFloat(is ? 0 : EDITEXT_OFFER, is ? EDITEXT_OFFER : 0);
        if (is) {
            animation.setStartDelay(400);
        }
        animation.setDuration(400);
        animation.setInterpolator(mInterpolator);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) searchEt.getLayoutParams();
                int margin = (int) value;
                lp.setMargins(margin, margin, margin, 0);
                searchEt.setLayoutParams(lp);
            }
        });
        animation.start();
    }

    @Override
    public void EInit() {
        super.EInit();
        setSwipeBackEnable(true);
        initPTFListView();
        findView();
        init();
    }

    private void init() {
        EDITEXT_OFFER = getResources().getDimension(R.dimen.content_gap);
        dialogShow();
        getDataList(this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_invite_contacts;
    }

    @Override
    public void initActionBar() {
        setToolbarLeftStrID(R.string.back);
        setToolbarIntermediate("通讯录");
        setToolbarRightVisbility(View.INVISIBLE, View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initPTFListView() {

        ptflistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<IndexableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<IndexableListView> refreshView) {
                contacts = null;
                getDataList(InviteContactsActivity.this);
            }
        });

    }

    private void findView() {
        listview = ptflistview.getRefreshableView();
        listview.setFastScrollEnabled(true);
        listview.setEmptyView(llListEmpty);
    }

    private void initListView() {

        mAdapter = new ContactFriendListHeadersAdapter(this, contacts);

        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(mAdapter);

        StickyListHeadersAdapterDecorator stickyListHeadersAdapterDecorator = new StickyListHeadersAdapterDecorator(animationAdapter);
        stickyListHeadersAdapterDecorator.setListViewWrapper(new StickyListHeadersListViewWrapper(listview));

        assert animationAdapter.getViewAnimator() != null;
        animationAdapter.getViewAnimator().setInitialDelayMillis(500);

        assert stickyListHeadersAdapterDecorator.getViewAnimator() != null;
        stickyListHeadersAdapterDecorator.getViewAnimator().setInitialDelayMillis(500);

        listview.setAdapter(stickyListHeadersAdapterDecorator);
    }


    private void getDataList(Context mContext) {
        activityHandler.post(new Runnable() {
            @Override
            public void run() {
                usernames = getContactsPhoneList(InviteContactsActivity.this);
                MessageManager manager = new MessageManager();
                manager.queryContactFriends(InviteContactsActivity.this, usernames, activityHandler, FRIEND_DATA);
            }
        });


    }

    private ArrayList<String> getContactsPhoneList(Context mContext) {

        ContentResolver resolver = mContext.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        ArrayList<String> phones = new ArrayList<>();
        mContacts = new ArrayList<>();

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);

                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                String phoneStr = phoneNumber.replaceAll("-", "");
                phoneStr = phoneStr.replaceAll(" ", "");

                if (StringUtil.checkMobileWithCountry(phoneStr)) {
                    if (phoneStr.contains("+86")) {
                        phoneStr = phoneStr.replaceAll("\\+86", "");
                    }
                    phones.add(phoneStr);
                } else {
                    continue;
                }


                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                //得到联系人ID
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

                //得到联系人头像ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                String imagPath = "";

                //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
                    imagPath = uri.toString();
                } else {
                    imagPath = "drawable://" + R.drawable.default_head;
                }


                ContactFriend contactFriend = new ContactFriend();
                UserInfo userInfo = new UserInfo();
                userInfo.setServno(phoneStr);
                userInfo.setUalias(contactName);
                userInfo.setUphoto(imagPath);
                contactFriend.setUserinfo(userInfo);
                contactFriend.setRole(2);
                contactFriend.setServno(phoneStr);
                Log.e("zxh", contactFriend.toString());

                mContacts.add(contactFriend);

            }
            phoneCursor.close();
        }
        return phones;
    }

    @Override
    public void onBackPressed() {
        if (isShowCancel) {
            editTextAni(false);
            isShowCancel = false;
            searchEt.clearFocus();
            searchEt.setText("");
            return;
        }
        super.onBackPressed();
    }

}

