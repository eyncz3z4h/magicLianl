package csumissu.fakewechat.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import csumissu.fakewechat.R;
import csumissu.fakewechat.data.Status;
import me.relex.circleindicator.CircleIndicator;

/**
 * @author sunyaxi
 * @date 2016/5/25
 */
public class PictureViewFragment extends DialogFragment {

    public static final String KEY_PICTURES = "key_pictures";
    public static final String KEY_SELECTED = "key_selected";

    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;

    public PictureViewFragment() {
        // Empty constructor required for DialogFragment
    }

    public static PictureViewFragment show(FragmentManager fragmentManager, List<Status.Picture> pictures) {
        return show(fragmentManager, pictures, 0);
    }

    public static PictureViewFragment show(FragmentManager fragmentManager, List<Status.Picture> pictures,
                                           int selectedIndex) {
        PictureViewFragment fragment = new PictureViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_PICTURES, (Serializable) pictures);
        args.putInt(KEY_SELECTED, selectedIndex);
        fragment.setArguments(args);
        fragment.show(fragmentManager, "picture_view_fragment");
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        List<Status.Picture> pictures = (List<Status.Picture>) args.getSerializable(KEY_PICTURES);
        int selectedIndex = args.getInt(KEY_SELECTED);

        View rootView = inflater.inflate(R.layout.fragment_picture_view, container, false);
        ButterKnife.bind(this, rootView);
        mViewpager.setAdapter(new PicturePagerAdapter(pictures));
        mViewpager.setCurrentItem(selectedIndex);
        mIndicator.setViewPager(mViewpager);
        return rootView;
    }

    class PicturePagerAdapter extends PagerAdapter {

        @BindView(R.id.middle_picture)
        ImageView middlePicture;
        @BindView(R.id.thumbnail_picture)
        ImageView thumbnailPicture;
        @BindView(R.id.loading_container)
        FrameLayout loadingContainer;
        private List<Status.Picture> iPictures;
        private LayoutInflater iLayoutInflater;

        public PicturePagerAdapter(List<Status.Picture> pictures) {
            iPictures = pictures;
            iLayoutInflater = LayoutInflater.from(getContext());
        }

        @Override
        public int getCount() {
            return iPictures.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View rootView = iLayoutInflater.inflate(R.layout.pager_picture_view, container, false);
            ButterKnife.bind(this, rootView);
            Status.Picture picture = iPictures.get(position);
            Glide.with(getContext()).load(picture.getMiddle()).crossFade().into(middlePicture);
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
