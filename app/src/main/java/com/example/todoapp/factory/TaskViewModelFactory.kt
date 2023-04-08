package com.example.todoapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.model.TaskProvider
import com.example.todoapp.viewmodel.TaskViewModel

class TaskViewModelFactory(private val provider: TaskProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(provider) as T
        }
        throw IllegalArgumentException("Unknown view_model class")
    }

}