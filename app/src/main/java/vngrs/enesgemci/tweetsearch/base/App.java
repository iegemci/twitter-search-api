package vngrs.enesgemci.tweetsearch.base;

import android.app.Application;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import vngrs.enesgemci.tweetsearch.BuildConfig;
import vngrs.enesgemci.tweetsearch.dagger.component.AppComponent;
import vngrs.enesgemci.tweetsearch.dagger.component.DaggerAppComponent;
import vngrs.enesgemci.tweetsearch.dagger.module.AppModule;
import vngrs.enesgemci.tweetsearch.dagger.module.DataModule;
import vngrs.enesgemci.tweetsearch.util.L;
import vngrs.enesgemci.tweetsearch.util.MInjector;

/**
 * Created by enesgemci on 08/04/2017.
 */

public class App extends Application {

    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        MInjector.setUp(this);

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule(BuildConfig.HOST))
                .build();

        L.init(BuildConfig.DEBUG);

        Picasso.setSingletonInstance(new Picasso.Builder(this)
                .memoryCache(new LruCache(250000000))
                .build());
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}