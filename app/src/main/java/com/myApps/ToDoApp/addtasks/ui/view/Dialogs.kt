package com.myApps.ToDoApp.addtasks.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.myApps.ToDoApp.addtasks.ui.viewmodel.TaskViewModel

/*
FloatinActionButton para que el usuario vaya añadiendo las tareas
 */
@Composable
fun FloatDialog(taskViewModel: TaskViewModel) {
    FloatingActionButton(
        onClick = { taskViewModel.onShowDialog() },
        modifier = Modifier.padding(end = 16.dp, bottom = 32.dp),
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

//Dialogo para borrar las tareas
@Composable
fun DeleteDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onDeleteTask: () -> Unit,
    isVisible: Boolean,
    onVisibilityChange: (Boolean) -> Unit
) {
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "¿Estas seguro que quieres borrar?",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
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
                    //Boton para borrar tareas
                    Button(
                        onClick = {
                            onDeleteTask()
                            onDismiss()
                            onVisibilityChange(!isVisible)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(disabledContainerColor = Color.Gray)
                    ) {
                        Text(text = "Borrar", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

