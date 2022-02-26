package com.example.firebase.model.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [User::class],
    version = 1
)
abstract  class UsersDatabase: RoomDatabase (){
    abstract val usersDao : UsersDao

}