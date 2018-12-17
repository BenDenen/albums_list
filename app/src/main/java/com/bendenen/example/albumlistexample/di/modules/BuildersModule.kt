package com.bendenen.example.albumlistexample.di.modules

import com.bendenen.example.albumlistexample.screens.main.MainActivity
import com.bendenen.example.albumlistexample.screens.search.SearchActivity
import com.bendenen.example.albumlistexample.screens.topalbums.TopAlbumsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    abstract fun contributeTopAlbumsActivity(): TopAlbumsActivity
}