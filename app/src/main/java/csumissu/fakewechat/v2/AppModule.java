package csumissu.fakewechat.v2;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import csumissu.fakewechat.v2.dagger.ForApplication;
import csumissu.fakewechat.v2.dagger.Local;
import csumissu.fakewechat.v2.dagger.ReadableDb;
import csumissu.fakewechat.v2.dagger.Remote;
import csumissu.fakewechat.v2.dagger.WritableDb;
import csumissu.fakewechat.v2.dao.DaoMaster;
import csumissu.fakewechat.v2.dao.DaoSession;
import csumissu.fakewechat.v2.model.UserDataSource;
import csumissu.fakewechat.v2.model.local.UserLocalDataSource;
import csumissu.fakewechat.v2.model.remote.UserRemoteDataSource;
import dagger.Module;
import dagger.Provides;

/**
 * @author sunyaxi
 * @date 2016/11/17.
 */
@Module
public class AppModule {

    private final WechatApp mApplication;
    private DaoMaster.DevOpenHelper mOpenHelper;

    public AppModule(WechatApp application) {
        mApplication = application;
        mOpenHelper = new DaoMaster.DevOpenHelper(mApplication, "wechat-db");
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    @WritableDb
    DaoSession provideWritableDaoSession() {
        Database db = mOpenHelper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    @Provides
    @Singleton
    @ReadableDb
    DaoSession provideReadableDaoSession() {
        Database db = mOpenHelper.getReadableDb();
        return new DaoMaster(db).newSession();
    }

    @Provides
    @Singleton
    @Local
    UserDataSource provideUserLocalDataSource(@ForApplication Context context) {
        return new UserLocalDataSource(context);
    }

    @Provides
    @Singleton
    @Remote
    UserDataSource provideUserRemoteDataSource() {
        return new UserRemoteDataSource();
    }

}
