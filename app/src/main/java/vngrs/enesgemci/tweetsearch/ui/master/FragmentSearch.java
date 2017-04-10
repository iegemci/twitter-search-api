package vngrs.enesgemci.tweetsearch.ui.master;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnTextChanged;
import vngrs.enesgemci.tweetsearch.R;
import vngrs.enesgemci.tweetsearch.adapter.AdapterSearch;
import vngrs.enesgemci.tweetsearch.base.BaseFragment;
import vngrs.enesgemci.tweetsearch.dagger.component.AppComponent;
import vngrs.enesgemci.tweetsearch.data.model.BasicAuthKey;
import vngrs.enesgemci.tweetsearch.data.model.Status;
import vngrs.enesgemci.tweetsearch.util.Page;
import vngrs.enesgemci.tweetsearch.util.endlessrecyclerview.Endless;

/**
 * Created by enesgemci on 08/04/2017.
 */

public class FragmentSearch extends BaseFragment<FragmentSearchView, FragmentSearchPresenter>
        implements FragmentSearchView, View.OnClickListener {

    private static final int ITEM_PER_PAGE = 10;

    @BindView(R.id.etSearch)
    EditText mSearchEt;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.tvEmpty)
    TextView mEmptyTv;

    @BindView(R.id.tvNoRecord)
    TextView mNoRecordTv;

    @Nullable
    @BindView(R.id.flChildContainer)
    FrameLayout mChildContainer;

    @Inject
    BasicAuthKey basicAuthKey;

    private Endless endless;
    private AdapterSearch adapterSearch;

    public static FragmentSearch newInstance(Object... objects) {
        Bundle args = new Bundle();

        FragmentSearch fragment = new FragmentSearch();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onFragmentStarted() {
        getPresenter().onFragmentStarted();

        RecyclerView.LayoutManager layoutManager;

        if (isTablet()) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getContext());
            ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        }

        mRecyclerView.setLayoutManager(layoutManager);

        ProgressBar progressBar = (ProgressBar) View.inflate(getContext(), R.layout.layout_loader_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressBar.setLayoutParams(layoutParams);
        endless = Endless.applyTo(mRecyclerView, progressBar);
        endless.setAdapter(adapterSearch = new AdapterSearch(getContext(), this));
        endless.setLoadMoreListener(page -> getPresenter().loadMore());
    }

    @NonNull
    @Override
    public Page getPage() {
        return Page.DASHBOARD;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void injectFragment(AppComponent component) {
        component.inject(this);
    }

    @Override
    public FragmentSearchPresenter createPresenter() {
        return new FragmentSearchPresenter(repository);
    }

    @OnTextChanged(callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED, value = R.id.etSearch)
    void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {
            mEmptyTv.setVisibility(View.GONE);
            getPresenter().search();
        } else {
            setResult(new ArrayList<>(), false);
        }
    }

    @Override
    public String getSearchText() {
        return mSearchEt.getText().toString();
    }

    @Override
    public BasicAuthKey getBasicAuthKey() {
        return basicAuthKey;
    }

    @Override
    public String getAuthTokenKey() {
        return getString(R.string.auth_token_key);
    }

    @Override
    public void setResult(List<Status> statusList, boolean loadMore) {
        adapterSearch.loadData(statusList, loadMore);
        endless.loadMoreComplete();
        endless.setLoadMoreAvailable(statusList.size() == ITEM_PER_PAGE);

        if (mSearchEt.length() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mNoRecordTv.setVisibility(View.GONE);
            mEmptyTv.setVisibility(View.VISIBLE);
        } else if (statusList.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            mNoRecordTv.setVisibility(View.VISIBLE);
        } else {
            mNoRecordTv.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return ITEM_PER_PAGE;
    }

    @Override
    public int getContainerId() {
        return isTablet() ? mChildContainer.getId() : -1;
    }

    @Override
    public FragmentManager getFM() {
        return isTablet() ? getChildFragmentManager() : null;
    }

    @Override
    public void onClick(View v) {
        if (isTablet()) {
            mChildContainer.setVisibility(View.VISIBLE);
            ((GridLayoutManager) mRecyclerView.getLayoutManager()).setSpanCount(1);
        }

        getPresenter().onItemClicked((Status) v.getTag());
    }
}