    package com.example.todoapp.viewmodel

import android.content.Context
import android.provider.Settings.Global
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.processNextEventInCurrentThread
import kotlin.coroutines.coroutineContext

// Modificaciones.
class TaskViewModel(private val provider: TaskProvider) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>> = _tasks

    fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _tasks.postValue(provider.getAllTasks())
        }
    }

    // push new task
    fun completeTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            provider.updateTask(task)
            loadTasks()
        }
        // Log.i("TODO1", "${System.identityHashCode(_tasks.value)} -- ${System.identityHashCode(TaskProvider.getAllTask())}")
        // Log.i("TODO1", "${_tasks.value.hashCode()} -- ${TaskProvider.getAllTask().hashCode()}")
        // TaskProvider.updateTask(task)
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            provider.deleteTask(task)
            loadTasks()
        }
    }

    fun addNewTask(title: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTask = Task(title = title, description = description)
            provider.insertTask(newTask)
            loadTasks()
        }
    }

    fun searchTask(title: String) {
        if (title.isEmpty()) {
            loadTasks()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val auxTask = provider.getAllTasks()
                // Aqui obtenemos todos las tareas que tienen como titulo algun patron que
                // el usuario ingresa en el buscador.
                val filter =
                    auxTask.filter { it.title.contains(other = title, ignoreCase = true) }

                // despues modificacmos el valor de nuestro viewModel.
                _tasks.postValue(filter)
            }
        }
    }
}