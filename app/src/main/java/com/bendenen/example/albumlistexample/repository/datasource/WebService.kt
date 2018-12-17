package com.bendenen.example.albumlistexample.repository.datasource

import com.bendenen.example.albumlistexample.models.SearchArtistResponse
import com.bendenen.example.albumlistexample.models.TopAlbumsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("2.0/?method=artist.search&format=json")
    fun searchArtist(@Query("artist") name: String, @Query("api_key") apiKey: String):
            Call<SearchArtistResponse>

    @GET("2.0/?method=artist.gettopalbums&format=json")
    fun getTopAlbums(@Query("mbid") artistId: String, @Query("api_key") apiKey: String):
            Call<TopAlbumsResponse>
}