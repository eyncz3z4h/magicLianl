package csumissu.fakewechat.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunyaxi
 * @date 2016/6/28
 */
public class TileAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private TileGapDelegate mGapDelegate;
    private TileMenuDelegate mMenuDelegate;
    private List mItems = new ArrayList<>();

    public TileAdapter(Context context) {
        mContext = context;
        mGapDelegate = new TileGapDelegate(context);
        mMenuDelegate = new TileMenuDelegate(context);
    }

    public void setData(List items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public int getItemViewType(int position) {
        if (mGapDelegate.isForViewType(mItems, position)) {
            return TileGapDelegate.TYPE_GAP;
        } else if (mMenuDelegate.isForViewType(mItems, position)) {
            return TileMenuDelegate.TYPE_MENU;
        }
        throw new IllegalArgumentException("No delegate found for position " + position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TileGapDelegate.TYPE_GAP:
                return mGapDelegate.onCreateViewHolder(parent);
            case TileMenuDelegate.TYPE_MENU:
                return mMenuDelegate.onCreateViewHolder(parent);
            default:
                throw new IllegalArgumentException("No delegate found for position " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TileGapDelegate.TYPE_GAP:
                mGapDelegate.onBindViewHolder(mItems, position, holder);
                break;
            case TileMenuDelegate.TYPE_MENU:
                mMenuDelegate.onBindViewHolder(mItems, position, holder);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
