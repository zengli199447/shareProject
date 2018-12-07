package com.example.administrator.sharenebulaproject.model.http.api;

import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.AccountBindNetBean;
import com.example.administrator.sharenebulaproject.model.bean.AlPayNetBean;
import com.example.administrator.sharenebulaproject.model.bean.DailyNetBean;
import com.example.administrator.sharenebulaproject.model.bean.FansContentNetBean;
import com.example.administrator.sharenebulaproject.model.bean.FansNetBean;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.IncomeBenefitNetBean;
import com.example.administrator.sharenebulaproject.model.bean.IncomeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.LoginInfoBean;
import com.example.administrator.sharenebulaproject.model.bean.MineInfoNetBean;
import com.example.administrator.sharenebulaproject.model.bean.MoneyAboutNetBean;
import com.example.administrator.sharenebulaproject.model.bean.MyLevelNetBean;
import com.example.administrator.sharenebulaproject.model.bean.MyShareNetBean;
import com.example.administrator.sharenebulaproject.model.bean.NewContentNetBean;
import com.example.administrator.sharenebulaproject.model.bean.OuterLayerEntity;
import com.example.administrator.sharenebulaproject.model.bean.PhoneBindNetBean;
import com.example.administrator.sharenebulaproject.model.bean.PosterGetNetBean;
import com.example.administrator.sharenebulaproject.model.bean.SettementLogNetBean;
import com.example.administrator.sharenebulaproject.model.bean.SettlementAccountNetBean;
import com.example.administrator.sharenebulaproject.model.bean.SettlementNetBean;
import com.example.administrator.sharenebulaproject.model.bean.TextExplainsNetBean;
import com.example.administrator.sharenebulaproject.model.bean.TheArticleDetailsNetBean;
import com.example.administrator.sharenebulaproject.model.bean.TheNewTypeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.TopNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadUserInfoNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpgradeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.ValidationCodeNet;
import com.example.administrator.sharenebulaproject.model.bean.VipBenefitNetBean;
import com.example.administrator.sharenebulaproject.model.bean.WechatPayNetBean;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/10/30.
 * 网络请求
 * 添加请求头可以统一添加  单一添加请查看操作符
 * 统一添加查看 di 目录下 httmModule
 */

public interface MyApis {

//    String HOST = "http://192.168.14.199:8088/portal/r/";

//    String HOST = "http://xfx.027perfect.com/api/";/

    String HOST = DataClass.URL;

    /**
     * 登录
     *
     * @return
     */
    @GET("api.mingfa.php?")
    Flowable<LoginInfoBean> login(@QueryMap Map<String, String> map);

    /**
     * 上传文件
     *
     * @return
     */
    @POST("")
    Flowable<OuterLayerEntity> UpCasePicData(@Body MultipartBody multipartBody);

    /**
     * 首页数据集
     */
    @GET("api.mingfa.php?")
    Flowable<HotAllDataBean> HotPageData(@QueryMap Map<String, String> map);

    /**
     * 结算账户列表
     */
    @GET("api.mingfa.php?")
    Flowable<SettlementAccountNetBean> SettlementAccountNetData(@QueryMap Map<String, String> map);

    /**
     * 结算账户绑定
     */
    @GET("api.mingfa.php?")
    Flowable<AccountBindNetBean> AccountBindNetData(@QueryMap Map<String, String> map);

    /**
     * 获取验证码
     */
    @GET("api.mingfa.php?")
    Flowable<ValidationCodeNet> ValidationCodeNetData(@QueryMap Map<String, String> map);

    /**
     * 获取个人信息
     */
    @GET("api.mingfa.php?")
    Flowable<MineInfoNetBean> MineInfoBeanNetData(@QueryMap Map<String, String> map);

    @POST("api.mingfa.php?version=v1&vars=")
    Flowable<Object> UploadFileWithPartMap(@Body RequestBody Body);

    /**
     * 更新个人信息
     */
    @POST("api.mingfa.php?")
    Flowable<Object> UploadFileWithPartMap(@QueryMap Map<String, String> map);

    /**
     * 返回提交状态
     */
    @POST("api.mingfa.php?")
    Flowable<UpLoadStatusNetBean> UpLoadStatus(@QueryMap Map<String, String> map);

