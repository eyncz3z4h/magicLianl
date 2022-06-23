package csumissu.fakewechat.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import csumissu.fakewechat.R;
import csumissu.fakewechat.data.source.EntityRepository;
import csumissu.fakewechat.data.source.local.LocalDataSource;
import csumissu.fakewechat.data.source.remote.RemoteDataSource;
import csumissu.fakewechat.util.ActivityUtils;

/**
 * @author sunyaxi
 * @date 2016/5/23
 */
public class CommentsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        CommentsFragment fragment = (CommentsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = new CommentsFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.content_frame);
        }

        CommentsPresenter presenter = new CommentsPresenter(fragment,
                EntityRepository.getInstance(LocalDataSource.getInstance(this),
                        RemoteDataSource.getInstance()));
        fragment.setPresenter(presenter);
    }

}
