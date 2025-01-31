package com.myApps.ToDoApp.addtasks.data.di

import android.content.Context
import androidx.room.Room
import com.myApps.ToDoApp.addtasks.data.TaskDao
import com.myApps.ToDoApp.addtasks.data.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    //Devuelvo la base de datos
    @Provides
    fun provideTaskDao(toDoDatabase: ToDoDatabase):TaskDao{
        return toDoDatabase.taskDao()
    }
    @Provides
    @Singleton
    //Creo la base de datos
    fun provideToDoDatabase(@ApplicationContext context: Context): ToDoDatabase{
        return Room.databaseBuilder(context, ToDoDatabase::class.java, "TaskDatabase").build()
    }
}