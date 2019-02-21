package com.example.administrator.sharenebulaproject.global;

import android.os.Bundle;

import com.example.administrator.sharenebulaproject.model.DataManager;
import com.example.administrator.sharenebulaproject.model.db.entity.LoginUserInfo;

/**
 * 作者：真理 Created by Administrator on 2019/2/21.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class Persistence {


    public void PersistenceStorage(Bundle savedInstanceState, DataManager dataManager) {

        DataClass.URL = "http://yuedeyi.douy18.com/api/";
        DataClass.URL_ = "http://yuedeyi.douy18.com/";
        DataClass.APK_URL = "http://192.168.2.197:8080/data/yuedeyi.apk";

        //媒体文件前缀
//    DataClass. FileUrl = "http://xfx.027perfect.com/";
        DataClass.FileUrl = "http://yuedeyi.douy18.com/";

        DataClass.BASE_URL = DataClass.URL + "api.mingfa.php?version=v1&vars=" + "{" + "\"" + "action" + "\"" + ":" + "\"" + "image_save_set" + "\"" + "}";

        DataClass.MESSAGE_URL = DataClass.URL_ + "pf_wx.php?act=home&do=messagelist&uid=";

        DataClass.DAILY_URL = DataClass.URL_ + "pf_wx.php?act=oneday&do=detail&newsid=";

        DataClass.SELECT = DataClass.URL_ + "pf_wx.php?act=oneday&do=display&userid_share=";

        DataClass.LOGIN_ = 1;//登陆
        DataClass.TRADE_PASSWORD = 2;//交易密码设置
        DataClass.QQ_LOGIN = 4;//qq登陆绑定手机
        DataClass.WECHAT_LOGIN = 5;//微信登陆绑定手机
        DataClass.OLDPHONE = 6;//修改绑定的手机号-旧手机号验证
        DataClass.NEWPHONE = 7;//修改绑定的手机号-新手机号验证

        DataClass.LOGINTYPE = 1;//登陆类型 1/手机登陆  2/qq登陆  3/wechat登陆

        DataClass.INFOTYPE_USE_AGREEMENT = 2; //使用协议
        DataClass.AWARDS_SHOW = 3; //奖励说明
        DataClass.HELP_CENTER = 6; //帮助中心
        DataClass.PROMOTION_COOPERATION = 7; //推广合作说明
        DataClass.HIERARCHY = 8; //等级制度
        DataClass.CONTACT_CUSTOMER_SERVICE = 9; //联系客服
        DataClass.BILLING_INSTRUCTIONS = 13; //结算说明

        DataClass.WINDOWS_WIDTH = 0;
        DataClass.WINDOWS_HEIGHT = 0;
        DataClass.PX_WINDOWS_WIDTH = 0;
        DataClass.PX_WINDOWS_HEIGHT = 0;

        LoginUserInfo loginUserInfo = dataManager.queryLoginUserInfo("admin");

        //用户登陆ID
        if (loginUserInfo!=null){
            DataClass.USERID = loginUserInfo.getUserid();
            DataClass.SELECT = DataClass.SELECT + loginUserInfo.getUserid();
        }

        //用户分享后缀
        //登陆
        DataClass.LOGIN = "user_login_get";
        //获取验证码
        DataClass.USERID_SHARE = "&userid_share=" + DataClass.USERID;
        DataClass.GET_CODE = "get_code";
        //获取首页数据
        DataClass.HOMEPAGE_GET = "homepage_get";
        //我的
        DataClass.MY_CENTER_GET = "my_center_get";
        //个人资料修改
        DataClass.USER_INFO_SET = "user_info_set";
        //图片上传
        DataClass.IMAGE_SAVE_SET = "image_save_set";
        // 头像保存地址
        DataClass.IMAGESAVEGET = DataClass.BASE_URL + "{" + "\"" + "action" + "\"" + ":" + "\"" + "image_save_set" + "\"" + "}";
        //实名认证
        DataClass.REALNAME_INFO_SET = "realname_info_set";
        //交易密码（修改交易密码）
        DataClass.TRANSACTION_PWD_SET = "transaction_pwd_set";
        //绑定新手机号
        DataClass.NEW_PHONE_SET = "new_phone_set";
        //绑定手机号
        DataClass.PHONE_BIND_SET = "phone_bind_set";
        //获取VIP升级信息
        DataClass.UPVIP_INIT_GET = "upvip_init_get";
        //会员权益
        DataClass.UPVIP_REMARK_GET = "upvip_remark_get";
        //升级会员
        DataClass.SERVICE_OPT_GETORDER_SET = "service_opt_getorder_set";
        //结算信息
        DataClass.SETTLEACCOUNTS_INIT_GET = "settleaccounts_init_get";
        //结算
        DataClass.SETTLEACCOUNTS_SET = "settleaccounts_set";
        //结算记录
        DataClass.SETTLEACCOUNTS_LIST_GET = "settleaccounts_list_get";
        //我的等级
        DataClass.MYVIP_DETAIL_GET = "myvip_detail_get";
        //我的粉丝
        DataClass.FANS_TJ_GET = "fans_tj_get";
        //粉丝列表
        DataClass.FANS_LIST_GET = "fans_list_get";
        //说明类容
        DataClass.TEXTINFO_GET = "textinfo_get";
        //推广合作
        DataClass.COOPERATION_SET = "cooperation_set";
        //收入信息
        DataClass.MONEY_IN_GET = "money_in_get";
        //收入权益
        DataClass.MY_INTERESTS_GET = "my_interests_get";
        //我的分享
        DataClass.MYSHARE_LIST_GET = "myshare_list_get";
        //收入明细
        DataClass.MONEY_IN_LIST_GET = "money_in_list_get";

        //获取结算账户列表
        DataClass.MONEY_ACCOUNT_LIST_GET = "money_account_list_get";
        //提交绑定
        DataClass.MONEY_ACCOUNT_SET = "money_account_set";
        //解除绑定
        DataClass.MONEY_ACCOUNT_DELETE_SET = "money_account_delete_set";
        //每日精选
        DataClass.TODAY_NEWS_LIST_GET = "today_news_list_get";
        //文章详情
        DataClass.NEWS_DETAIL_GET = "news_detail_get";
        //达人榜
        DataClass.TALENT_GET = "talent_get";
        //获取当前个人信息
        DataClass.USER_DETAIL_GET = "user_detail_get";
        //分享成功回调
        DataClass.AFTERSHARE_SET = "aftershare_set";
        //banner首页详情
        DataClass.TEXTINFO_DETAIL_GET = "textinfo_detail_get";
        //获取海报图
        DataClass.POSTER_GET = "poster_get";
        //微信绑定
        DataClass.WEIXIN_SET = "weixin_set";
        //邀请码提交
        DataClass.BIND_INVITATIONCODE = "bind_invitationcode";
        //新闻分类
        DataClass.CATE_GET = "cate_get";
        //新闻分类顺序排列
        DataClass.CATE_SORT_SET = "cate_sort_set";
        //点赞
        DataClass.ZAN_SET = "zan_set";
        //发送评论
        DataClass.COMMENT_SET = "comment_set";


    }

}
