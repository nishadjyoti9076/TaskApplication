package com.example.taskapplication.utils

import android.app.Application
import com.example.di_3rddaggerwithmvvmapp.di.ApplicationComponent
import com.example.di_3rddaggerwithmvvmapp.di.DaggerApplicationComponent


class PostsApplication : Application() {
 lateinit var applicationcomponent : ApplicationComponent

    companion object {
        lateinit var sharedPreferenceClass: SharedPreferenceClass
        var mContext: PostsApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
    applicationcomponent= DaggerApplicationComponent.factory().create(this)
        mContext = this
        sharedPreferenceClass = SharedPreferenceClass(applicationContext)
    }



}