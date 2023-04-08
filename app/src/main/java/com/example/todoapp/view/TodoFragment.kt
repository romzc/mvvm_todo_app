package com.example.todoapp.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTodoBinding
import com.example.todoapp.factory.TaskViewModelFactory
import com.example.todoapp.model.TaskProvider
import com.example.todoapp.viewmodel.TaskViewModel

class TodoFragment() : Fragment() {

    private lateinit var binding: FragmentTodoBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapterTask: TaskAdapter
    private lateinit var viewModelFactory: TaskViewModelFactory

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
        // Aqui creamos el viewModel de tasks.
        // first of all we instance a task viewModel factory to inject a task provider
        viewModelFactory = TaskViewModelFactory(TaskProvider())
        // THen instance a viewModel with viewModelProvider, this code returns and viewModel with
        // taskProvider injected.
        taskViewModel = ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]

        // En esta sección configuramos el recycler view.
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
        fun newInstance(provider: TaskProvider) =
            TodoFragment().apply {
                arguments = Bundle()
            }
    }
}