package com.myApps.ToDoApp.addtasks.data

import com.myApps.ToDoApp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val task: Flow<List<TaskModel>> =
        taskDao.getTask().map { items -> items.map { TaskModel(it.id, it.task, it.check) } }

    //Añadir a la base de datos
    suspend fun add(taskModel: TaskModel) {
        taskDao.insertTask(taskModel.toData())
    }

    //Actualizar para poder marcar las tareas
    suspend fun update(taskModel: TaskModel) {
        taskDao.updateTask(taskModel.toData())
    }

    //Borrar de la base de datos
    suspend fun delete(taskModel: TaskModel) {
        taskDao.deleteTask(taskModel.toData())
    }
}

fun TaskModel.toData(): TaskEntity{
    return TaskEntity(this.id, this.task,this.check)
}