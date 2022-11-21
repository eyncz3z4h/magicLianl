package csumissu.fakewechat.v2.ui.splash;

import android.util.Log;

import javax.inject.Inject;

import csumissu.fakewechat.v2.model.UserRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author sunyaxi
 * @date 2016/11/17.
 */
public class SplashPresenter implements SplashContract.Presenter {

    private static final String TAG = SplashPresenter.class.getSimpleName();
    private final UserRepository mUserRepository;
    private final SplashContract.View mSplashView;

    @Inject
    SplashPresenter(UserRepository userRepository, SplashContract.View view) {
        Log.i(TAG, "splash presenter constructor");
        mUserRepository = userRepository;
        mSplashView = view;
    }

    @Inject
    void setupListeners() {
        Log.i(TAG, "splash presenter setup listeners");
        mSplashView.setPresenter(this);
    }

    @Override
    public void start() {
        mUserRepository.getOwner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    mSplashView.enterMainActivity();
                }, throwable -> {
                    Log.w(TAG, "get owner failed", throwable);
                    mSplashView.showError(throwable.getMessage());
                });
    }
}
