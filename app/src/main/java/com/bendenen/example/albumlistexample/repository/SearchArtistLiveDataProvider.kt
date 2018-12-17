package com.bendenen.example.albumlistexample.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bendenen.example.albumlistexample.R
import com.bendenen.example.albumlistexample.models.SearchArtistResponse
import com.bendenen.example.albumlistexample.repository.datasource.Resource
import com.bendenen.example.albumlistexample.repository.datasource.WebService
import com.bendenen.example.albumlistexample.repository.persistence.ArtistDao
import com.bendenen.example.albumlistexample.utils.AppExecutors
import com.bendenen.example.albumlistexample.utils.Utils
import retrofit2.Call
import javax.inject.Inject

class SearchArtistLiveDataProvider @Inject constructor(
    val webService: WebService,
    val utils: Utils,
    val appExecutors: AppExecutors,
    val context: Context,
    val artistDao: ArtistDao
) {
    val TAG = "SearchArtistProvider"

    open fun searchArtist(name: String): LiveData<Resource<SearchArtistResponse>> {
        return object : Repository<SearchArtistResponse>(appExecutors) {

            override fun saveNetworkCallResult(data: SearchArtistResponse?) {
                Log.d(TAG, "saveNetworkCallResult")
                data?.results?.artistmatches?.artist?.forEach {
                    artistDao.insertArtist(it)
                }
            }

            override fun shouldLoadFromNetwork(data: SearchArtistResponse?): Boolean = utils.hasConnection()

            override fun loadFromDatabase(): LiveData<SearchArtistResponse> {
                Log.d(TAG, "loadFromDatabase")
                val data = artistDao.queryArtistList("%$name%" )
                return Transformations.map(data) {
                    SearchArtistResponse(
                        SearchArtistResponse.Results(
                            SearchArtistResponse.Results.ArtistMatches(
                                it
                            )
                        )
                    )
                }
            }

            override fun createNetworkCall(): Call<SearchArtistResponse> {
                Log.d(TAG, "createNetworkCall")
                return webService.searchArtist(
                    name,
                    context.getString(R.string.last_fm_api_token)
                )
            }
        }.asLiveData()
    }

}