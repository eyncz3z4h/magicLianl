package csumissu.fakewechat.v2.model.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import javax.inject.Singleton;

import csumissu.fakewechat.data.User;
import csumissu.fakewechat.v2.dagger.ForApplication;
import csumissu.fakewechat.v2.model.UserDataSource;
import csumissu.fakewechat.v2.util.JsonUtils;
import rx.Observable;

/**
 * @author sunyaxi
 * @date 2016/11/17.
 */
@Singleton
public class UserLocalDataSource implements UserDataSource {

    private static final String TAG = UserLocalDataSource.class.getSimpleName();
    private SharedPreferences mSharedPrefs;
    private static final String KEY_OWNER = "owner";

    public UserLocalDataSource(@ForApplication Context context) {
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Observable<User> getOwner() {
        String userJson = mSharedPrefs.getString(KEY_OWNER, null);
        User user = JsonUtils.fromJson(userJson, User.class);
        return user == null ? Observable.empty() : Observable.just(user);
    }

    public void saveOwner(User user) {
        Log.i(TAG, "save owner " + user);
        if (user == null) {
            mSharedPrefs.edit().remove(KEY_OWNER).apply();
        } else {
            mSharedPrefs.edit().putString(KEY_OWNER, JsonUtils.toJson(user)).apply();
        }
    }
}
