package com.bendenen.example.albumlistexample.repository.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bendenen.example.albumlistexample.models.Artist

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artist")
    fun queryAllArtistList(): LiveData<List<Artist>>

    @Query("SELECT * FROM artist WHERE name LIKE :name")
    fun queryArtistList(name:String): LiveData<List<Artist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtist(artist: Artist)

    @Query("DELETE FROM artist")
    fun deletAllArtisit()
}