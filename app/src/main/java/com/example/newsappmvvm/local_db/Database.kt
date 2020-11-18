package com.example.newsappmvvm.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsappmvvm.modelData.Article

@Database(entities = [Article::class],version = 1,exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract val Dao:NewsDao
    companion object {
        @Volatile
        var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context):NewsDatabase{
            synchronized(this){
                var instance= INSTANCE
                if(instance==null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java,
                        "news_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE=instance
                }

                return instance
            }
        }



    }


}