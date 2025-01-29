package com.myApps.ToDoApp.addtasks.ui.model

data class TaskModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val task: String,
    val check: Boolean = false
)