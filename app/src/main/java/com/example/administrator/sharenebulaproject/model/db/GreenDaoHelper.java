package com.example.administrator.sharenebulaproject.model.db;

import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.db.entity.AppDBInfo;
import com.example.administrator.sharenebulaproject.model.db.entity.AppDBInfoDao;
import com.example.administrator.sharenebulaproject.model.db.entity.DaoMaster;
import com.example.administrator.sharenebulaproject.model.db.entity.DaoSession;
import com.example.administrator.sharenebulaproject.model.db.entity.LoginUserInfo;
import com.example.administrator.sharenebulaproject.model.db.entity.LoginUserInfoDao;
import com.example.administrator.sharenebulaproject.model.db.entity.ThirdPartyLoginStatusInfo;
import com.example.administrator.sharenebulaproject.model.db.entity.ThirdPartyLoginStatusInfoDao;
import com.example.administrator.sharenebulaproject.widget.Constants;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by Administrator on 2018/2/11.
 */

public class GreenDaoHelper implements DBHelper {

    private final LoginUserInfoDao loginUserInfoDao;
    private DaoSession mDaoSession;
    private final AppDBInfoDao appDBInfoDao;
    private final ThirdPartyLoginStatusInfoDao thirdPartyLoginStatusInfoDao;

    @Inject
    public GreenDaoHelper() {
        //初始化数据库
        setupDatabase();
        loginUserInfoDao = mDaoSession.getLoginUserInfoDao();
        appDBInfoDao = mDaoSession.getAppDBInfoDao();
        thirdPartyLoginStatusInfoDao = mDaoSession.getThirdPartyLoginStatusInfoDao();

    }

    private void setupDatabase() {
        //创建数据库,DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.getInstance().getApplicationContext(), Constants.DATABASE_USER_NAME, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象,DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者,DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
        mDaoSession = daoMaster.newSession();
    }

    @Override
    public LoginUserInfo queryLoginUserInfo(String mUserName) {
        mDaoSession.clear();
        return loginUserInfoDao.queryBuilder().where(LoginUserInfoDao.Properties.Username.eq(mUserName)).build().unique();
    }

    @Override
    public ThirdPartyLoginStatusInfo queryThirdPartyLoginStatusInfo(String thirdPartyId) {
        return thirdPartyLoginStatusInfoDao.queryBuilder().where(ThirdPartyLoginStatusInfoDao.Properties.ThirdPartyId.eq(thirdPartyId)).build().unique();
    }

    @Override
    public List<LoginUserInfo> loadLoginUserInfo() {
        mDaoSession.clear();
        return loginUserInfoDao.loadAll();
    }

    @Override
    public List<AppDBInfo> loadAppDBInfoDao() {
        mDaoSession.clear();
        return appDBInfoDao.loadAll();
    }


    @Override
    public void insertLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        loginUserInfoDao.insertOrReplace(mLoginUserInfo);
    }

    @Override
    public void insertAppDBInfoDao(AppDBInfo appDBInfo) {
        appDBInfoDao.insertOrReplace(appDBInfo);
    }

    @Override
    public void insertThirdPartyLoginStatusInfo(ThirdPartyLoginStatusInfo thirdPartyLoginStatusInfo) {
        thirdPartyLoginStatusInfoDao.insertOrReplace(thirdPartyLoginStatusInfo);
    }

    @Override
    public void deleteLoginUserInfo(String mUserName) {
        LoginUserInfo loginUserInfo = loginUserInfoDao.queryBuilder().where(LoginUserInfoDao.Properties.Username.eq(mUserName)).build().unique();
        if (loginUserInfo != null)
            loginUserInfoDao.delete(loginUserInfo);
    }

    @Override
    public void UpDataLoginUserInfo(LoginUserInfo mLoginUserInfo) {
        loginUserInfoDao.insertOrReplace(mLoginUserInfo);
    }


}
