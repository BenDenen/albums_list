package com.bendenen.example.albumlistexample.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bendenen.example.albumlistexample.R
import com.bendenen.example.albumlistexample.models.TopAlbumsResponse
import com.bendenen.example.albumlistexample.repository.datasource.Resource
import com.bendenen.example.albumlistexample.repository.datasource.WebService
import com.bendenen.example.albumlistexample.repository.persistence.AlbumDao
import com.bendenen.example.albumlistexample.utils.AppExecutors
import com.bendenen.example.albumlistexample.utils.Utils
import retrofit2.Call
import javax.inject.Inject

class TopAlbumsLiveDataProvider @Inject constructor(
    val webService: WebService,
    val utils: Utils,
    val appExecutors: AppExecutors,
    val context: Context,
    val albumDao: AlbumDao
) {
    val TAG = "SearchArtistProvider"

    open fun deleteAlbums(
        artistId: String,
        albumName: String
    ) {
        appExecutors.networkIO().execute {
            albumDao.deleteAlbumWithNameAndId(artistId, albumName)
        }
    }

    open fun getTopAlbums(artistId: String): LiveData<Resource<TopAlbumsResponse>> {
        return object : Repository<TopAlbumsResponse>(appExecutors) {

            override fun saveNetworkCallResult(data: TopAlbumsResponse?) {
                Log.d(TAG, "saveNetworkCallResult")
                data?.topalbums?.album?.forEach {
                    albumDao.insertAlbum(it)
                }
            }

            override fun shouldLoadFromNetwork(data: TopAlbumsResponse?): Boolean {
                val shouldLoadFromNetwork = utils.hasConnection() && (data == null || data.topalbums.album.isEmpty())
                Log.d(TAG, "shouldLoadFromNetwork: $shouldLoadFromNetwork")
                return shouldLoadFromNetwork
            }

            override fun loadFromDatabase(): LiveData<TopAlbumsResponse> {
                Log.d(TAG, "loadFromDatabase")
                val data = albumDao.queryAlbumList(artistId)
                return Transformations.map(data) {
                    TopAlbumsResponse(
                        TopAlbumsResponse.TopAlbums(
                            it
                        )
                    )
                }
            }

            override fun createNetworkCall(): Call<TopAlbumsResponse> {
                Log.d(TAG, "createNetworkCall")
                return webService.getTopAlbums(
                    artistId,
                    context.getString(R.string.last_fm_api_token)
                )
            }
        }.asLiveData()
    }

}