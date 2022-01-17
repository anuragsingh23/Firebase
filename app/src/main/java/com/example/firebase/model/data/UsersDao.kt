package com.example.firebase.model.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: Users)

    @Delete
    suspend fun deleteUser(users: Users)



    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<Users>>

}