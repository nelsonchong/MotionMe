package com.example.motionme.di

import com.example.motionme.constant.Constant
import com.example.motionme.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ActivityComponent::class)
object NetworkModule {

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor(logger = {
            Timber.tag("###").d("\nit\n")
        })
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        val builder = OkHttpClient.Builder()
            .connectTimeout(Constant.CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(Constant.READ_TIMEOUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
        return builder.build()
    }

    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

}