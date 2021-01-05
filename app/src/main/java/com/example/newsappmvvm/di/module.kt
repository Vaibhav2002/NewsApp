package com.example.newsappmvvm.di

import android.content.Context
import androidx.room.Room
import com.example.newsappmvvm.Remote.NewsApi
import com.example.newsappmvvm.Util.BASE_URL
import com.example.newsappmvvm.local_db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object module {

    @Provides
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun providesApi(retrofit: Retrofit) = retrofit.create(NewsApi::class.java)

    @Provides
    fun providesDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NewsDatabase::class.java, "news_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesDao(database: NewsDatabase) = database.Dao

}