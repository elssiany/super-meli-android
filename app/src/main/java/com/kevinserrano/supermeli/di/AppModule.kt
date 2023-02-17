package com.kevinserrano.supermeli.di

import android.content.Context
import androidx.room.Room
import com.kevinserrano.supermeli.lib.meli.local.ArticleViewedDao
import com.kevinserrano.supermeli.lib.meli.local.DATABASE_NAME
import com.kevinserrano.supermeli.lib.meli.local.MeliDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MeliDatabase {
        return Room.databaseBuilder(
            context, MeliDatabase::class.java,
            DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Reusable
    @Provides
    fun provideArticleViewedDao(database: MeliDatabase): ArticleViewedDao {
        return database.articleViewedDao()
    }
}