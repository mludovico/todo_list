package com.mludovico.todo_list.ui.recycler_view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mludovico.todo_list.R
import com.mludovico.todo_list.databinding.TaskItemLayoutBinding
import com.mludovico.todo_list.model.Task

class ListRecyclerViewAdapter() : ListAdapter<Task, ListRecyclerViewAdapter.TaskViewHolder>(DiffCallback()) {

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskItemLayoutBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding);
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(private val binding: TaskItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.tvTaskItemTitle.text = item.title
            binding.tvTaskItemDescription.text = item.description
            binding.tvTaskItemDate.text = item.date
            binding.tvTaskItemTime.text = item.time
            binding.btnTaskMore.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(task: Task) {
            val btnMore = binding.btnTaskMore
            val popupMenu = PopupMenu(btnMore.context, btnMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerEdit(task)
                    R.id.action_delete -> listenerDelete(task)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.id == newItem.id &&
        oldItem.title == newItem.title &&
        oldItem.description == newItem.description &&
        oldItem.date == newItem.date &&
        oldItem.time == newItem.time

}