package com.example.di_3rddaggerwithmvvmapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.taskapplication.models.Products

@Dao
interface PostDAO {

    @Insert
   suspend fun addProducts(products: List<Products>)

   @Query("Select * from post")
   fun getproducts() : List<Products>

   @Query("DELETE from post")
   suspend fun deletePost()
}