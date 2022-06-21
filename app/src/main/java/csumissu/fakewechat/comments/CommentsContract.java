package csumissu.fakewechat.comments;

import java.util.List;

import csumissu.fakewechat.base.BasePresenter;
import csumissu.fakewechat.base.BaseView;
import csumissu.fakewechat.data.Status;
import csumissu.fakewechat.data.User;

/**
 * @author sunyaxi
 * @date 2016/5/23
 */
public interface CommentsContract {

    interface Presenter extends BasePresenter {

        void loadOwner();

        void loadAllStatuses(boolean forceUpdate);

        void loadStatuses(int page);

    }

    interface View extends BaseView<Presenter> {

        void setRefreshIndicator(boolean active);

        void setLoadingIndicator(boolean active);

        void showOwner(User owner);

        void showStatuses(List<Status> statuses);

        void showLoadingError();

    }
}
