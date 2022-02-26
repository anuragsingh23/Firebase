package com.example.firebase.model.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: User)

    @Delete
    suspend fun deleteUser(users: User)



    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<User>>

}