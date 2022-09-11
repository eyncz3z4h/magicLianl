package csumissu.fakewechat.data.source.remote;

import android.util.Log;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;

import csumissu.fakewechat.data.FriendshipResult;
import csumissu.fakewechat.data.Status;
import csumissu.fakewechat.data.StatusResult;
import csumissu.fakewechat.data.User;
import csumissu.fakewechat.data.source.EntityDataSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * @author sunyaxi
 * @date 2016/5/20
 */
public class RemoteDataSource implements EntityDataSource {

    private static final String TAG = RemoteDataSource.class.getSimpleName();
    private static RemoteDataSource INSTANCE;
    private WeiboApi mWeiboApi;

    private RemoteDataSource() {
        Gson gson = new GsonBuilder()
                .setDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
                .serializeNulls()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weibo.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mWeiboApi = retrofit.create(WeiboApi.class);
    }

    public synchronized static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<User> getOwner() {
        return mWeiboApi.getUser(WeiboApi.ACCESS_TOKEN, WeiboApi.OWNER_UID);
    }

    @Override
    public void saveOwner(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<List<User>> getFriends() {
        return mWeiboApi.getFriends(WeiboApi.ACCESS_TOKEN, WeiboApi.OWNER_UID)
                .map(FriendshipResult::getUsers)
                .map(users -> {
                    if (users != null) {
                        for (User user : users) {
                            String pinyin = PinyinHelper.convertToPinyinString(
                                    user.getName().trim(), "", PinyinFormat.WITHOUT_TONE);
                            System.out.println("name=" + user.getName() + ", pinyin=" + pinyin);
                            user.setPinyin(pinyin);
                        }
                        Collections.sort(users, (u1, u2) -> u1.getPinyin().compareTo(u2.getPinyin()));
                    }
                    return users;
                });
    }

    @Override
    public void saveFriends(List<User> users) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<StatusResult> getStatusResult() {
        return mWeiboApi.getPublicStatuses(WeiboApi.ACCESS_TOKEN, 200);
    }

    @Override
    public void saveStatusResult(StatusResult statusResult) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<List<Status>> getStatuses(int count, int page) {
        return getStatusResult().map(statusResult -> {
            int size = statusResult.getStatuses().size();
            int startIndex = Math.max(0, Math.min(count * page, size - 1));
            int endIndex = Math.max(startIndex, Math.min(count * (page + 1), size));
            Log.d(TAG, "size=" + size + ", start=" + startIndex + ", end=" + endIndex);
            return statusResult.getStatuses().subList(startIndex, endIndex);
        });

    }
}
