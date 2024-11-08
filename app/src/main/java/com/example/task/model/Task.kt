package com.example.task.model

data class Task(
    val id: Int,
    var title: String,
    var isCompleted: Boolean = false
)
