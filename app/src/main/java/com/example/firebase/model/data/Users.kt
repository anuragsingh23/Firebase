package com.example.firebase.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "Users")
@Parcelize
data class Users(
    val name:String,
    val phoneNumber : String,
    val isLoggedIn :Boolean,
    @PrimaryKey(autoGenerate = true ) val id : Int? =null
) : Parcelable
