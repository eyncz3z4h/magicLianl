package csumissu.fakewechat.v2.ui.splash;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import csumissu.fakewechat.R;
import csumissu.fakewechat.v2.base.BaseActivity;
import csumissu.fakewechat.widget.ProgressWheel;

/**
 * @author sunyaxi
 * @date 2016/11/17.
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {

    private static final String TAG = SplashActivity.class.getSimpleName();
    @BindView(R.id.pw_spinner)
    ProgressWheel mProgressWheel;
    @Inject
    SplashPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initInject();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void enterMainActivity() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        Log.i(TAG, "set presenter " + presenter);
    }

    private void initInject() {
        Log.i(TAG, "init inject");
        DaggerSplashComponent.builder()
                .splashPresenterModule(new SplashPresenterModule(this))
                .appComponent(getApplicationComponent())
                .build()
                .inject(this);
        Log.w(TAG, "mPresenter " + mPresenter);
    }
}
