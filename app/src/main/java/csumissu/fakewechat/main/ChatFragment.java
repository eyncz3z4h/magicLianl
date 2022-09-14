package csumissu.fakewechat.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import csumissu.fakewechat.R;
import csumissu.fakewechat.data.User;
import csumissu.fakewechat.main.ChatAdapter.Tweet;

/**
 * @author sunyaxi
 * @date 2016/6/27
 */
public class ChatFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private User mRobotUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, rootView);
        // 禁止多点触发
        mRecyclerView.setMotionEventSplittingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mAdapter = new ChatAdapter(getActivity());
        mAdapter.setData(getDefaultData());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private List<Tweet> getDefaultData() {
        mRobotUser = new User();
        mRobotUser.setUid(1234567890);
        mRobotUser.setName("小微");
        mRobotUser.setImageUrl("android.resource://" + getContext().getPackageName() + "/" + R.drawable.ic_robot);
        mRobotUser.setGender("f");

        List<Tweet> tweets = new ArrayList<>();
        Tweet tweet = new Tweet();
        tweet.content = "您好，我是聊天机器人小微！我能复述您说的话！";
        tweet.sender = mRobotUser;
        tweets.add(tweet);
        return tweets;
    }

}
