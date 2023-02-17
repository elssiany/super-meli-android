package com.kevinserrano.supermeli.lib.network

import com.google.gson.Gson
import com.kevinserrano.supermeli.BuildConfig
import com.kevinserrano.supermeli.lib.meli.network.MeliService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Reusable
    @Provides
    fun provideMeliApiRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Reusable
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(MeliService::class.java)
}
