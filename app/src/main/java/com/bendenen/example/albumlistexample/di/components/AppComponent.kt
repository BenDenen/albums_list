package com.bendenen.example.albumlistexample.di.components

import com.bendenen.example.albumlistexample.AlbumListExampleApp
import com.bendenen.example.albumlistexample.di.modules.AppModule
import com.bendenen.example.albumlistexample.di.modules.BuildersModule
import com.bendenen.example.albumlistexample.di.modules.NetModule
import com.bendenen.example.albumlistexample.di.modules.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, BuildersModule::class, ViewModelModule::class, AppModule::class,
        NetModule::class]
)
interface AppComponent {
    fun inject(app: AlbumListExampleApp)
}