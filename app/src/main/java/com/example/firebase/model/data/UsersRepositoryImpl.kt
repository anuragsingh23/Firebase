package com.example.firebase.model.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow

class UsersRepositoryImpl(
    private val usersDao: UsersDao,
) : UsersRepository {


    override suspend fun insertUser(users: Users) {
        usersDao.insertUser(users)
    }

    override suspend fun deleteUser(users: Users) {
        usersDao.deleteUser(users)
    }

    override fun getUsers(): Flow<List<Users>> {
         return  usersDao.getUsers()
    }

    lateinit var auth: FirebaseAuth

}