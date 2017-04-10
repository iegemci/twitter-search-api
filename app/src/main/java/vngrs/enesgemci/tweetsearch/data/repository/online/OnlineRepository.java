package vngrs.enesgemci.tweetsearch.data.repository.online;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import vngrs.enesgemci.tweetsearch.base.App;
import vngrs.enesgemci.tweetsearch.data.model.AccessTokenResponse;
import vngrs.enesgemci.tweetsearch.data.model.SearchResponse;
import vngrs.enesgemci.tweetsearch.data.repository.Repository;
import vngrs.enesgemci.tweetsearch.network.RestService;

/**
 * Created by Aditya on 23-Oct-16.
 */

public class OnlineRepository extends Repository {

    @Inject
    Retrofit retrofit;

    private RestService restService;

    public OnlineRepository() {
        App.getAppComponent().inject(this);
        restService = retrofit.create(RestService.class);
    }

    @Override
    public Observable<AccessTokenResponse> getAccessToken(String authorization, String grantType) {
        return restService.getAccessToken(authorization, grantType);
    }

    @Override
    public Observable<SearchResponse> getSearchResponse(String authorization, String query,
                                                        int count, long maxId) {
        return restService.getSearchResponse(authorization, query, count, maxId);
    }
}