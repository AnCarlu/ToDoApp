package com.myApps.ToDoApp.addtasks.ui.model

sealed interface UiState {
    //Estado cargando
    object Loading:UiState
    //Estado error
    data class Error(val throwable: Throwable):UiState
    //Estado correcto
    data class Succes(val tasks: List<TaskModel>): UiState

}