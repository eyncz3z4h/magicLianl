package csumissu.fakewechat.comments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import csumissu.fakewechat.R;
import csumissu.fakewechat.common.PictureViewFragment;
import csumissu.fakewechat.data.Status;

import static csumissu.fakewechat.util.Preconditions.checkNotNull;

/**
 * @author syx
 * @date 2016/5/24.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Context mContext;
    private List<Status> mStatuses = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private FragmentManager mFragmentManager;

    public CommentsAdapter(Context context, FragmentManager fragmentManager) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mFragmentManager = fragmentManager;
    }

    public void setData(@NonNull List<Status> statuses) {
        mStatuses = checkNotNull(statuses);
    }

    public void addData(@NonNull List<Status> statuses) {
        mStatuses.addAll(checkNotNull(statuses));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.list_item_comments, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Status status = mStatuses.get(position);
        Glide.with(mContext).load(status.getSender().getImageUrl())
                .placeholder(R.drawable.ic_photo_placeholder)
                .into(holder.senderPhoto);
        holder.senderName.setText(status.getSender().getName());
        holder.statusContent.setText(status.getText());
        holder.statusPictures.setAdapter(new GridAdapter(status.getPicUrls()));
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
        @BindView(R.id.status_pictures)
        GridView statusPictures;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class GridAdapter extends BaseAdapter {

        private List<Status.Picture> pictures;

        public GridAdapter(@NonNull List<Status.Picture> pictures) {
            this.pictures = checkNotNull(pictures);
        }

        @Override
        public int getCount() {
            return pictures.size();
        }

        @Override
        public Object getItem(int position) {
            return pictures.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewHolder viewHolder;
            if (convertView != null) {
                viewHolder = (GridViewHolder) convertView.getTag(R.id.glide_tag);
            } else {
                convertView = mLayoutInflater.inflate(R.layout.grid_item_picture, parent, false);
                viewHolder = new GridViewHolder(convertView);
                convertView.setTag(R.id.glide_tag, viewHolder);
            }
            Status.Picture picture = (Status.Picture) getItem(position);
            Glide.with(mContext).load(picture.getThumbnail())
                    .placeholder(R.drawable.ic_photo_placeholder)
                    .into(viewHolder.photo);
            viewHolder.photo.setOnClickListener(view -> {
                PictureViewFragment.show(mFragmentManager, pictures, position);
            });
            return convertView;
        }

        class GridViewHolder {
            @BindView(R.id.status_photo)
            ImageView photo;

            public GridViewHolder(View itemView) {
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
