package com.example.taskapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class Products(
    val body: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val userId: Int
)