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
    fun queryAllAlbumList(): LiveData<List<Album>>

    @Query("SELECT * FROM album WHERE artist_mbid LIKE :artistId")
    fun queryAlbumList(artistId: String): LiveData<List<Album>>

    @Query("DELETE FROM album WHERE artist_mbid LIKE :artistId AND name LIKE :albumName")
    fun deleteAlbumWithNameAndId(artistId: String, albumName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(album: Album)
}