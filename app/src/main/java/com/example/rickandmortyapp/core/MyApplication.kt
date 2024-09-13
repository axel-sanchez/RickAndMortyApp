package com.example.rickandmortyapp.core

import android.app.Application
import com.example.rickandmortyapp.di.module.ApplicationModule
import com.example.rickandmortyapp.di.component.ApplicationComponent
import com.example.rickandmortyapp.di.component.DaggerApplicationComponent

/**
 * @author Axel Sanchez
 */
class MyApplication: Application() {
    val component: ApplicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(ApplicationModule(this))
        .build()
}