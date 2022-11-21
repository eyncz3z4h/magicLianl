package csumissu.fakewechat.v2.model;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import csumissu.fakewechat.data.User;
import csumissu.fakewechat.v2.dagger.Local;
import csumissu.fakewechat.v2.dagger.Remote;
import csumissu.fakewechat.v2.model.local.UserLocalDataSource;
import rx.Observable;

/**
 * @author sunyaxi
 * @date 2016/11/17.
 */
@Singleton
public class UserRepository implements UserDataSource {

    private static final String TAG = UserRepository.class.getSimpleName();
    private final UserDataSource mRemoteDataSource;
    private final UserDataSource mLocalDataSource;

    @Inject
    UserRepository(@Remote UserDataSource remoteDataSource,
                   @Local UserDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    @Override
    public Observable<User> getOwner() {
        Observable<User> disk = mLocalDataSource.getOwner();
        Observable<User> network = mRemoteDataSource.getOwner()
                .doOnNext(user -> {
                    Log.i(TAG, "save owner " + user);
                    if (mLocalDataSource instanceof UserLocalDataSource) {
                        ((UserLocalDataSource) mLocalDataSource).saveOwner(user);
                    }
                });
        return Observable.concat(disk, network).first();
    }
}
