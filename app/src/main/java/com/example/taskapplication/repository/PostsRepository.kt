package com.example.di_3rddaggerwithmvvmapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.di_3rddaggerwithmvvmapp.db.PostDB
import com.example.di_3rddaggerwithmvvmapp.retrofit.PostsApi
import com.example.taskapplication.models.Products
import com.example.taskapplication.utils.CheckConnectivity
import javax.inject.Inject
class PostsRepository @Inject constructor(private val context: Context,private val fakerApi: PostsApi, private val fakerDB : PostDB){

    private val _product= MutableLiveData<List<Products>>()
     val products: LiveData<List<Products>>
     get() = _product

   suspend fun getProducts() {
       if (CheckConnectivity.isInternetAvailable(context)){
           val result =fakerApi.getProducts()
           if (result.isSuccessful && result.body() !=null){
               fakerDB.getFakerDao().deletePost()
               fakerDB.getFakerDao().addProducts(result.body()!!)
               _product.postValue(result.body())
           }
       } /*else{
           _product.postValue(fakerDB.getFakerDao().getproducts())
       }*/
    }
}