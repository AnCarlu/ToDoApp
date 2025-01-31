package com.myApps.ToDoApp.addtasks.domain

import com.myApps.ToDoApp.addtasks.data.TaskRepository
import com.myApps.ToDoApp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    //Devuelvo el Flow de la capa de ui
    operator fun invoke():Flow<List<TaskModel>>{
        return taskRepository.task
    }
}