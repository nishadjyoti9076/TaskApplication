package com.example.di_3rddaggerwithmvvmapp.di

import android.content.Context
import androidx.room.Room
import com.example.di_3rddaggerwithmvvmapp.db.PostDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule  {

    @Singleton
    @Provides
    fun ProvidePostDB(context : Context) : PostDB {
       return Room.databaseBuilder(context, PostDB::class.java,"NavComdb").build()
    }

}