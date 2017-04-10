package vngrs.enesgemci.tweetsearch.data.repository.offline;

import rx.Observable;
import vngrs.enesgemci.tweetsearch.data.model.AccessTokenResponse;
import vngrs.enesgemci.tweetsearch.data.model.SearchResponse;
import vngrs.enesgemci.tweetsearch.network.RestService;

/**
 * Created by Aditya on 23-Oct-16.
 */

public class AppOfflineDataStore implements RestService {

    public AppOfflineDataStore() {
    }

    @Override
    public Observable<AccessTokenResponse> getAccessToken(String authorization, String grantType) {
        // TODO: 08/04/2017 from realm
        return null;
    }

    @Override
    public Observable<SearchResponse> getSearchResponse(String authorization, String query,
                                                        int count, long maxId) {
        // TODO: 08/04/2017 from realm
        return null;
    }
}