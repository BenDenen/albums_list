package com.bendenen.example.albumlistexample.repository.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bendenen.example.albumlistexample.models.Album

@Database(entities = [Album::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}