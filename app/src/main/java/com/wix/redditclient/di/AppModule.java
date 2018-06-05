package com.wix.redditclient.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wix.redditclient.repository.Data;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private static final int MAX_SIZE = 10 * 1024 * 1024;
    private static final int MAX_STALE = 60 * 60 * 24;

    @Provides
    Context providesContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "httpCache");
        Cache cache = new Cache(httpCacheDirectory, MAX_SIZE);

        return new Retrofit.Builder()
                .baseUrl(Data.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .cache(cache)
                        .addInterceptor(chain -> {
                            try {
                                return chain.proceed(chain.request());
                            } catch (Exception e) {
                                Request offlineRequest = chain.request().newBuilder()
                                        .header("Cache-Control", "public, only-if-cached," +
                                                "max-stale=" + MAX_STALE)
                                        .build();
                                return chain.proceed(offlineRequest);
                            }
                        })
                        .build())
                .build();
    }
}
