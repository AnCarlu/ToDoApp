package com.myApps.ToDoApp.addtasks.domain

import com.myApps.ToDoApp.addtasks.data.TaskRepository
import com.myApps.ToDoApp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    //Inserto los task en el repositorio
    suspend operator fun invoke(taskModel: TaskModel){
        taskRepository.delete(taskModel)
    }
}