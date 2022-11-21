package csumissu.fakewechat.v2.model.local;

import android.content.Context;

import javax.inject.Singleton;

import csumissu.fakewechat.data.User;
import csumissu.fakewechat.v2.dagger.ForApplication;
import csumissu.fakewechat.v2.model.UserDataSource;
import csumissu.fakewechat.v2.util.DiskCache;
import csumissu.fakewechat.v2.util.JsonUtils;
import rx.Observable;

/**
 * @author sunyaxi
 * @date 2016/11/17.
 */
@Singleton
public class UserLocalDataSource implements UserDataSource {

    private static final String TAG = UserLocalDataSource.class.getSimpleName();
    private static final String CACHE_KEY = String.valueOf(TAG.hashCode());
    private static final int CACHE_INDEX = 0;
    private DiskCache mDiskCache;

    public UserLocalDataSource(@ForApplication Context context) {
        mDiskCache = new DiskCache(context, "user", 1, 1024 * 1024);
    }

    @Override
    public Observable<User> getOwner() {
        String userJson = mDiskCache.get(CACHE_KEY, CACHE_INDEX);
        User user = JsonUtils.fromJson(userJson, User.class);
        return user == null ? Observable.empty() : Observable.just(user);
    }

    public void saveOwner(User user) {
        if (user == null) {
            mDiskCache.remove(CACHE_KEY);
        } else {
            mDiskCache.put(CACHE_KEY, CACHE_INDEX, JsonUtils.toJson(user));
        }
    }
}
