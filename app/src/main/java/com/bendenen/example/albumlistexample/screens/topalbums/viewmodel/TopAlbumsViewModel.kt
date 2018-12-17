package com.bendenen.example.albumlistexample.screens.topalbums.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bendenen.example.albumlistexample.models.TopAlbumsResponse
import com.bendenen.example.albumlistexample.repository.TopAlbumsLiveDataProvider
import com.bendenen.example.albumlistexample.repository.datasource.Resource
import javax.inject.Inject

class TopAlbumsViewModel @Inject constructor(private val liveDataProvider: TopAlbumsLiveDataProvider) : ViewModel() {

    var initialized = false

    var artistId: MutableLiveData<String> = MutableLiveData()

    var deleteData: DeleteData? = null

    val topAlbumsResponse: LiveData<Resource<TopAlbumsResponse>> = Transformations.switchMap(artistId) { artistId ->
        initialized = true;
        if (deleteData != null) {
            val localData = deleteData!!
            liveDataProvider.deleteAlbums(localData.artistId, localData.albumName)
            deleteData = null
        }
        liveDataProvider.getTopAlbums(artistId)
    }

    data class DeleteData(
        val artistId: String,
        val albumName: String
    )

}