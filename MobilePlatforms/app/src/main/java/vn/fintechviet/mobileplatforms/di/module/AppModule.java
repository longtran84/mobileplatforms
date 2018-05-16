/*
 *  Copyright (C) 2018 - 2019 FINTECHVIET
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package vn.fintechviet.mobileplatforms.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.fintechviet.mobileplatforms.BuildConfig;
import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.data.AppDataManager;
import vn.fintechviet.mobileplatforms.data.DataManager;
import vn.fintechviet.mobileplatforms.data.local.db.AppDatabase;
import vn.fintechviet.mobileplatforms.data.local.db.AppDbHelper;
import vn.fintechviet.mobileplatforms.data.local.db.DbHelper;
import vn.fintechviet.mobileplatforms.data.local.prefs.AppPreferencesHelper;
import vn.fintechviet.mobileplatforms.data.local.prefs.PreferencesHelper;
import vn.fintechviet.mobileplatforms.data.remote.ApiHeader;
import vn.fintechviet.mobileplatforms.data.remote.ApiHelper;
import vn.fintechviet.mobileplatforms.data.remote.VietnamStateTreasuryService;
import vn.fintechviet.mobileplatforms.di.ApiInfo;
import vn.fintechviet.mobileplatforms.di.DatabaseInfo;
import vn.fintechviet.mobileplatforms.di.PreferenceInfo;
import vn.fintechviet.mobileplatforms.utils.AppConstants;
import vn.fintechviet.mobileplatforms.utils.AppLogger;
import vn.fintechviet.mobileplatforms.utils.Constants;
import vn.fintechviet.mobileplatforms.utils.Preference;
import vn.fintechviet.mobileplatforms.utils.rx.AppSchedulerProvider;
import vn.fintechviet.mobileplatforms.utils.rx.SchedulerProvider;

/**
 * Created by long_tran on 07/07/17.
 */
@Module
public class AppModule {

    private final String TAG = AppModule.class.getName();

    /**
     * auto add header to each request
     */
    private class HeaderInterceptor implements Interceptor {

        private Context context;
        private PreferencesHelper preferencesHelper;

        public HeaderInterceptor(Context context, PreferencesHelper preferencesHelper){
            this.context = context;
            this.preferencesHelper = preferencesHelper;
        }

        private Request request;

        @Override
        public Response intercept(final Chain chain)
                throws IOException {
            request = chain.request();
            if (!StringUtils.isBlank(preferencesHelper.getAuthorization())) {
                AppLogger.i(TAG, preferencesHelper.getAuthorization());
                request = request.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        //.addHeader("Token", headerToken)
                        .addHeader("Authorization", preferencesHelper.getAuthorization())
                        .build();
            }
            Response response = chain.proceed(request);
            //GetCountMessageByReadFlag?readFlag=0
            AppLogger.d(TAG, "Code : " + response.code());
            if (response.code() == 401) {
                // Magic is here ( Handle the error as your way )
                //sendBroadcastUnauthorized();
                return response;
            }
            return response;
        }
    }

    @Provides
    @Singleton
    LoggingInterceptor provideInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addQueryParam("apiKey", BuildConfig.API_KEY)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(LoggingInterceptor interceptor, Context context, PreferencesHelper preferencesHelper) {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new HeaderInterceptor(context, preferencesHelper))
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.END_POINT)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    VietnamStateTreasuryService provideVietnamStateTreasuryService(Retrofit retrofit) {
        return retrofit.create(VietnamStateTreasuryService.class);
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/helveticaneue.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
                                                           PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getCurrentUserId(),
                preferencesHelper.getAccessToken());
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
