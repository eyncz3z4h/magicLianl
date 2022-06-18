package csumissu.fakewechat.data.source.remote;

import csumissu.fakewechat.data.StatusResult;
import csumissu.fakewechat.data.User;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author sunyaxi
 * @date 2016/5/20
 */
public interface WeiboApi {

    String ACCESS_TOKEN = "2.00CWl4eCEgoNVE91f6c1c061uuYprB";
    long OWNER_UID = 2428983302L;

    @GET("2/users/show.json")
    Observable<User> getUser(@Query("access_token") String accessToken,
                             @Query("uid") long uid);

    @GET("2/statuses/public_timeline.json")
    Observable<StatusResult> getPublicStatuses(@Query("access_token") String accessToken,
                                               @Query("count") int count);

}
