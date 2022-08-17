package csumissu.fakewechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import csumissu.fakewechat.widget.ProgressWheel;

/**
 * @author sunyaxi
 * @date 2016/6/27
 */
public class StartActivity extends AppCompatActivity {

    private static final int MSG_INIT = 0;
    private static final int MSG_LOOP = 1;
    private static final int MSG_MAIN = 2;
    private final MyHandler mHandler = new MyHandler(this);
    @BindView(R.id.pw_spinner)
    ProgressWheel mProgressWheel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = AppManager.getInstance().getActivity(MainActivity.class);
        if (activity != null && !activity.isFinishing()) {
            finish();
        }

        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        mProgressWheel.setOnClickListener(view -> {
            mHandler.removeMessages(MSG_LOOP);
            mHandler.sendEmptyMessage(MSG_MAIN);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.sendEmptyMessage(MSG_INIT);
        mHandler.sendEmptyMessage(MSG_LOOP);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeMessages(MSG_LOOP);
    }

    private static class MyHandler extends Handler {
        private final WeakReference<StartActivity> iActivity;
        private int iProgress = 0;

        public MyHandler(StartActivity activity) {
            iActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            StartActivity activity = iActivity.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case MSG_INIT:
                    iProgress = 0;
                    break;
                case MSG_LOOP:
                    System.out.println(iProgress);
                    if (iProgress > 360) {
                        sendEmptyMessage(MSG_MAIN);
                    } else {
                        iProgress += 2;
                        activity.mProgressWheel.setProgress(iProgress);
                        sendEmptyMessageDelayed(MSG_LOOP, 10);
                    }
                    break;
                case MSG_MAIN:
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                    break;
            }
        }
    }
}
