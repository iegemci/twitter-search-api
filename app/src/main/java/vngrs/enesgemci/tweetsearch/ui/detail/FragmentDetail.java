package vngrs.enesgemci.tweetsearch.ui.detail;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

import butterknife.BindView;
import vngrs.enesgemci.tweetsearch.R;
import vngrs.enesgemci.tweetsearch.base.BaseFragment;
import vngrs.enesgemci.tweetsearch.dagger.component.AppComponent;
import vngrs.enesgemci.tweetsearch.data.model.Media;
import vngrs.enesgemci.tweetsearch.data.model.PhotoSize;
import vngrs.enesgemci.tweetsearch.data.model.Status;
import vngrs.enesgemci.tweetsearch.util.Page;

/**
 * Created by enesgemci on 08/04/2017.
 */

public class FragmentDetail extends BaseFragment<FragmentDetailView, FragmentDetailPresenter>
        implements FragmentDetailView {

    private static final String STATUS = "STATUS";

    @BindView(R.id.ivProfile)
    ImageView mProfileIv;

    @BindView(R.id.tvName)
    TextView mNameTv;

    @BindView(R.id.tvScreenName)
    TextView mScreenNameTv;

    @BindView(R.id.tvStatus)
    TextView mStatusTv;

    @BindView(R.id.ivMedia)
    ImageView mMediaIv;

    @BindView(R.id.tvDate)
    TextView mDateTv;

    private Status status;

    public static FragmentDetail newInstance(Object... objects) {
        Bundle args = new Bundle();

        if (objects != null) {
            args.putParcelable(STATUS, (Parcelable) objects[0]);
        }

        FragmentDetail fragment = new FragmentDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onFragmentStarted() {
        if (getArguments() != null && getArguments().containsKey(STATUS)) {
            status = getArguments().getParcelable(STATUS);

            Picasso.with(getContext())
                    .load(status.getUser().getProfileImageUrl())
                    .into(mProfileIv);

            mStatusTv.setText(status.getText());

            mNameTv.setText(status.getUser().getName());
            mScreenNameTv.setText("@" + status.getUser().getScreenName());

            DateTimeFormatter format = DateTimeFormat.forPattern("EE MMM dd HH:mm:ss Z yyyy").withLocale(Locale.ENGLISH);
            DateTime dateTime = format.parseDateTime(status.getCreatedAt());
            mDateTv.setText(dateTime.toString("HH:mm dd MMM yy"));

            if (status.getEntities().getMedia() != null && status.getEntities().getMedia().size() > 0) {
                Media userMedia = status.getEntities().getMedia().get(0);

                if (userMedia.getType().equals("photo")) {
                    int w = getResources().getDisplayMetrics().widthPixels;
                    PhotoSize photoSize = userMedia.getSizes().getLarge();

                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mMediaIv.getLayoutParams();
                    layoutParams.width = photoSize.getW() > w ? w : photoSize.getW();
                    layoutParams.height = photoSize.getH();
                    mMediaIv.setLayoutParams(layoutParams);

                    mMediaIv.setVisibility(View.VISIBLE);

                    Picasso.with(getContext())
                            .load(userMedia.getMediaUrl())
                            .resize(photoSize.getW(), photoSize.getH())
                            .centerInside()
                            .into(mMediaIv);
                } else {
                    mMediaIv.setVisibility(View.GONE);
                }
            } else {
                mMediaIv.setVisibility(View.GONE);
            }
        }
    }

    @NonNull
    @Override
    public Page getPage() {
        return Page.DETAIL;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    public void injectFragment(AppComponent component) {
        component.inject(this);
    }

    @Override
    public FragmentDetailPresenter createPresenter() {
        return new FragmentDetailPresenter(repository);
    }
}