package com.example.di_3rddaggerwithmvvmapp.di

import android.content.Context
import com.example.taskapplication.ui.main.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Component.Factory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Component(modules =[NetworkModule::class, DatabaseModule::class])
interface ApplicationComponent {

    fun inject(homeFragment: HomeFragment)

    @Component.Factory
    interface factory{
        fun create(@BindsInstance context:Context) : ApplicationComponent
    }
}