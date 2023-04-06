package com.example.todoapp.model

import android.content.Context

class TaskProvider(context: Context) {

    private val db = AppDatabase.getDatabase(context)
    // se agrego todas las funciones necesarias.
    suspend fun getAllTask(): List<Task> {
        return db.taskDao().getAll()
    }

    suspend fun getInsertTask(newTask: Task) {
        db.taskDao().insert(newTask)
    }

    suspend fun deleteTask(task: Task) {
        db.taskDao().delete(task)
    }

    suspend fun updateTask(task: Task) {
        db.taskDao().update(task)
    }

    companion object {
        private val tasks = mutableListOf(
            Task(1, "Comer", "Comer algo"),
            Task(2, "Jugar", "jugar algo"),
            Task(3, "Estudiar", "estudiar algo"),
            Task(4, "Programar", "programar algo"),
            Task(5, "Resolver", "resolver algo")
        )

        fun getAllTask(): MutableList<Task> {
            return this.tasks
        }

        fun insertTask(newTask: Task) {
            this.tasks.add(newTask)
        }

        fun deleteTask(task: Task) {
            val index = this.tasks.indexOf(task)
            this.tasks.removeAt(index)
        }

        fun updateTask(task: Task) {
            this.tasks.forEach {
                if( it.id == task.id) it.done = task.done
            }
        }
    }
}