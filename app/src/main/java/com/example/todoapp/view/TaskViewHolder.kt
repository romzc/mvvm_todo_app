package com.example.todoapp.view

import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemTaskBinding.bind(view)

    fun render(task: Task, updateCall: (Task) -> Unit, deleteCall: (Task)->Unit) {
        binding.tvTodoTitle.text = task.title
        binding.tvTodoDescription.text = task.description
        binding.cbTodoCheck.isChecked = task.done

        if(task.done) {
            binding.tvTodoTitle.paintFlags = binding.tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        else {
            binding.tvTodoTitle.paintFlags = binding.tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        binding.llTodoDescription.setOnClickListener {
            updateCall(task)
        }

        binding.cbTodoCheck.setOnClickListener {
            updateCall(task)
        }

        binding.ivCloseIcon.setOnClickListener {
            deleteCall(task)
        }
    }
}