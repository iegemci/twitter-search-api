package vngrs.enesgemci.tweetsearch.dagger.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vngrs.enesgemci.tweetsearch.data.repository.Repository;
import vngrs.enesgemci.tweetsearch.data.repository.online.OnlineRepository;
import vngrs.enesgemci.tweetsearch.network.HeaderInterceptor;
import vngrs.enesgemci.tweetsearch.network.MExecuter;
import vngrs.enesgemci.tweetsearch.util.L;

/**
 * Created by Aditya on 23-Oct-16.
 */

@Module
public class DataModule {

    String mBaseUrl;

    public DataModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        SharedPreferences preferences;

        try {
            preferences = PreferenceManager.getDefaultSharedPreferences(application);

            // try writing
            preferences.edit().commit();
        } catch (Exception e) {
            L.e(e);
            String alternate = "preferences" + android.os.Process.myUid();
            preferences = application.getSharedPreferences(alternate, Context.MODE_PRIVATE);
        }

        return preferences;
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new HeaderInterceptor())
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .callbackExecutor(MExecuter.getInstance()::execute)
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }


//    @Provides
//    @Singleton
//    AppOfflineDataStore porvidesAppLocalDataStore() {
//        return new AppOfflineDataStore();
//    }

//    @Provides
//    @Singleton
//    OnlineRepository providesRepository() {
//        return new OnlineRepository();
//    }

    @Provides
    @Singleton
    Repository providesRepository() {
        return new OnlineRepository();
    }
}
