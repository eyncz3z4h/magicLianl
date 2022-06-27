package csumissu.fakewechat.comments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import csumissu.fakewechat.R;
import csumissu.fakewechat.data.Status;
import csumissu.fakewechat.data.User;
import csumissu.fakewechat.widget.RecycleViewDivider;

import static csumissu.fakewechat.util.Preconditions.checkNotNull;

/**
 * @author sunyaxi
 * @date 2016/5/23
 */
public class CommentsFragment extends Fragment implements CommentsContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    private CommentsContract.Presenter mPresenter;
    private CommentsAdapter mAdapter;

    public CommentsFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CommentsAdapter(getContext());
        mPresenter.loadOwner();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull CommentsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);
        ButterKnife.bind(this, rootView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(),
                LinearLayoutManager.VERTICAL));
        return rootView;
    }

    @Override
    public void setRefreshIndicator(boolean active) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showOwner(User owner) {
        ((CommentsActivity) getActivity()).showOwnerInternal(owner);
    }

    @Override
    public void showStatuses(List<Status> statuses) {
        mAdapter.setData(statuses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mRefreshLayout, message, Snackbar.LENGTH_LONG).show();
    }

}
