package com.bendenen.example.albumlistexample.screens.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bendenen.example.albumlistexample.models.Album
import com.bendenen.example.albumlistexample.repository.SavedAlbumsLiveDataProvider
import com.bendenen.example.albumlistexample.repository.datasource.Resource
import javax.inject.Inject

class MainViewModel @Inject constructor(private val liveDataProvider: SavedAlbumsLiveDataProvider) : ViewModel() {

    val carDescriptions: LiveData<Resource<List<Album>>> = liveDataProvider.getSavedAlbumList()

}