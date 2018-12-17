package com.bendenen.example.albumlistexample.repository.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bendenen.example.albumlistexample.models.Album
import com.bendenen.example.albumlistexample.models.Artist

@Database(entities = [Album::class, Artist::class], version = 2)
abstract class Database : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    abstract fun artistDao(): ArtistDao
}