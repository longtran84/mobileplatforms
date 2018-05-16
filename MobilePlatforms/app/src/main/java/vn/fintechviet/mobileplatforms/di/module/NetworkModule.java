///*
// *  Copyright (C) 2018 - 2019 FINTECHVIET
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// *
// *      https://mindorks.com/license/apache-v2
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License
// */
//
//package vn.fintechviet.mobileplatforms.di.module;
//
//
//import com.ihsanbal.logging.Level;
//import com.ihsanbal.logging.LoggingInterceptor;
//
//import javax.inject.Singleton;
//
//import dagger.Module;
//import dagger.Provides;
//import okhttp3.OkHttpClient;
//import okhttp3.internal.platform.Platform;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//import vn.fintechviet.mobileplatforms.BuildConfig;
//import vn.fintechviet.mobileplatforms.data.remote.VietnamStateTreasuryService;
//
//@Module
//public class NetworkModule {
//
//    @Provides
//    @Singleton
//    LoggingInterceptor provideInterceptor() {
//        return new LoggingInterceptor.Builder()
//                .loggable(BuildConfig.DEBUG)
//                .setLevel(Level.BASIC)
//                .log(Platform.INFO)
//                .request("Request")
//                .response("Response")
//                .addQueryParam("apiKey", BuildConfig.API_KEY)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    OkHttpClient provideOkHttp(LoggingInterceptor interceptor) {
//        return new OkHttpClient.Builder()
//                .addNetworkInterceptor(interceptor)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    Retrofit provideRetrofit(OkHttpClient client) {
//        return new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(BuildConfig.END_POINT)
//                .client(client)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    VietnamStateTreasuryService provideApi(Retrofit retrofit) {
//        return retrofit.create(VietnamStateTreasuryService.class);
//    }
//}
