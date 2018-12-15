package com.bendenen.example.albumlistexample.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.bendenen.example.albumlistexample.models.Album
import com.bendenen.example.albumlistexample.repository.datasource.Resource
import com.bendenen.example.albumlistexample.repository.persistence.AlbumDao
import com.bendenen.example.albumlistexample.utils.AppExecutors
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple wrapper for DB call.
 * We do not use the network call for now but we keep all interfaces in case we add extra logic for retriving user-specific data
 */
@Singleton
open class SavedAlbumsLiveDataProvider @Inject constructor(
    val albumDao: AlbumDao,
    val appExecutors: AppExecutors
) {
    val TAG = "SavedAlbumsProvider"

    open fun getSavedAlbumList(): LiveData<Resource<List<Album>>> {
        return object : Repository<List<Album>>(appExecutors) {

            override fun saveNetworkCallResult(data: List<Album>?) {
                Log.e(TAG, "should not be call")
            }

            override fun shouldLoadFromNetwork(data: List<Album>?): Boolean {
                // For now we do not use Network call
                return false
            }

            override fun loadFromDatabase(): LiveData<List<Album>> {
                Log.d(TAG, "loadFromDatabase")
                return albumDao.queryAlbumList()
            }

            override fun createNetworkCall(): Call<List<Album>>? {
                // We do not use network call for now. Show only stored data
                Log.e(TAG, "should not be call")
                return null
            }
        }.asLiveData()
    }
}