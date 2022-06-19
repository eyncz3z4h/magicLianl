package csumissu.fakewechat.data.source.local;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

import csumissu.fakewechat.data.Status;
import csumissu.fakewechat.data.StatusResult;
import csumissu.fakewechat.data.User;
import csumissu.fakewechat.data.source.EntityDataSource;
import rx.Observable;

/**
 * @author sunyaxi
 * @date 2016/5/20
 */
public class LocalDataSource implements EntityDataSource {

    private static LocalDataSource INSTANCE;
    private Context mContext;
    private Gson mGson;
    private FileManager mFileManager;
    private static final String KEY_OWNER = "owner";
    private File mResultFile;

    private LocalDataSource(Context context) {
        mContext = context;
        mGson = new GsonBuilder()
                .setDateFormat("EEE MMM dd hh:mm:ss zzz yyyy")
                .serializeNulls()
                .create();
        mFileManager = FileManager.getInstance();
        mResultFile = new File(mContext.getFilesDir(), "result.json");
    }

    public synchronized static LocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public Observable<User> getOwner() {
        String owner = mFileManager.getFromPreferences(mContext, KEY_OWNER);
        if (TextUtils.isEmpty(owner)) {
            return Observable.empty();
        } else {
            return Observable.just(mGson.fromJson(owner, User.class));
        }
    }

    @Override
    public void saveOwner(User user) {
        mFileManager.writeToPreferences(mContext, KEY_OWNER, mGson.toJson(user));
    }

    @Override
    public Observable<StatusResult> getStatusResult() {
        String result = mFileManager.readFileContent(mResultFile);
        if (TextUtils.isEmpty(result)) {
            return Observable.empty();
        } else {
            return Observable.just(mGson.fromJson(result, StatusResult.class));
        }
    }

    @Override
    public void saveStatusResult(StatusResult statusResult) {
        mFileManager.writeToFile(mResultFile, mGson.toJson(statusResult));
    }

    @Override
    public Observable<List<Status>> getStatuses(int count, int page) {
        return getStatusResult().map(statusResult ->
                statusResult.getStatuses().subList(count * page, count * (page + 1)));
    }

}
