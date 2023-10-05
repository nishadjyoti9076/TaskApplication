package com.example.di_3rddaggerwithmvvmapp.retrofit

import com.example.taskapplication.models.Products
import retrofit2.Response
import retrofit2.http.GET

interface PostsApi {

    @GET("posts")
   suspend fun getProducts() : Response<List<Products>>
}