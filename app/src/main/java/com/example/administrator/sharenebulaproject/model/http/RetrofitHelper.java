package com.example.administrator.sharenebulaproject.model.http;

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
import com.example.administrator.sharenebulaproject.model.http.api.MyApis;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/10/30.
 */

public class RetrofitHelper implements HttpHelper {

    private MyApis mMyApiService;

    @Inject
    public RetrofitHelper(MyApis myApiService) {
        this.mMyApiService = myApiService;
    }


    @Override
    public Flowable<LoginInfoBean> fetchLogin(Map map) {
        return mMyApiService.login(map);
    }

    @Override
    public Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody) {
        return mMyApiService.UpCasePicData(multipartBody);
    }

    @Override
    public Flowable<HotAllDataBean> HotPageData(Map map) {
        return mMyApiService.HotPageData(map);
    }

    @Override
    public Flowable<SettlementAccountNetBean> SettlementAccountNetData(Map map) {
        return mMyApiService.SettlementAccountNetData(map);
    }

    @Override
    public Flowable<AccountBindNetBean> AccountBindNetBeanNetData(Map map) {
        return mMyApiService.AccountBindNetData(map);
    }

    @Override
    public Flowable<ValidationCodeNet> ValidationCodeNetData(Map map) {
        return mMyApiService.ValidationCodeNetData(map);
    }

    @Override
    public Flowable<MineInfoNetBean> MineInfoBeanNetData(Map map) {
        return mMyApiService.MineInfoBeanNetData(map);
    }

    @Override
    public Flowable<Object> SetUpUserContent(RequestBody body) {
        return mMyApiService.UploadFileWithPartMap(body);
    }

    @Override
    public Flowable<Object> SetUpUserContent(Map map) {
        return mMyApiService.UploadFileWithPartMap(map);
    }

    @Override
    public Flowable<UpLoadStatusNetBean> UpLoadStatus(Map map) {
        return mMyApiService.UpLoadStatus(map);
    }

    @Override
    public Flowable<UpgradeNetBean> getUpgradeNetBean(Map map) {
        return mMyApiService.getUpgradeNetBean(map);
    }

    @Override
    public Flowable<VipBenefitNetBean> getVipBenefitNetBean(Map map) {
        return mMyApiService.getVipBenefitNetBean(map);
    }

    @Override
    public Flowable<SettlementNetBean> getSettlementNetBean(Map map) {
        return mMyApiService.getSettlementNetBean(map);
    }

    @Override
    public Flowable<SettementLogNetBean> getSettementLogNetBean(Map map) {
        return mMyApiService.getSettementLogNetBean(map);
    }

    @Override
    public Flowable<MyLevelNetBean> getMyLevelNetBean(Map map) {
        return mMyApiService.getMyLevelNetBean(map);
    }

    @Override
    public Flowable<FansNetBean> getFansNetBean(Map map) {
        return mMyApiService.getFansNetBean(map);
    }

    @Override
    public Flowable<FansContentNetBean> getFansContentNetBean(Map map) {
        return mMyApiService.getFansContentNetBean(map);
    }

    @Override
    public Flowable<TextExplainsNetBean> getTextExplainsNetBean(Map map) {
        return mMyApiService.getTextExplainsNetBean(map);
    }

    @Override
    public Flowable<IncomeNetBean> getIncomeNetBean(Map map) {
        return mMyApiService.getIncomeNetBean(map);
    }

    @Override
    public Flowable<IncomeBenefitNetBean> getIncomeBenefitNetBean(Map map) {
        return mMyApiService.getIncomeBenefitNetBean(map);
    }

    @Override
    public Flowable<MyShareNetBean> getMyShareNetBean(Map map) {
        return mMyApiService.getMyShareNetBean(map);
    }

    @Override
    public Flowable<DailyNetBean> getDailyNetBean(Map map) {
        return mMyApiService.getDailyNetBean(map);
    }

    @Override
    public Flowable<NewContentNetBean> getNewContentNetBean(Map map) {
        return mMyApiService.getNewContentNetBean(map);
    }

    @Override
    public Flowable<TopNetBean> getTopNetBean(Map map) {
        return mMyApiService.getTopNetBean(map);
    }

    @Override
    public Flowable<UpLoadUserInfoNetBean> getUpLoadUserInfoNetBean(Map map) {
        return mMyApiService.getUpLoadUserInfoNetBean(map);
    }

    @Override
    public Flowable<AlPayNetBean> getAlPayNetBean(Map map) {
        return mMyApiService.getAlPayNetBean(map);
    }

    @Override
    public Flowable<WechatPayNetBean> getWechatPayNetBean(Map map) {
        return mMyApiService.getWechatPayNetBean(map);
    }

    @Override
    public Flowable<MoneyAboutNetBean> getMoneyAboutNetBean(Map map) {
        return mMyApiService.getMoneyAboutNetBean(map);
    }

    @Override
    public Flowable<PosterGetNetBean> getPosterGetNetBean(Map map) {
        return mMyApiService.getPosterGetNetBean(map);
    }

    @Override
    public Flowable<PhoneBindNetBean> getPhoneBindNetBean(Map map) {
        return mMyApiService.getPhoneBindNetBean(map);
    }

    @Override
    public Flowable<TheNewTypeNetBean> getTheNewTypeNetBean(Map map) {
        return mMyApiService.getTheNewTypeNetBean(map);
    }

    @Override
    public Flowable<TheArticleDetailsNetBean> getTheArticleDetailsNetBean(Map map) {
        return mMyApiService.getTheArticleDetailsNetBean(map);
    }


}
