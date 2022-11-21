package csumissu.fakewechat.v2.model;

import csumissu.fakewechat.data.User;
import rx.Observable;

/**
 * @author sunyaxi
 * @date 2016/11/17.
 */
public interface UserDataSource {

    Observable<User> getOwner();

}
