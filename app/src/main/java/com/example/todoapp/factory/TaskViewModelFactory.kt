package com.example.todoapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.model.TaskProvider
import com.example.todoapp.viewmodel.TaskViewModel

class TaskViewModelFactory(private val taskProvider: TaskProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(taskProvider) as T
        }

        throw IllegalArgumentException("Unknown viewModel class")
    }
}