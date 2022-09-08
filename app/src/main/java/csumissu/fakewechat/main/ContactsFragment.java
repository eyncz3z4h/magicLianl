package csumissu.fakewechat.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import csumissu.fakewechat.data.User;

import static csumissu.fakewechat.util.Preconditions.checkNotNull;

/**
 * @author sunyaxi
 * @date 2016/6/27
 */
public class ContactsFragment extends Fragment implements ContactsContract.View {

    private ContactsContract.Presenter mPresenter;
    private TextView mTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTextView = new TextView(getContext());
        mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mTextView.setText("ContactsFragment");
        return mTextView;
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

    }

    @Override
    public void showFriends(List<User> friends) {
        mTextView.setText(new Gson().toJson(friends));
    }

    @Override
    public void setPresenter(@NonNull ContactsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
