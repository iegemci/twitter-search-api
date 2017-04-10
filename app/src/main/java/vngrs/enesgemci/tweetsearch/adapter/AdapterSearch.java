package vngrs.enesgemci.tweetsearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vngrs.enesgemci.tweetsearch.R;
import vngrs.enesgemci.tweetsearch.data.model.Media;
import vngrs.enesgemci.tweetsearch.data.model.PhotoSize;
import vngrs.enesgemci.tweetsearch.data.model.Status;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {

    private final Context context;
    private LayoutInflater layoutInflater;
    private View.OnClickListener onClickListener;
    private final List<Status> statusList;

    public AdapterSearch(Context context, View.OnClickListener onClickListener) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onClickListener = onClickListener;
        this.statusList = new ArrayList<>();
        this.context = context;
    }

    public void loadData(List<Status> list, boolean loadMore) {
        if (!loadMore) {
            statusList.clear();
        }

        statusList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_search, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(statusList.get(position));
    }

    @Override
    public int getItemCount() {
        return statusList == null ? 0 : statusList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivProfile)
        ImageView profile;

        @BindView(R.id.ivMedia)
        ImageView media;

        @BindView(R.id.tvStatus)
        TextView status;

        @BindView(R.id.llRoot)
        LinearLayout root;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setData(Status data) {
            Picasso.with(context)
                    .load(data.getUser().getProfileImageUrl())
                    .into(profile);

            status.setText(data.getText());
            root.setTag(data);

            if (data.getEntities().getMedia() != null && data.getEntities().getMedia().size() > 0) {
                Media userMedia = data.getEntities().getMedia().get(0);

                if (userMedia.getType().equals("photo")) {
                    PhotoSize photoSize = userMedia.getSizes().getSmall();
                    int w = context.getResources().getDisplayMetrics().widthPixels;

                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) media.getLayoutParams();
                    layoutParams.width = photoSize.getW() > w ? w : photoSize.getW();
                    layoutParams.height = photoSize.getH();
                    media.setLayoutParams(layoutParams);

                    media.setVisibility(View.VISIBLE);

                    Picasso.with(context)
                            .load(userMedia.getMediaUrl())
                            .resize(photoSize.getW(), photoSize.getH())
                            .into(media);
                } else {
                    media.setVisibility(View.GONE);
                }
            } else {
                media.setVisibility(View.GONE);
            }
        }

        @OnClick(R.id.llRoot)
        void onClick() {
            if (onClickListener != null) {
                onClickListener.onClick(root);
            }
        }
    }
}
