package csumissu.fakewechat;

import android.app.Application;

import csumissu.fakewechat.data.source.EntityRepository;
import csumissu.fakewechat.data.source.local.LocalDataSource;
import csumissu.fakewechat.data.source.remote.RemoteDataSource;
import rx.schedulers.Schedulers;

/**
 * @author sunyaxi
 * @date 2016/6/22
 */
public class AppContext extends Application {

    private static AppContext sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        EntityRepository repository = EntityRepository.getInstance(
                LocalDataSource.getInstance(this),
                RemoteDataSource.getInstance());
        repository.getOwner().subscribeOn(Schedulers.io()).subscribe();
    }

    public static AppContext getInstance() {
        return sInstance;
    }
}
