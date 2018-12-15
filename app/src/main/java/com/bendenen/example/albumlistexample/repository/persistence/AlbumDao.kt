package com.bendenen.example.albumlistexample.repository.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bendenen.example.albumlistexample.models.Album

@Dao
interface AlbumDao {

    @Query("SELECT * FROM album")
    fun queryAlbumList(): LiveData<List<Album>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(album: Album)
}