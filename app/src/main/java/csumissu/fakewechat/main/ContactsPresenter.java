package csumissu.fakewechat.main;

import android.support.annotation.NonNull;

import csumissu.fakewechat.data.source.EntityRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static csumissu.fakewechat.util.Preconditions.checkNotNull;

/**
 * @author sunyaxi
 * @date 2016/6/30
 */
public class ContactsPresenter implements ContactsContract.Presenter {

    private ContactsContract.View mView;
    private final EntityRepository mRepository;

    public ContactsPresenter(@NonNull ContactsContract.View view,
                             @NonNull EntityRepository repository) {
        mView = checkNotNull(view);
        mRepository = checkNotNull(repository);
    }

    @Override
    public void loadAllFriends() {
        mRepository.getFriends()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> mView.showLoading(true))
                .doOnTerminate(() -> mView.showLoading(false))
                .subscribe(users -> {
                    mView.showFriends(users);
                }, throwable -> {
                    System.out.println(throwable);
                });
    }

    @Override
    public void start() {
        loadAllFriends();
    }
}
