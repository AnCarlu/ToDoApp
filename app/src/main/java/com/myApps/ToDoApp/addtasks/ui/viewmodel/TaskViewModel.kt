package com.myApps.ToDoApp.addtasks.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myApps.ToDoApp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(): ViewModel() {

    //Livedata para mostrar el Dialog
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog



    /*
    En las diferentes funciones voy cambiando el valor de _showDialog
    Para que se muestre o no el Dialog
     */
    //Cerrar el Dialog
    fun onDialogClose(){
        _showDialog.value=false
    }

    //Recupera el valor de añadido en el TextField
    fun onTaskCreate(task: String) {
        _showDialog.value=false
    }

    //Mostrar el Dialog
    fun onShowDialog() {
        _showDialog.value=true
    }

    fun onCheckSelected(taskModel: TaskModel) {

    }

    //Borrar la tarea
    fun onItemRemove(taskModel: TaskModel){

    }
}