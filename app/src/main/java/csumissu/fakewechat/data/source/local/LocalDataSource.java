package csumissu.fakewechat.data.source.local;

import android.content.Context;

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

    private LocalDataSource(Context context) {
        mContext = context;
    }

    public synchronized static LocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public Observable<User> getOwner() {
        return null;
    }

    @Override
    public Observable<StatusResult> getStatusResult() {
        return null;
    }

    @Override
    public void saveStatusResult(StatusResult statusResult) {

    }

    @Override
    public Observable<List<Status>> getStatuses(int count, int page) {
        return null;
    }
}
