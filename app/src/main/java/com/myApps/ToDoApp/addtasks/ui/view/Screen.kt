package com.myApps.ToDoApp.addtasks.ui.view

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.myApps.ToDoApp.addtasks.ui.model.TaskModel
import com.myApps.ToDoApp.addtasks.ui.model.UiState
import com.myApps.ToDoApp.addtasks.ui.viewmodel.TaskViewModel

/*
Pantalla principal de la aplicacion
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Screen(modifier: Modifier, taskViewModel: TaskViewModel) {

    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(false)
    val showDelete: Boolean by taskViewModel.showDelete.observeAsState(false)
    val lifecycle = LocalLifecycleOwner.current.lifecycle //Ciclo de vida de Screen
    var isVisible by rememberSaveable { mutableStateOf(false) }
    //Este estado siempre tendrá el ultimo valor actualizado
    val uiState by produceState<UiState>(
        initialValue = UiState.Loading,
        key1 = lifecycle,
        key2 = taskViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) { //Mientras el estado ha empezado siempre está consumiendo datos
            taskViewModel.uiState.collect { value = it }
        }
    }
    when (uiState) {
        is UiState.Error -> {

        }

        UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Succes -> {

            Scaffold(
                topBar = { TopAppBar(isVisible, taskViewModel) },
                floatingActionButton = { FloatDialog(taskViewModel) }
            ) { paddingValues ->
                Surface(modifier.fillMaxSize(), color = Color.White) {
                    AddTaskDialog(
                        showDialog,
                        onDismiss = { taskViewModel.onDialogClose() },
                        onTaskAdd = { taskViewModel.onTaskCreate(it) })
                    ListTask(
                        (uiState as UiState.Succes).tasks,
                        taskViewModel,
                        contentPadding = paddingValues,
                        isVisible,
                        onVisibilityChange = { isVisible = !isVisible }
                    )
                    DeleteDialog(
                        showDelete,
                        onDismiss = { taskViewModel.onDeleteClose() },
                        onDeleteTask = { taskViewModel.onDeleteSelectedTasks() },
                        isVisible,
                        onVisibilityChange = { isVisible = !isVisible }
                    )

                }
            }

        }
    }
}

/*
Barra superior de la pantalla de la aplicacion
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(isVisible: Boolean, taskViewModel: TaskViewModel) {
    //Metodo usado para que el texto aparezca centrado en el TopBar
    CenterAlignedTopAppBar(

        title = {
            Text(
                text = "Tareas Pendientes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        },
        ///Icono del menu
        actions = {
            if (isVisible) {
                IconButton(onClick = { taskViewModel.onShowDelete() })
                {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White
        )
    )
}


//Recibe una lista de las tareas par mostrar
@Composable
fun ListTask(
    task: List<TaskModel>,
    taskViewModel: TaskViewModel,
    contentPadding: PaddingValues,
    isVisible: Boolean,
    onVisibilityChange: (Boolean) -> Unit
) {
    LazyColumn(contentPadding = contentPadding) {
        //Recoge la clave unica
        items(task, key = { it.id }) {
            ItemTask(
                it,
                taskViewModel,
                isVisible,
                onVisibilityChange = { onVisibilityChange(!isVisible) })
        }
    }

}

//Cada task que añade el usuario
@Composable
fun ItemTask(
    taskModel: TaskModel,
    taskViewModel: TaskViewModel,
    isVisible: Boolean,
    onVisibilityChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = { onVisibilityChange(!isVisible) })
            }
            .testTag("card"),
        border = BorderStroke(2.dp, color = Color.Blue),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.LightGray)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .size(40.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            //Recibe el task que añade el usuario
            Text(
                text = taskModel.task,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
                    ,
                color = Color.Black, fontFamily = FontFamily.SansSerif
            )

            //Animación para ver el CheckBox
            AnimatedVisibility(isVisible) {
                Checkbox(
                    checked = taskModel.check,
                    onCheckedChange = {
                        taskViewModel.onCheckSelected(taskModel)
                        taskViewModel.onTaskSelected(taskModel)
                    },
                    modifier = Modifier.testTag("checkBox"),
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Blue,
                    )
                )
            }
        }


    }
}

