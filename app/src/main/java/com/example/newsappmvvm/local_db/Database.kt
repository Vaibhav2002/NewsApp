package com.example.newsappmvvm.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsappmvvm.modelData.Article

@Database(entities = [Article::class], version = 5, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract val Dao: NewsDao
}