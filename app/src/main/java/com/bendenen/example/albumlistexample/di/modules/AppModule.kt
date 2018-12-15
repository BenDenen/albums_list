package com.bendenen.example.albumlistexample.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bendenen.example.albumlistexample.repository.persistence.AlbumDao
import com.bendenen.example.albumlistexample.repository.persistence.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext


    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database =
        Room.databaseBuilder(app, Database::class.java, "last_fm_albums_db").build()

    @Provides
    @Singleton
    fun provideAlbumDao(db: Database): AlbumDao = db.albumDao()
}