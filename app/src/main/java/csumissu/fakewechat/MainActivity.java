package csumissu.fakewechat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import csumissu.fakewechat.fragments.ChatFragment;
import csumissu.fakewechat.fragments.ContactsFragment;
import csumissu.fakewechat.fragments.ExploreFragment;
import csumissu.fakewechat.fragments.MeFragment;
import csumissu.fakewechat.util.ActivityUtils;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBnb;
    private static final String KEY_SELECTED_POSITION = "key_selected_position";
    private ArrayList<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int selectedPosition = savedInstanceState != null ? savedInstanceState.getInt(
                KEY_SELECTED_POSITION, 0) : 0;
        initViews(selectedPosition);
    }

    private void initViews(int selectedPosition) {
        mBnb.setMode(BottomNavigationBar.MODE_FIXED);
        mBnb.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBnb.addItem(new BottomNavigationItem(R.drawable.ic_donut_large_24dp, R.string.tab_chat)
                .setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_account_circle_24dp, R.string.tab_contacts)
                        .setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_explore_24dp, R.string.tab_explore)
                        .setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_face_24dp, R.string.tab_me)
                        .setActiveColorResource(R.color.brown))
                .setFirstSelectedPosition(selectedPosition)
                .initialise();
        mBnb.setTabSelectedListener(this);

        if (mFragment == null) {
            mFragment = new ArrayList<>();
            mFragment.add(new ChatFragment());
            mFragment.add(new ContactsFragment());
            mFragment.add(new ExploreFragment());
            mFragment.add(new MeFragment());
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                mFragment.get(selectedPosition), R.id.lay_frame);
    }

    @Override
    public void onTabSelected(int position) {
        if (mFragment != null && position < mFragment.size()) {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mFragment.get(position), R.id.lay_frame);
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (mFragment != null && position < mFragment.size()) {
            ActivityUtils.removeFragmentFromActivity(getSupportFragmentManager(),
                    mFragment.get(position));
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}
