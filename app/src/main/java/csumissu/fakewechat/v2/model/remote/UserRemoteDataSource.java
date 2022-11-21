package csumissu.fakewechat.v2.model.remote;

import javax.inject.Singleton;

import csumissu.fakewechat.data.User;
import csumissu.fakewechat.v2.api.ApiClient;
import csumissu.fakewechat.v2.api.ApiConstants;
import csumissu.fakewechat.v2.api.ApiService;
import csumissu.fakewechat.v2.model.UserDataSource;
import rx.Observable;

/**
 * @author sunyaxi
 * @date 2016/11/17.
 */
@Singleton
public class UserRemoteDataSource implements UserDataSource {

    private ApiService mApiService;

    public UserRemoteDataSource() {
        mApiService = ApiClient.create(ApiService.class);
    }

    @Override
    public Observable<User> getOwner() {
        return mApiService.getUser(ApiConstants.ACCESS_TOKEN, ApiConstants.OWNER_UID);
    }
}
