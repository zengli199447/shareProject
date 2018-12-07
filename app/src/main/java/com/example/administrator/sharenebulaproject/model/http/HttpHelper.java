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

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/10/27.
 */


public interface HttpHelper {

    Flowable<LoginInfoBean> fetchLogin(Map map);

    Flowable<OuterLayerEntity> UpCasePicData(MultipartBody multipartBody);

    Flowable<HotAllDataBean> HotPageData(Map map);

    Flowable<SettlementAccountNetBean> SettlementAccountNetData(Map map);

    Flowable<AccountBindNetBean> AccountBindNetBeanNetData(Map map);

    Flowable<ValidationCodeNet> ValidationCodeNetData(Map map);

    Flowable<MineInfoNetBean> MineInfoBeanNetData(Map map);

    Flowable<Object> SetUpUserContent(RequestBody body);

    Flowable<Object> SetUpUserContent(Map map);

    Flowable<UpLoadStatusNetBean> UpLoadStatus(Map map);

    Flowable<UpgradeNetBean> getUpgradeNetBean(Map map);

    Flowable<VipBenefitNetBean> getVipBenefitNetBean(Map map);

    Flowable<SettlementNetBean> getSettlementNetBean(Map map);

    Flowable<SettementLogNetBean> getSettementLogNetBean(Map map);

    Flowable<MyLevelNetBean> getMyLevelNetBean(Map map);

    Flowable<FansNetBean> getFansNetBean(Map map);

    Flowable<FansContentNetBean> getFansContentNetBean(Map map);

    Flowable<TextExplainsNetBean> getTextExplainsNetBean(Map map);

    Flowable<IncomeNetBean> getIncomeNetBean(Map map);

    Flowable<IncomeBenefitNetBean> getIncomeBenefitNetBean(Map map);

    Flowable<MyShareNetBean> getMyShareNetBean(Map map);

    Flowable<DailyNetBean> getDailyNetBean(Map map);

    Flowable<NewContentNetBean> getNewContentNetBean(Map map);

    Flowable<TopNetBean> getTopNetBean(Map map);

    Flowable<UpLoadUserInfoNetBean> getUpLoadUserInfoNetBean(Map map);

    Flowable<AlPayNetBean> getAlPayNetBean(Map map);

    Flowable<WechatPayNetBean> getWechatPayNetBean(Map map);

    Flowable<MoneyAboutNetBean> getMoneyAboutNetBean(Map map);

    Flowable<PosterGetNetBean> getPosterGetNetBean(Map map);

    Flowable<PhoneBindNetBean> getPhoneBindNetBean(Map map);

    Flowable<TheNewTypeNetBean> getTheNewTypeNetBean(Map map);

    Flowable<TheArticleDetailsNetBean> getTheArticleDetailsNetBean(Map map);

}
