package vngrs.enesgemci.tweetsearch.network;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import vngrs.enesgemci.tweetsearch.data.model.AccessTokenResponse;
import vngrs.enesgemci.tweetsearch.data.model.SearchResponse;

/**
 * Created by enesgemci on 17/06/16.
 */

public interface RestService {

    @FormUrlEncoded
    @POST("oauth2/token")
    Observable<AccessTokenResponse> getAccessToken(@Header("Authorization") String authorization,
                                                   @Field("grant_type") String grantType);

    @GET("1.1/search/tweets.json")
    Observable<SearchResponse> getSearchResponse(@Header("Authorization") String authorization,
                                                 @Query(value = "q", encoded = true) String query,
                                                 @Query(value = "count") int count,
                                                 @Query(value = "max_id") long maxId);
}