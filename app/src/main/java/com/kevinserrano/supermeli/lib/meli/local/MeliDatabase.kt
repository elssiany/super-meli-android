package com.kevinserrano.supermeli.lib.meli.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kevinserrano.supermeli.lib.meli.model.ArticleViewedEntity

@Database(entities = [ArticleViewedEntity::class], version = 1, exportSchema = false)
abstract class MeliDatabase : RoomDatabase() {

    abstract fun articleViewedDao(): ArticleViewedDao

}

const val DATABASE_NAME = "db_super_meli_app"
