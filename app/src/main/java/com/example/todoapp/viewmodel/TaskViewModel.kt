package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskProvider
import java.security.Provider

class TaskViewModel() : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>(emptyList())
    val tasks: LiveData<List<Task>> = _tasks

    fun loadTasks() {
        _tasks.value = TaskProvider.getAllTask().toList()
    }

    // push new task
    fun completeTask(task: Task) {
        val newTasks = _tasks.value?.map { item ->
            if (item.id == task.id) task
            else item
        } ?: emptyList()
        _tasks.value = newTasks
        //Log.i("TODO1", "${System.identityHashCode(_tasks.value)} -- ${System.identityHashCode(TaskProvider.getAllTask())}")
        //Log.i("TODO1", "${_tasks.value.hashCode()} -- ${TaskProvider.getAllTask().hashCode()}")
        TaskProvider.updateTask(task)
    }

    fun deleteTask(task: Task) {
        _tasks.value = _tasks.value?.filter { it.id != task.id }

        TaskProvider.deleteTask(task)
    }

    fun addNewTask(title: String, description: String) {
        val newId = TaskProvider.getAllTask().size
        val newTask = Task(newId +1, title, description)
        TaskProvider.insertTask(newTask)

        _tasks.value = _tasks.value.orEmpty() + newTask
    }

    fun searchTask(title: String) {
        if( title.isEmpty() ) {
            _tasks.value = TaskProvider.getAllTask()
        }
        else {

        }
    }
}