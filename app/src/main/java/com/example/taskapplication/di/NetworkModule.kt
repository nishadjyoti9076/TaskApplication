package com.example.di_3rddaggerwithmvvmapp.di
import com.example.di_3rddaggerwithmvvmapp.retrofit.PostsApi
import com.example.di_3rddaggerwithmvvmapp.utils.Constant
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun ProvidesRetrofit() :Retrofit{

        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun ProvidesePostApi(retrofit: Retrofit) : PostsApi{
        return retrofit.create(PostsApi::class.java)
    }
}