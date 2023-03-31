package com.example.todoapp.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    var done: Boolean = false
)
