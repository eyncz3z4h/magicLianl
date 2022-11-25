package csumissu.fakewechat.v2.base;

import com.trello.rxlifecycle.components.support.RxFragment;

import csumissu.fakewechat.v2.AppComponent;
import csumissu.fakewechat.v2.WechatApp;

/**
 * @author sunyaxi
 * @date 2016/11/19.
 */
public class BaseFragment extends RxFragment {

    protected AppComponent getApplicationComponent() {
        return ((WechatApp) getActivity().getApplication()).component();
    }
}
