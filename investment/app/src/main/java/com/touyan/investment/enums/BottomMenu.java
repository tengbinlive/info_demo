package com.touyan.investment.enums;

import com.touyan.investment.R;

/**
 * 底部菜单枚举类.
 *
 * @author
 */
public enum BottomMenu {

    //    主界面
    //投研社
    INVESTMENT(R.string.main_investment,R.string.main_investment, R.drawable.main_investment_press, R.drawable.main_investment_normal, 0xfff12e40, 0xff808080),
    //荐股
    RECOMMEND(R.string.main_recommend,R.string.main_recommend, R.drawable.main_recommend_press, R.drawable.main_recommend_normal, 0xfff12e40, 0xff808080),
    //啦呱
    GUNG(R.string.main_gung,R.string.main_gung, R.drawable.main_gung_press, R.drawable.main_gung_normal, 0xfff12e40, 0xff808080),
    //我
    ME(R.string.main_me, R.string.main_me,R.drawable.main_me_press, R.drawable.main_me_normal, 0xfff12e40, 0xff808080),

    //	投研社
    //转发
    SHARE(R.string.bottom_menu_share,R.string.bottom_menu_share, R.drawable.share_press, R.drawable.share_noramal, 0xfff12e40, 0xff808080),
    //评论
    REVIEW(R.string.bottom_menu_review,R.string.bottom_menu_review, R.drawable.review_press, R.drawable.review_normal, 0xfff12e40, 0xff808080),
    //收藏
    COLLECT(R.string.bottom_menu_collect,R.string.bottom_menu_collect, R.drawable.collect_press, R.drawable.collect_normal, 0xfff12e40, 0xff808080),
    //打赏
    REWARD(R.string.bottom_menu_reward,R.string.bottom_menu_reward, R.drawable.reward_press, R.drawable.reward_normal, 0xfff12e40, 0xff808080),
    //报名
    SIGN(R.string.bottom_menu_unsign, R.string.bottom_menu_sign,R.drawable.sign_press, R.drawable.sign_normal, 0xfff12e40, 0xff808080),
    //回答
    REPLY(R.string.bottom_menu_reply,R.string.bottom_menu_reply, R.drawable.reply_press, R.drawable.reply_normal, 0xfff12e40, 0xff808080);

    private int title;
    private int title_press;
    private int resid_press;
    private int resid_normal;
    private int title_colos_press;
    private int title_colos_normal;

    BottomMenu(int title,int title_press ,int resid_press, int resid_normal, int title_colos_press, int title_colos_normal) {
        this.title = title;
        this.title_press = title_press;
        this.resid_press = resid_press;
        this.resid_normal = resid_normal;
        this.title_colos_press = title_colos_press;
        this.title_colos_normal = title_colos_normal;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getTitle_press() {
        return title_press;
    }

    public void setTitle_press(int title_press) {
        this.title_press = title_press;
    }

    public int getResid_press() {
        return resid_press;
    }

    public void setResid_press(int resid_press) {
        this.resid_press = resid_press;
    }

    public int getResid_normal() {
        return resid_normal;
    }

    public void setResid_normal(int resid_normal) {
        this.resid_normal = resid_normal;
    }

    public int getTitle_colos_press() {
        return title_colos_press;
    }

    public void setTitle_colos_press(int title_colos_press) {
        this.title_colos_press = title_colos_press;
    }

    public int getTitle_colos_normal() {
        return title_colos_normal;
    }

    public void setTitle_colos_normal(int title_colos_normal) {
        this.title_colos_normal = title_colos_normal;
    }

    @Override
    public String toString() {
        return "BottomMenu{" +
                "title=" + title +
                ", title_press=" + title_press +
                ", resid_press=" + resid_press +
                ", resid_normal=" + resid_normal +
                ", title_colos_press=" + title_colos_press +
                ", title_colos_normal=" + title_colos_normal +
                '}';
    }
}
