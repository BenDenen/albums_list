package com.bendenen.example.albumlistexample.di.modules

import androidx.lifecycle.ViewModel
import com.bendenen.example.albumlistexample.di.ViewModelKey
import com.bendenen.example.albumlistexample.screens.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel
}