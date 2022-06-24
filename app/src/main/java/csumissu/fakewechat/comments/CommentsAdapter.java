package csumissu.fakewechat.comments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import csumissu.fakewechat.R;
import csumissu.fakewechat.data.Status;

import static csumissu.fakewechat.util.Preconditions.checkNotNull;

/**
 * @author syx
 * @date 2016/5/24.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Context mContext;
    private List<Status> mStatuses = new ArrayList<>();

    public CommentsAdapter(Context context) {
        mContext = context;
    }

    public void setData(@NonNull List<Status> statuses) {
        mStatuses = checkNotNull(statuses);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_comments, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Status status = mStatuses.get(position);
        Glide.with(mContext).load(status.getSender().getImageUrl()).into(holder.senderPhoto);
        holder.senderName.setText(status.getSender().getName());
        holder.statusContent.setText(status.getText());
    }

    @Override
    public int getItemCount() {
        return mStatuses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sender_photo)
        ImageView senderPhoto;
        @BindView(R.id.sender_name)
        TextView senderName;
        @BindView(R.id.status_content)
        TextView statusContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
