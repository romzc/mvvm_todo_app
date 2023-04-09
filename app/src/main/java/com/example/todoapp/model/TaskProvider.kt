package com.example.todoapp.model


class TaskProvider(private val taskDao: TaskDao) {
     fun getAllTasks(): List<Task> = taskDao.getAll()
     fun insertTask(task: Task) = taskDao.insert(task)
     fun updateTask(task: Task) = taskDao.update(task)
     fun deleteTask(task: Task) = taskDao.delete(task)

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
                if (it.id == task.id) it.done = task.done
            }
        }
    }
}