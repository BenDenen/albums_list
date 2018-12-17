package com.bendenen.example.albumlistexample.screens.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bendenen.example.albumlistexample.models.SearchArtistResponse
import com.bendenen.example.albumlistexample.repository.SearchArtistLiveDataProvider
import com.bendenen.example.albumlistexample.repository.datasource.Resource
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val liveDataProvider: SearchArtistLiveDataProvider) : ViewModel() {

    var initialized = false

    var artistName: MutableLiveData<String> = MutableLiveData()

    val searchResponse: LiveData<Resource<SearchArtistResponse>> = Transformations.switchMap(artistName) { artistName ->
        initialized = true; liveDataProvider.searchArtist(artistName)
    }

}