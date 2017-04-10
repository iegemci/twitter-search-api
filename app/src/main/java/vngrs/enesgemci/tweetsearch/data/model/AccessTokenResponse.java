package vngrs.enesgemci.tweetsearch.data.model;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 08/04/2017.
 */

public class AccessTokenResponse extends JsonData {

    private String tokenType;
    private String accessToken;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}