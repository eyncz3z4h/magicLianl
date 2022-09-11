package csumissu.fakewechat.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import csumissu.fakewechat.R;
import csumissu.fakewechat.data.User;

import static csumissu.fakewechat.util.Preconditions.checkNotNull;

/**
 * @author sunyaxi
 * @date 2016/6/27
 */
public class ContactsFragment extends Fragment implements ContactsContract.View {

    private ContactsContract.Presenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_tip)
    View mLoadingView;
    private ContactsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, rootView);
        // 禁止多点触发
        mRecyclerView.setMotionEventSplittingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                .colorResId(R.color.whiteSmoke)
                .sizeResId(R.dimen.divider)
                .marginResId(R.dimen.tile_divider_margin)
                .build());
        mAdapter = new ContactsAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void showLoading(boolean show) {
        mLoadingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showFriends(List<User> friends) {
        if (friends == null || friends.isEmpty()) {
            Toast.makeText(getContext(), R.string.no_friends, Toast.LENGTH_SHORT).show();
            return;
        }
        mAdapter.setData(friends);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(@NonNull ContactsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
