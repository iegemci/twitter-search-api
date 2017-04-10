package vngrs.enesgemci.tweetsearch.data.model;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class BasicAuthKey extends JsonData {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