    /**
     * 升级会员（页面初始化）
     */
    @POST("api.mingfa.php?")
    Flowable<UpgradeNetBean> getUpgradeNetBean(@QueryMap Map<String, String> map);

    /**
     * 会员权益
     */
    @POST("api.mingfa.php?")
    Flowable<VipBenefitNetBean> getVipBenefitNetBean(@QueryMap Map<String, String> map);

    /**
     * 结算信息
     */
    @POST("api.mingfa.php?")
    Flowable<SettlementNetBean> getSettlementNetBean(@QueryMap Map<String, String> map);

    /**
     * 结算记录
     */
    @POST("api.mingfa.php?")
    Flowable<SettementLogNetBean> getSettementLogNetBean(@QueryMap Map<String, String> map);

    /**
     * 我的等级
     */
    @POST("api.mingfa.php?")
    Flowable<MyLevelNetBean> getMyLevelNetBean(@QueryMap Map<String, String> map);

    /**
     * 我的粉丝
     */
    @POST("api.mingfa.php?")
    Flowable<FansNetBean> getFansNetBean(@QueryMap Map<String, String> map);

    /**
     * 粉丝列表
     */
    @POST("api.mingfa.php?")
    Flowable<FansContentNetBean> getFansContentNetBean(@QueryMap Map<String, String> map);

    /**
     * 粉丝列表
     */
    @POST("api.mingfa.php?")
    Flowable<TextExplainsNetBean> getTextExplainsNetBean(@QueryMap Map<String, String> map);

    /**
     * 收入列表
     */
    @POST("api.mingfa.php?")
    Flowable<IncomeNetBean> getIncomeNetBean(@QueryMap Map<String, String> map);

    /**
     * 收入权益
     */
    @POST("api.mingfa.php?")
    Flowable<IncomeBenefitNetBean> getIncomeBenefitNetBean(@QueryMap Map<String, String> map);

    /**
     * 我的分享
     */
    @POST("api.mingfa.php?")
    Flowable<MyShareNetBean> getMyShareNetBean(@QueryMap Map<String, String> map);

    /**
     * 每日精选
     */
    @POST("api.mingfa.php?")
    Flowable<DailyNetBean> getDailyNetBean(@QueryMap Map<String, String> map);

    /**
     * 文章内容
     */
    @POST("api.mingfa.php?")
    Flowable<NewContentNetBean> getNewContentNetBean(@QueryMap Map<String, String> map);

    /**
     * 达人榜单
     */
    @POST("api.mingfa.php?")
    Flowable<TopNetBean> getTopNetBean(@QueryMap Map<String, String> map);

    /**
     * 修改个人信息
     */
    @POST("api.mingfa.php?")
    Flowable<UpLoadUserInfoNetBean> getUpLoadUserInfoNetBean(@QueryMap Map<String, String> map);

    /**
     * 支付宝支付
     */
    @POST("api.mingfa.php?")
    Flowable<AlPayNetBean> getAlPayNetBean(@QueryMap Map<String, String> map);

    /**
     * 微信支付
     */
    @POST("api.mingfa.php?")
    Flowable<WechatPayNetBean> getWechatPayNetBean(@QueryMap Map<String, String> map);

    /**
     * 收入明细
     */
    @POST("api.mingfa.php?")
    Flowable<MoneyAboutNetBean> getMoneyAboutNetBean(@QueryMap Map<String, String> map);

    /**
     * 收入明细
     */
    @POST("api.mingfa.php?")
    Flowable<PosterGetNetBean> getPosterGetNetBean(@QueryMap Map<String, String> map);

    /**
     * 绑定手机/更新绑定
     */
    @POST("api.mingfa.php?")
    Flowable<PhoneBindNetBean> getPhoneBindNetBean(@QueryMap Map<String, String> map);

    /**
     * 获取新闻类型
     */
    @POST("api.mingfa.php?")
    Flowable<TheNewTypeNetBean> getTheNewTypeNetBean(@QueryMap Map<String, String> map);

    /**
     * 获取文章详情
     */
    @POST("api.mingfa.php?")
    Flowable<TheArticleDetailsNetBean> getTheArticleDetailsNetBean(@QueryMap Map<String, String> map);


}
