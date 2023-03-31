package com.example.todoapp.view

import android.app.Dialog
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTodoBinding
import com.example.todoapp.databinding.ShowDialogBinding
import com.example.todoapp.model.Task
import com.example.todoapp.viewmodel.TaskViewModel

class TodoFragment : Fragment() {

    private lateinit var binding: FragmentTodoBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapterTask: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoBinding.inflate(inflater, container, false)
        initUI()
        initListeners()
        return binding.root
    }

    private fun initDialog() {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.show_dialog)

        val btnSave: Button = dialog.findViewById(R.id.btnSave)
        val etTaskTitle: TextView = dialog.findViewById(R.id.etTaskTitle)
        val etTaskDescription: TextView = dialog.findViewById(R.id.etTaskDescription)
        btnSave.setOnClickListener {
            val titleTask = etTaskTitle.text.toString()
            val descriptionTask = etTaskDescription.text.toString()

            if (titleTask.isNotEmpty() || descriptionTask.isNotEmpty()) {
                taskViewModel.addNewTask(titleTask, descriptionTask)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun initListeners() {
        binding.fabAddTask.setOnClickListener { initDialog() }

        binding.svTodoApp.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                taskViewModel.searchTask(newText.orEmpty())
                return false
            }
        })
    }

    private fun initUI() {
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        adapterTask = TaskAdapter(
            updateTask = { task ->
                task.done = !task.done
                taskViewModel.completeTask(task)
            },
            deleteTask = { task -> taskViewModel.deleteTask(task) }
        )
        binding.rvTodoTasks.setHasFixedSize(true)
        binding.rvTodoTasks.layoutManager = LinearLayoutManager(context)
        binding.rvTodoTasks.adapter = adapterTask

        // observa los cambios que ocurren en la lista tasks del viewmodel
        // y ejecuta la funcion lambda
        taskViewModel.tasks.observe(viewLifecycleOwner) {
            adapterTask.submitList(it)
        }
        taskViewModel.loadTasks()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TodoFragment().apply {
                arguments = Bundle()
            }
    }
}