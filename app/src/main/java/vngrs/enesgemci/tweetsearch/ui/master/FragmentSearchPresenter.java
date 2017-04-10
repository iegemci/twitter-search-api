package vngrs.enesgemci.tweetsearch.ui.master;

import com.squareup.otto.Subscribe;

import vngrs.enesgemci.tweetsearch.base.mvp.BaseMvpPresenter;
import vngrs.enesgemci.tweetsearch.data.model.AccessTokenResponse;
import vngrs.enesgemci.tweetsearch.data.model.SearchResponse;
import vngrs.enesgemci.tweetsearch.data.model.Status;
import vngrs.enesgemci.tweetsearch.data.repository.Repository;
import vngrs.enesgemci.tweetsearch.network.MRequest;
import vngrs.enesgemci.tweetsearch.network.ServiceConstant;
import vngrs.enesgemci.tweetsearch.util.Page;
import vngrs.enesgemci.tweetsearch.util.Preferences;
import vngrs.enesgemci.tweetsearch.util.TransactionAnimation;

/**
 * Created by enesgemci on 08/04/2017.
 */

class FragmentSearchPresenter extends BaseMvpPresenter<FragmentSearchView> {

    private SearchResponse searchResponse;

    FragmentSearchPresenter(Repository repository) {
        super(repository);
    }

    void onFragmentStarted() {
        getView().sendRequest(new MRequest(repository.getAccessToken(
                "Basic " + getView().getBasicAuthKey().getKey(),
                "client_credentials"),
                ServiceConstant.AccessToken));
    }

    void search() {
        getView().sendRequest(new MRequest(repository.getSearchResponse(
                "Bearer " + Preferences.getString(getView().getAuthTokenKey()),
                getView().getSearchText(),
                getView().getItemCount(),
                Long.MAX_VALUE), ServiceConstant.Search));
    }

    void loadMore() {
        int lastIndex = searchResponse.getStatuses().size() - 1;
        long maxId = lastIndex >= 0 ? searchResponse.getStatuses().get(lastIndex).getId() - 1 : Long.MAX_VALUE;

        getView().sendRequest(new MRequest(repository.getSearchResponse(
                "Bearer " + Preferences.getString(getView().getAuthTokenKey()),
                getView().getSearchText(),
                getView().getItemCount(), maxId),
                ServiceConstant.Search));
    }

    @Subscribe
    public void onResponse(AccessTokenResponse accessTokenResponse) {
        if (isViewAttached()) {
            Preferences.setString(getView().getAuthTokenKey(), accessTokenResponse.getAccessToken());
        }
    }

    @Subscribe
    public void onResponse(SearchResponse response) {
        if (isViewAttached()) {
            if (getView().getSearchText().equals(response.getSearchMetadata().getQuery())) {
                getView().setResult(response.getStatuses(),
                        searchResponse != null
                                && searchResponse.getSearchMetadata().getQuery().equals(
                                response.getSearchMetadata().getQuery()));
            }

            this.searchResponse = response;
        }
    }

    void onItemClicked(Status status) {
        getView().showPage(getView().getPage(Page.DETAIL, status)
                .setContainerId(getView().getContainerId())
                .setFragmentManager(getView().getFM())
                .setTransactionAnimation(getView().isTablet()
                        ? TransactionAnimation.NO_ANIM
                        : TransactionAnimation.ENTER_FROM_RIGHT));
    }
}