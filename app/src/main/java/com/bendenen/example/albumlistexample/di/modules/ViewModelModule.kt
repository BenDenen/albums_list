package com.bendenen.example.albumlistexample.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bendenen.example.albumlistexample.di.ViewModelKey
import com.bendenen.example.albumlistexample.mvvm.ViewModelFactory
import com.bendenen.example.albumlistexample.screens.main.viewmodel.MainViewModel
import com.bendenen.example.albumlistexample.screens.search.viewmodel.SearchViewModel
import com.bendenen.example.albumlistexample.screens.topalbums.viewmodel.TopAlbumsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TopAlbumsViewModel::class)
    internal abstract fun bindTopAlbumsViewModel(topAlbumsViewModel: TopAlbumsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}