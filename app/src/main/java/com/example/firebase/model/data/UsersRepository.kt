package com.example.firebase.model.data

import kotlinx.coroutines.flow.Flow

interface UsersRepository {


    suspend fun insertUser(users: User)
    suspend fun deleteUser(users: User)
    fun getUsers(): Flow<List<User>>


}