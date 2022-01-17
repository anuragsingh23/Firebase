package com.example.firebase.di

import android.app.Application
import androidx.room.Room
import com.example.firebase.model.data.UsersDatabase
import com.example.firebase.model.data.UsersRepository
import com.example.firebase.model.data.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUsersDatabase(app : Application): UsersDatabase {
        return Room.databaseBuilder(
            app,
            UsersDatabase::class.java,
            "users_db"
        ).build()

    }

    @Singleton
    @Provides
    fun provideUsersRepository(db:UsersDatabase): UsersRepository {

        return UsersRepositoryImpl(db.usersDao)
    }


}