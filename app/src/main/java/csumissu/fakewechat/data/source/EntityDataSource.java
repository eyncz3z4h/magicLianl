package csumissu.fakewechat.data.source;

import java.util.List;

import csumissu.fakewechat.data.Status;
import csumissu.fakewechat.data.StatusResult;
import csumissu.fakewechat.data.User;
import rx.Observable;

/**
 * @author sunyaxi
 * @date 2016/5/20
 */
public interface EntityDataSource {

    Observable<User> getOwner();

    Observable<StatusResult> getStatusResult();

    void saveStatusResult(StatusResult statusResult);

    Observable<List<Status>> getStatuses(int count, int page);
}
