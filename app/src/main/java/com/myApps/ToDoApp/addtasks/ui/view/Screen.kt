package com.myApps.ToDoApp.addtasks.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.myApps.ToDoApp.addtasks.ui.model.TaskModel
import com.myApps.ToDoApp.addtasks.ui.viewmodel.TaskViewModel

/*
Pantalla principal de la aplicacion
 */
@Composable
fun Screen(modifier: Modifier, taskViewModel: TaskViewModel) {

    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(false)
    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { TopAppBar() },
            floatingActionButton = {
                FloatDialog(
                    Modifier.align(Alignment.BottomEnd),
                    taskViewModel
                )
            }
        ) {}

//        ListTask(
//            task = TODO(),
//            taskViewModel = TODO()
//        )
        AddTaskDialog(
            showDialog,
            onDismiss = { taskViewModel.onDialogClose() },
            onTaskAdd = { taskViewModel.onTaskCreate(it) })

    }
}

/*
Barra superior de la pantalla de la aplicacion
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
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
            IconButton(onClick = {})
            {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "menu"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White
        )
    )
}

/*
FloatinActionButton para que el usuario vaya añadiendo las tareas
 */
@Composable
fun FloatDialog(modifier: Modifier, taskViewModel: TaskViewModel) {
    FloatingActionButton(
        onClick = { taskViewModel.onShowDialog() },
        modifier = modifier.padding(end = 16.dp, bottom = 32.dp),
        containerColor = Color.Blue
    )
    {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "add task",
            tint = Color.White
        )
    }
}

//Dialogo para añadir las tareas
@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdd: (String) -> Unit) {
    //Variable que recoge la tarea escrita en el TextField
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade una nueva tarea",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.size(16.dp))
                TextField(
                    value = myTask,
                    onValueChange = { myTask = it },
                    maxLines = 1,
                    singleLine = true
                )
                Spacer(Modifier.size(16.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    //Boton de cancelar la accion
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(text = "Cancelar", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    //Boton para añadir tarea
                    Button(
                        onClick = {
                            //Recojo el valor de myTask y lo pongo otra vez en blanco
                            onTaskAdd(myTask)
                            myTask = ""
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        enabled = myTask.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Gray)
                    ) {
                        Text(text = "Añadir", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

//Recibe una lista de las tareas par mostrar
@Composable
fun ListTask(task: List<TaskModel>, taskViewModel: TaskViewModel) {
    LazyColumn {
        //Recoge la clave unica
        items(task, key = { it.id }) {
            ItemTask(it, taskViewModel)
        }
    }
}

//Cada task que añade el usuario
@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TaskViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        border = BorderStroke(2.dp, color = Color.Blue),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            //Recibe el task que añade el usuario
            Text(
                text = taskModel.task,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
            )

            Checkbox(
                checked = taskModel.check,
                onCheckedChange = { taskViewModel.onCheckSelected(taskModel) })
        }

    }
}

