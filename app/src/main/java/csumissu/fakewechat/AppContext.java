package csumissu.fakewechat;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.File;

import csumissu.fakewechat.data.User;
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
        AppConfig.init(this);

        if (getLoginUser() == null) {
            RemoteDataSource.getInstance().getOwner()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(this::saveLoginUser);
        }
    }

    public static AppContext getInstance() {
        return sInstance;
    }

    public void saveLoginUser(User user) {
        if (user == null) {
            AppConfig.remove(AppConfig.KEY_USER);
        } else {
            AppConfig.set(AppConfig.KEY_USER, new Gson().toJson(user));
        }
    }

    public User getLoginUser() {
        String json = AppConfig.get(AppConfig.KEY_USER);
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, User.class);
        }
        return null;
    }

    public void logout() {
        AppConfig.remove(AppConfig.KEY_USER);
    }
}
