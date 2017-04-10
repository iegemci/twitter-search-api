package vngrs.enesgemci.tweetsearch.util;

import android.app.Activity;
import android.content.Context;

/**
 * Created by enesgemci on 15/06/16.
 */
public class MInjector {

    private static volatile Activity activityContext;
    private static volatile Context applicationContext;

    private MInjector() {
    }

    public static void inject(Activity act) {
        activityContext = act;
    }

    public static Context getActivityContext() {
        return activityContext;
    }

    public static void tearDown() {
        activityContext = null;
        applicationContext = null;
    }

    public static void setUp(Context ctx) {
        applicationContext = ctx;
    }

    public static Context getApplicationContext() {
        return applicationContext == null ? activityContext : applicationContext;
    }
}