package com.example.firebase.model.data

import kotlinx.coroutines.flow.Flow

class UsersRepositoryImpl(
    private val usersDao: UsersDao,
) : UsersRepository {


    override suspend fun insertUser(users: User) {
        usersDao.insertUser(users)
    }

    override suspend fun deleteUser(users: User) {
        usersDao.deleteUser(users)
    }

    override fun getUsers(): Flow<List<User>> {
         return  usersDao.getUsers()
    }




}