package com.mludovico.todo_list.ui

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mludovico.todo_list.databinding.ActivityMainBinding
import com.mludovico.todo_list.datasource.TaskDatasource
import com.mludovico.todo_list.ui.recycler_view.ListRecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { ListRecyclerViewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listView.adapter = adapter

        addListeners()
    }

    private fun addListeners() {
        binding.btnAddTask.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }
        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, EDIT_TASK)
        }
        adapter.listenerDelete = {
            TaskDatasource.deleteTask(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == CREATE_NEW_TASK || requestCode == EDIT_TASK) && resultCode == RESULT_OK) updateList()
    }

    private fun updateList() {
        val list = TaskDatasource.getList()
        if (list.isEmpty()) {
            binding.listView.visibility = View.GONE
            binding.includeEmpty.emptyState.visibility = View.VISIBLE
        } else {
            binding.listView.visibility = View.VISIBLE
            binding.includeEmpty.emptyState.visibility = View.GONE
        }
        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
        private const val EDIT_TASK = 1001
    }
}