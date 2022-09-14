package csumissu.fakewechat;

import android.app.Application;
import android.os.StrictMode;

import csumissu.fakewechat.data.User;
import csumissu.fakewechat.data.source.EntityRepository;
import csumissu.fakewechat.data.source.local.LocalDataSource;
import csumissu.fakewechat.data.source.remote.RemoteDataSource;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author sunyaxi
 * @date 2016/6/22
 */
public class AppContext extends Application {

    public boolean DEBUG = true;
    private static AppContext sInstance;
    private EntityRepository mRepository;
    private User mOwner;
    private boolean mIsLoading;

    @Override
    public void onCreate() {
        super.onCreate();

        if (DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
        }
        sInstance = this;

        mRepository = EntityRepository.getInstance(LocalDataSource.getInstance(this),
                RemoteDataSource.getInstance());
        getLoginUser();
    }

    public static AppContext getInstance() {
        return sInstance;
    }

    public User getLoginUser() {
        if (mOwner == null && !mIsLoading) {
            mRepository.getOwner()
                    .doOnSubscribe(() -> mIsLoading = true)
                    .doOnTerminate(() -> mIsLoading = false)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(user -> {
                        mOwner = user;
                    });
        }
        return mOwner;
    }

}
