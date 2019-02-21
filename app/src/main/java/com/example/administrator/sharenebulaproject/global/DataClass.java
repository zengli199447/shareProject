package com.example.administrator.sharenebulaproject.global;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.DataManager;
import com.example.administrator.sharenebulaproject.model.bean.LoginInfoBean;
import com.example.administrator.sharenebulaproject.model.bean.TheNewTypeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.db.entity.LoginUserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class DataClass {

    //TODO BaseUrl
//    public static String URL = "http://192.168.14.199:8088/portal/r/";
//    public static String URL = "http://xfx.027perfect.com/api/";
//    public static String URL_ = "http://xfx.027perfect.com/";
//    public static String URL = "http://ydy.lixb.cc/api/";
    public static String URL = "http://yuedeyi.douy18.com/api/";
    public static String URL_ = "http://yuedeyi.douy18.com/";
    public static String APK_URL = "http://192.168.2.197:8080/data/yuedeyi.apk";

    //媒体文件前缀
//    public static String FileUrl = "http://xfx.027perfect.com/";
    public static String FileUrl = "http://yuedeyi.douy18.com/";

    public static String BASE_URL = URL + "api.mingfa.php?version=v1&vars=" + "{" + "\"" + "action" + "\"" + ":" + "\"" + "image_save_set" + "\"" + "}";

    public static String MESSAGE_URL = URL_ + "pf_wx.php?act=home&do=messagelist&uid=";

    public static String DAILY_URL = URL_ + "pf_wx.php?act=oneday&do=detail&newsid=";

    public static String SELECT = URL_ + "pf_wx.php?act=oneday&do=display&userid_share=";

    public static int LOGIN_ = 1;//登陆
    public static int TRADE_PASSWORD = 2;//交易密码设置
    public static int QQ_LOGIN = 4;//qq登陆绑定手机
    public static int WECHAT_LOGIN = 5;//微信登陆绑定手机
    public static int OLDPHONE = 6;//修改绑定的手机号-旧手机号验证
    public static int NEWPHONE = 7;//修改绑定的手机号-新手机号验证

    public static int LOGINTYPE = 1;//登陆类型 1/手机登陆  2/qq登陆  3/wechat登陆

    public static int INFOTYPE_USE_AGREEMENT = 2; //使用协议
    public static int AWARDS_SHOW = 3; //奖励说明
    public static int HELP_CENTER = 6; //帮助中心
    public static int PROMOTION_COOPERATION = 7; //推广合作说明
    public static int HIERARCHY = 8; //等级制度
    public static int CONTACT_CUSTOMER_SERVICE = 9; //联系客服
    public static int BILLING_INSTRUCTIONS = 13; //结算说明

    public static int WINDOWS_WIDTH = 0;
    public static int WINDOWS_HEIGHT = 0;
    public static int PX_WINDOWS_WIDTH = 0;
    public static int PX_WINDOWS_HEIGHT = 0;

    //用户登陆ID
    public static String USERID = "";
    //用户分享后缀
    //登陆
    public static String LOGIN = "user_login_get";
    //获取验证码
    public static String USERID_SHARE = "&userid_share=" + USERID;
    public static String GET_CODE = "get_code";
    //获取首页数据
    public static String HOMEPAGE_GET = "homepage_get";
    //我的
    public static String MY_CENTER_GET = "my_center_get";
    //个人资料修改
    public static String USER_INFO_SET = "user_info_set";
    //图片上传
    public static String IMAGE_SAVE_SET = "image_save_set";
    // 头像保存地址
    public static String IMAGESAVEGET = BASE_URL + "{" + "\"" + "action" + "\"" + ":" + "\"" + "image_save_set" + "\"" + "}";
    //实名认证
    public static String REALNAME_INFO_SET = "realname_info_set";
    //交易密码（修改交易密码）
    public static String TRANSACTION_PWD_SET = "transaction_pwd_set";
    //绑定新手机号
    public static String NEW_PHONE_SET = "new_phone_set";
    //绑定手机号
    public static String PHONE_BIND_SET = "phone_bind_set";
    //获取VIP升级信息
    public static String UPVIP_INIT_GET = "upvip_init_get";
    //会员权益
    public static String UPVIP_REMARK_GET = "upvip_remark_get";
    //升级会员
    public static String SERVICE_OPT_GETORDER_SET = "service_opt_getorder_set";
    //结算信息
    public static String SETTLEACCOUNTS_INIT_GET = "settleaccounts_init_get";
    //结算
    public static String SETTLEACCOUNTS_SET = "settleaccounts_set";
    //结算记录
    public static String SETTLEACCOUNTS_LIST_GET = "settleaccounts_list_get";
    //我的等级
    public static String MYVIP_DETAIL_GET = "myvip_detail_get";
    //我的粉丝
    public static String FANS_TJ_GET = "fans_tj_get";
    //粉丝列表
    public static String FANS_LIST_GET = "fans_list_get";
    //说明类容
    public static String TEXTINFO_GET = "textinfo_get";
    //推广合作
    public static String COOPERATION_SET = "cooperation_set";
    //收入信息
    public static String MONEY_IN_GET = "money_in_get";
    //收入权益
    public static String MY_INTERESTS_GET = "my_interests_get";
    //我的分享
    public static String MYSHARE_LIST_GET = "myshare_list_get";
    //收入明细
    public static String MONEY_IN_LIST_GET = "money_in_list_get";

    //获取结算账户列表
    public static String MONEY_ACCOUNT_LIST_GET = "money_account_list_get";
    //提交绑定
    public static String MONEY_ACCOUNT_SET = "money_account_set";
    //解除绑定
    public static String MONEY_ACCOUNT_DELETE_SET = "money_account_delete_set";
    //每日精选
    public static String TODAY_NEWS_LIST_GET = "today_news_list_get";
    //文章详情
    public static String NEWS_DETAIL_GET = "news_detail_get";
    //达人榜
    public static String TALENT_GET = "talent_get";
    //获取当前个人信息
    public static String USER_DETAIL_GET = "user_detail_get";
    //分享成功回调
    public static String AFTERSHARE_SET = "aftershare_set";
    //banner首页详情
    public static String TEXTINFO_DETAIL_GET = "textinfo_detail_get";
    //获取海报图
    public static String POSTER_GET = "poster_get";
    //微信绑定
    public static String WEIXIN_SET = "weixin_set";
    //邀请码提交
    public static String BIND_INVITATIONCODE = "bind_invitationcode";
    //新闻分类
    public static String CATE_GET = "cate_get";
    //新闻分类顺序排列
    public static String CATE_SORT_SET = "cate_sort_set";
    //点赞
    public static String ZAN_SET = "zan_set";
    //发送评论
    public static String COMMENT_SET = "comment_set";


    //新闻类型
    public static List<Object> TYPE_TITLE = new ArrayList<>();
    //所有新闻类型
    public static List<TheNewTypeNetBean.ResultBean.AllBean> ALL_TYPE_TITLE = new ArrayList<>();

    private ArrayList<Integer> bannerList = new ArrayList<>();
    //当前城市
    public static String CNBYLOCATION = "本地";

    public static int DefaultInformationFlow = 9;

    public static boolean WEBSTATUS;

    public static boolean VERSION_WEBSTATUS;

    public static boolean VERSION_UP = true;

    public DataManager dataManager;

    public DataClass() {

    }

    public DataClass(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void DbCurrentUser(String userName, String passWord) {  //保存/修改当前帐号信息
        final List<LoginUserInfo> loginUserInfos = dataManager.loadLoginUserInfo();
        if (loginUserInfos != null && loginUserInfos.size() > 0) {
            final LoginUserInfo loginUserInfo = loginUserInfos.get(0);
            if (userName.equals(loginUserInfo.getUsername())) {
                dataManager.UpDataLoginUserInfo(new LoginUserInfo(loginUserInfo.getId(), "token", userName, passWord));
            } else {
                dataManager.insertLoginUserInfo(new LoginUserInfo(userName, "token", passWord));
            }
        } else {
            dataManager.insertLoginUserInfo(new LoginUserInfo(userName, "token", passWord));
        }
    }

    public LoginUserInfo GetCurrentUser() { // 获取上一次登录用户信息
        LoginUserInfo loginUserInfo = null;
        final List<LoginUserInfo> loginUserInfos = dataManager.loadLoginUserInfo();
        if (loginUserInfos != null && loginUserInfos.size() > 0) {
            loginUserInfo = loginUserInfos.get(0);
        }
        return loginUserInfo;
    }

    public ArrayList<Integer> getWelcomeBannerList() {
//      slide_show.addImageUrl("http://e.hiphotos.baidu.com/image/pic/item/d0c8a786c9177f3eeb8bedb57ccf3bc79e3d56ce.jpg");
        bannerList.add(R.drawable.banner2);
        bannerList.add(R.drawable.banner1);
        bannerList.add(R.drawable.banner3);
        return bannerList;
    }

    public ArrayList<Integer> getHomePageBannerList() {
        bannerList.add(R.drawable.home_banner);
        bannerList.add(R.drawable.home_banner);
        bannerList.add(R.drawable.home_banner);
        return bannerList;
    }

    public static void initUserInfo(LoginInfoBean.ResultBean result) {
        UserInfoBean.Invitationcode = result.getInvitationcode();
        UserInfoBean.brithday = result.getBrithday();
        UserInfoBean.city = result.getCity();
        UserInfoBean.createdate = result.getCreatedate();
        UserInfoBean.idcard = result.getIdcard();
        UserInfoBean.ifaccountcheck = result.getIfaccountcheck();
        UserInfoBean.iflogin = result.getIflogin();
        UserInfoBean.ifphonecheck = result.getIfphonecheck();
        UserInfoBean.ifpwdcheck = result.getIfpwdcheck();
        UserInfoBean.ifrealnamecheck = result.getIfrealnamecheck();
        UserInfoBean.ifwxcheck = result.getIfwxcheck();
        UserInfoBean.job = result.getJob();
        UserInfoBean.levelconfigid = result.getLevelconfigid();
        UserInfoBean.levelpath = result.getLevelpath();
        UserInfoBean.moneyin = result.getMoneyin();
        UserInfoBean.name = result.getName();
        UserInfoBean.phone = result.getPhone();
        UserInfoBean.photo = result.getPhoto();
        UserInfoBean.pid = result.getPid();
        UserInfoBean.province = result.getProvince();
        UserInfoBean.pwd = result.getPwd();
        UserInfoBean.qq = result.getQq();
        UserInfoBean.role = result.getRole();
        UserInfoBean.secondname = result.getSecondname();
        UserInfoBean.sex = result.getSex();
        UserInfoBean.state = result.getState();
        UserInfoBean.token = result.getToken();
        UserInfoBean.traderspwd = result.getTraderspwd();
        UserInfoBean.userid = result.getUserid();
        UserInfoBean.vipexpirydate = result.getVipexpirydate();
        UserInfoBean.weixin = result.getWeixin();
        USERID = result.getUserid();
    }

    public static String getUSERID() {
        return USERID;
    }

}
