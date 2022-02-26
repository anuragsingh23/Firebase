package com.example.firebase.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(
    tableName = "users"
)
data class User(
    val name:String,
    val phoneNumber : String,
    val isLoggedIn :Boolean,
    @PrimaryKey val id : Int? =null
)
