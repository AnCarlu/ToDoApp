package com.myApps.ToDoApp.addtasks.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myApps.ToDoApp.addtasks.domain.AddTaskUseCase
import com.myApps.ToDoApp.addtasks.domain.DeleteTaskUseCase
import com.myApps.ToDoApp.addtasks.domain.GetTaskUseCase
import com.myApps.ToDoApp.addtasks.domain.UpdateTaskUseCase
import com.myApps.ToDoApp.addtasks.ui.model.TaskModel
import com.myApps.ToDoApp.addtasks.ui.model.UiState
import com.myApps.ToDoApp.addtasks.ui.model.UiState.Succes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    /*Injecto los casos de uso para que el StateFlow los vaya recuperando
    y en la pantalla se muestre segun el estado
     */
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTaskUseCase: GetTaskUseCase
) : ViewModel() {

    //Livedata para mostrar el Dialog de Add
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    //LiveData para mostrar el Dialog de Delete
    private val _showDelete = MutableLiveData<Boolean>()
    val showDelete: LiveData<Boolean> = _showDelete


    // Lista de tareas seleccionadas
    private val _selectedTasks = mutableStateListOf<TaskModel>()
    val selectedTasks: List<TaskModel> get() = _selectedTasks


    /*Creo un StateFlow de Uistate  que llama a los casos de uso,
    y cada vez que actualice los datos devuelve los diferentes casos de uso
    */
    val uiState: StateFlow<UiState> = getTaskUseCase().map(::Succes)
        .catch { UiState.Error(it) }
        /*Combierto el Flow en un StateFlow, y cuando la aplicación este en segundo plano
        detiene el Flow a los 5 segundos y recupera el estado
         */
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)


    /*
    En las diferentes funciones voy cambiando el valor de _showDialog y _showCheck
    Para que se muestre o no el Dialog y los CheckBox de ca item
     */
    //Mostrar el Dialog de Add
    fun onShowDialog() {
        _showDialog.value = true
    }
    //Cerrar el Dialog de Add
    fun onDialogClose() {
        _showDialog.value = false
    }

    //Mostrar el Dialog de Delete
    fun onShowDelete(){
        _showDelete.value=true
    }
    //No mostrar el Dialog de Delete
    fun onDeleteClose(){
        _showDelete.value=false
    }



    //Recupera el valor de añadido en el TextField
    fun onTaskCreate(task: String) {
        _showDialog.value = false
        //Añado las task con Flow
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }



    //Actualizar el check de cada tarea
    fun onCheckSelected(taskModel: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(check = !taskModel.check))
        }
    }

    // Función para agregar o quitar una tarea de la lista de seleccionadas
    fun onTaskSelected(taskModel: TaskModel) {
        if (_selectedTasks.contains(taskModel)) {
            _selectedTasks.remove(taskModel)
        } else {
            _selectedTasks.add(taskModel)
        }
    }

    // Función para eliminar las tareas seleccionadas
    fun onDeleteSelectedTasks() {
        viewModelScope.launch {
            _selectedTasks.forEach { task ->
                deleteTaskUseCase(task)
            }
            _selectedTasks.clear() // Limpiar la lista después de eliminar
        }
    }



}