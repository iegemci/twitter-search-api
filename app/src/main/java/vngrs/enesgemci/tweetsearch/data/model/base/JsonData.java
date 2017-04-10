package vngrs.enesgemci.tweetsearch.data.model.base;

import vngrs.enesgemci.tweetsearch.util.Utils;

/**
 * Created by enesgemci on 08/04/2017.
 */

public class JsonData {

    @Override
    public String toString() {
        return Utils.toJson(this);
    }
}