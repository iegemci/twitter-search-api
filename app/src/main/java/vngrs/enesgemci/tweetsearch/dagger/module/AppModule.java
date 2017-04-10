package vngrs.enesgemci.tweetsearch.dagger.module;

import android.app.Application;
import android.net.ConnectivityManager;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vngrs.enesgemci.tweetsearch.R;
import vngrs.enesgemci.tweetsearch.data.model.BasicAuthKey;
import vngrs.enesgemci.tweetsearch.util.L;

/**
 * Created by Aditya on 23-Oct-16.
 */

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    ConnectivityManager provideConnectivityManager() {
        return (ConnectivityManager) mApplication.getSystemService(Application.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    BasicAuthKey provideBasicAuthKey() {
        BasicAuthKey basicAuthKey = new BasicAuthKey();

        try {
            String key = mApplication.getString(R.string.consumer_key);
            String secret = mApplication.getString(R.string.consumer_secret);
            String combined = key + ":" + secret;

            basicAuthKey.setKey(Base64.encodeToString(combined.getBytes("UTF-8"), Base64.NO_WRAP));
        } catch (UnsupportedEncodingException e) {
            L.e(e);
        }

        return basicAuthKey;
    }
}
