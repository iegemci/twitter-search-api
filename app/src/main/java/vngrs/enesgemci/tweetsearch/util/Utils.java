package vngrs.enesgemci.tweetsearch.util;

import com.google.gson.Gson;

/**
 * Created by enesgemci on 08/04/2017.
 */

public final class Utils {

    private static final Gson GSON = new Gson();

    public static String toJson(Object o) {
        return GSON.toJson(o);
    }
}
