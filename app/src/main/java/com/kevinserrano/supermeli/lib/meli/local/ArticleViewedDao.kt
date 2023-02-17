package com.kevinserrano.supermeli.lib.meli.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kevinserrano.supermeli.lib.meli.model.ArticleViewedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleViewedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleViewed: ArticleViewedEntity):Long

    @Query("SELECT * FROM articleviewedentity ORDER BY timestamp ASC LIMIT 10")
    fun getLastViewedItems(): Flow<List<ArticleViewedEntity>>
}