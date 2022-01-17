package com.example.firebase.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Users(
    val name:String,
    val phoneNumber : String,
    val isLoggedIn :Boolean,
    @PrimaryKey val id : Int? =null
)
