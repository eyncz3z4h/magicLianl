package csumissu.fakewechat.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import csumissu.fakewechat.AppConfig;
import csumissu.fakewechat.AppManager;
import csumissu.fakewechat.R;
import csumissu.fakewechat.util.DoubleClickExitHelper;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener,
        ViewPager.OnPageChangeListener {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavBar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private static final String KEY_SELECTED_POSITION = "key_selected_position";
    private ArrayList<Fragment> mFragment;
    private DoubleClickExitHelper mDoubleClickExitHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AppManager.getInstance().addActivity(this);
        mDoubleClickExitHelper = new DoubleClickExitHelper(this);

        int selectedPosition = savedInstanceState != null ? savedInstanceState.getInt(
                KEY_SELECTED_POSITION, 0) : 0;
        initViews(selectedPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (AppConfig.getBoolean(AppConfig.KEY_DOUBLE_CLICK_EXIT, true)) {
                return mDoubleClickExitHelper.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initViews(int selectedPosition) {
        mBottomNavBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavBar.addItem(new BottomNavigationItem(R.drawable.ic_donut_large_24dp, R.string.tab_chat)
                .setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_account_circle_24dp, R.string.tab_contacts)
                        .setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_explore_24dp, R.string.tab_explore)
                        .setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_face_24dp, R.string.tab_me)
                        .setActiveColorResource(R.color.brown))
                .setFirstSelectedPosition(selectedPosition)
                .initialise();
        mBottomNavBar.setTabSelectedListener(this);

        if (mFragment == null) {
            mFragment = new ArrayList<>();
            mFragment.add(new ChatFragment());
            mFragment.add(new ContactsFragment());
            mFragment.add(new ExploreFragment());
            mFragment.add(new MeFragment());
        }

        mViewPager.setAdapter(new TabViewPager(getSupportFragmentManager()));
        mViewPager.setCurrentItem(selectedPosition);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        mViewPager.setCurrentItem(position, true);
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mBottomNavBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    class TabViewPager extends FragmentPagerAdapter {

        public TabViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment.get(position);
        }

        @Override
        public int getCount() {
            return mFragment.size();
        }
    }
}
