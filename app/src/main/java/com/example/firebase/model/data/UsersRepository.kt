package com.example.firebase.model.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow

interface UsersRepository {


    suspend fun insertUser(users: Users)


    suspend fun deleteUser(users: Users)

    fun getUsers(): Flow<List<Users>>


}