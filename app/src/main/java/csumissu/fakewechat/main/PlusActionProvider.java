package csumissu.fakewechat.main;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.SubMenu;
import android.view.View;

import csumissu.fakewechat.R;

/**
 * @author sunyaxi
 * @date 2016/6/29
 */
public class PlusActionProvider extends ActionProvider {

    private Context mContext;

    public PlusActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add(mContext.getString(R.string.add_new_friend))
                .setIcon(R.drawable.ic_menu_friend)
                .setOnMenuItemClickListener(null);
        subMenu.add(mContext.getString(R.string.scan_qr_code))
                .setIcon(R.drawable.ic_menu_qrcode);
        subMenu.add(mContext.getString(R.string.receive_payment))
                .setIcon(R.drawable.ic_menu_card);
        subMenu.add(mContext.getString(R.string.help_and_feedback))
                .setIcon(R.drawable.ic_menu_feedback);
    }
}
