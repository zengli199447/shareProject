package com.example.administrator.sharenebulaproject.model;

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
import com.example.administrator.sharenebulaproject.model.db.DBHelper;
import com.example.administrator.sharenebulaproject.model.db.entity.AppDBInfo;
import com.example.administrator.sharenebulaproject.model.db.entity.LoginUserInfo;
import com.example.administrator.sharenebulaproject.model.db.entity.ThirdPartyLoginStatusInfo;
import com.example.administrator.sharenebulaproject.model.http.HttpHelper;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/10/27.
 */

public class DataManager implements HttpHelper, DBHelper {
    String TAG = "DataManager";

    private HttpHelper mHttpHelper;
    private DBHelper mDbHelper;

    public DataManager(HttpHelper mHttpHelper, DBHelper mDbHelper) {
        this.mHttpHelper = mHttpHelper;
        this.mDbHelper = mDbHelper;
    }

    //---------------------------网络请求---------------------------------------

    @Override
    public Flowable<LoginInfoBean> fetchLogin(Map map) {
        return mHttpHelper.fetchLogin(map);
    }

    @Override
    public Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody) {
        return mHttpHelper.UpCasePicData(multipartBody);
    }

    @Override
    public Flowable<HotAllDataBean> HotPageData(Map map) {
        return mHttpHelper.HotPageData(map);
    }

    @Override
    public Flowable<SettlementAccountNetBean> SettlementAccountNetData(Map map) {
        return mHttpHelper.SettlementAccountNetData(map);
    }

    @Override
    public Flowable<AccountBindNetBean> AccountBindNetBeanNetData(Map map) {
        return mHttpHelper.AccountBindNetBeanNetData(map);
    }

    @Override
    public Flowable<ValidationCodeNet> ValidationCodeNetData(Map map) {
        return mHttpHelper.ValidationCodeNetData(map);
    }

    @Override
    public Flowable<MineInfoNetBean> MineInfoBeanNetData(Map map) {
        return mHttpHelper.MineInfoBeanNetData(map);
    }

    @Override
    public Flowable<Object> SetUpUserContent(RequestBody body) {
        return mHttpHelper.SetUpUserContent(body);
    }

    @Override
    public Flowable<Object> SetUpUserContent(Map map) {
        return mHttpHelper.SetUpUserContent(map);
    }

    @Override
    public Flowable<UpLoadStatusNetBean> UpLoadStatus(Map map) {
        return mHttpHelper.UpLoadStatus(map);
    }

    @Override
    public Flowable<UpgradeNetBean> getUpgradeNetBean(Map map) {
        return mHttpHelper.getUpgradeNetBean(map);
    }

    @Override
    public Flowable<VipBenefitNetBean> getVipBenefitNetBean(Map map) {
        return mHttpHelper.getVipBenefitNetBean(map);
    }

    @Override
    public Flowable<SettlementNetBean> getSettlementNetBean(Map map) {
        return mHttpHelper.getSettlementNetBean(map);
    }

    @Override
    public Flowable<SettementLogNetBean> getSettementLogNetBean(Map map) {
        return mHttpHelper.getSettementLogNetBean(map);
    }

    @Override
    public Flowable<MyLevelNetBean> getMyLevelNetBean(Map map) {
        return mHttpHelper.getMyLevelNetBean(map);
    }

    @Override
    public Flowable<FansNetBean> getFansNetBean(Map map) {
        return mHttpHelper.getFansNetBean(map);
    }

    @Override
    public Flowable<FansContentNetBean> getFansContentNetBean(Map map) {
        return mHttpHelper.getFansContentNetBean(map);
    }

    @Override
    public Flowable<TextExplainsNetBean> getTextExplainsNetBean(Map map) {
        return mHttpHelper.getTextExplainsNetBean(map);
    }

    @Override
    public Flowable<IncomeNetBean> getIncomeNetBean(Map map) {
        return mHttpHelper.getIncomeNetBean(map);
    }

    @Override
    public Flowable<IncomeBenefitNetBean> getIncomeBenefitNetBean(Map map) {
        return mHttpHelper.getIncomeBenefitNetBean(map);
    }

    @Override
    public Flowable<MyShareNetBean> getMyShareNetBean(Map map) {
        return mHttpHelper.getMyShareNetBean(map);
    }

    @Override
    public Flowable<DailyNetBean> getDailyNetBean(Map map) {
        return mHttpHelper.getDailyNetBean(map);
    }

    @Override
    public Flowable<NewContentNetBean> getNewContentNetBean(Map map) {
        return mHttpHelper.getNewContentNetBean(map);
    }

    @Override
    public Flowable<TopNetBean> getTopNetBean(Map map) {
        return mHttpHelper.getTopNetBean(map);
    }

    @Override
    public Flowable<UpLoadUserInfoNetBean> getUpLoadUserInfoNetBean(Map map) {
        return mHttpHelper.getUpLoadUserInfoNetBean(map);
    }

    @Override
    public Flowable<AlPayNetBean> getAlPayNetBean(Map map) {
        return mHttpHelper.getAlPayNetBean(map);
    }

    @Override
    public Flowable<WechatPayNetBean> getWechatPayNetBean(Map map) {
        return mHttpHelper.getWechatPayNetBean(map);
    }

    @Override
    public Flowable<MoneyAboutNetBean> getMoneyAboutNetBean(Map map) {
        return mHttpHelper.getMoneyAboutNetBean(map);
    }

    @Override
    public Flowable<PosterGetNetBean> getPosterGetNetBean(Map map) {
        return mHttpHelper.getPosterGetNetBean(map);
    }

    @Override
    public Flowable<PhoneBindNetBean> getPhoneBindNetBean(Map map) {
        return mHttpHelper.getPhoneBindNetBean(map);
    }

    @Override
    public Flowable<TheNewTypeNetBean> getTheNewTypeNetBean(Map map) {
        return mHttpHelper.getTheNewTypeNetBean(map);
    }

    @Override
    public Flowable<TheArticleDetailsNetBean> getTheArticleDetailsNetBean(Map map) {
        return mHttpHelper.getTheArticleDetailsNetBean(map);
    }


    //---------------------------数据库查询---------------------------------------

    @Override
    public LoginUserInfo queryLoginUserInfo(String mUserName) {
        return mDbHelper.queryLoginUserInfo(mUserName);
    }

    @Override
    public ThirdPartyLoginStatusInfo queryThirdPartyLoginStatusInfo(String thirdPartyId) {
        return mDbHelper.queryThirdPartyLoginStatusInfo(thirdPartyId);
    }

    @Override
    public List<LoginUserInfo> loadLoginUserInfo() {
        return mDbHelper.loadLoginUserInfo();
    }

    @Override
    public List<AppDBInfo> loadAppDBInfoDao() {
        return mDbHelper.loadAppDBInfoDao();
    }

    @Override
    public void insertLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        mDbHelper.insertLoginUserInfo(mLoginUserInfo);
    }

    @Override
    public void insertAppDBInfoDao(AppDBInfo appDBInfo) {
        mDbHelper.insertAppDBInfoDao(appDBInfo);
    }

    @Override
    public void insertThirdPartyLoginStatusInfo(ThirdPartyLoginStatusInfo thirdPartyLoginStatusInfo) {
        mDbHelper.insertThirdPartyLoginStatusInfo(thirdPartyLoginStatusInfo);
    }

    @Override
    public void deleteLoginUserInfo(String mUserName) {
        mDbHelper.deleteLoginUserInfo(mUserName);
    }

    @Override
    public void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        mDbHelper.UpDataLoginUserInfo(mLoginUserInfo);
    }


}
