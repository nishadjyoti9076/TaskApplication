package com.example.di_3rddaggerwithmvvmapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskapplication.models.Products

@Database(entities = [Products::class], version = 2)
abstract class PostDB : RoomDatabase() {

 abstract fun getFakerDao() : PostDAO
}