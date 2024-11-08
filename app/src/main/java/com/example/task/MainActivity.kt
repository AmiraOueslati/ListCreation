// MainActivity.kt
package com.example.task

import TaskViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.task.model.Task

class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListApp(taskViewModel)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListApp(viewModel: TaskViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task List") }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                AddTaskButton(onAddTask = { viewModel.addTask() })
                Spacer(modifier = Modifier.height(16.dp))
                TaskList(
                    tasks = viewModel.tasks,
                    onTaskStatusChange = { task, isCompleted -> viewModel.updateTaskStatus(task, isCompleted) },
                    onDeleteTask = { viewModel.removeTask(it) }
                )
            }
        }
    )
}

@Composable
fun AddTaskButton(onAddTask: () -> Unit) {
    Button(
        onClick = onAddTask,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Add one")
    }
}

@Composable
fun TaskList(
    tasks: List<Task>,
    onTaskStatusChange: (Task, Boolean) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            TaskItem(
                task = task,
                onStatusChange = { isCompleted -> onTaskStatusChange(task, isCompleted) },
                onDelete = { onDeleteTask(task) }
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onStatusChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onStatusChange(it) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = task.title)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Close, contentDescription = "Delete task")
        }
    }
}
