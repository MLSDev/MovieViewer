package com.shykun.volodymyr.movieviewer.presentation.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

}