package com.kevinserrano.supermeli.lib.meli.di

import android.content.Context
import com.google.gson.Gson
import com.kevinserrano.supermeli.lib.meli.local.ArticleViewedDao
import com.kevinserrano.supermeli.lib.meli.network.MeliService
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepository
import com.kevinserrano.supermeli.lib.meli.repository.MeliRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideMeliRepository(
        gson: Gson,
        @ApplicationContext context: Context,
        articleViewedDao: ArticleViewedDao,
        meliService: MeliService
    ): MeliRepository {
        return MeliRepositoryImpl(gson, context, articleViewedDao, meliService)
    }
}