package com.messon.noodoe.work.di.module

import android.app.Application
import android.content.Context
import com.messon.noodoe.work.module.Api
import com.messon.noodoe.work.repo.ServiceApi
import com.messon.noodoe.work.util.OkHttpUtil
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Api.API_DOMAIN)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpUtil.createOkHttpClient()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ServiceApi {
        return retrofit.create(ServiceApi::class.java)
    }
}
