package com.mludovico.todo_list.datasource

import com.mludovico.todo_list.model.Task

object TaskDatasource {

    private var list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun addTask(task: Task) {
        if (task.id == 0) {
            list.add(task.copy(id = list.size + 1))
        } else {
            list.remove(task)
            list.add(task)
        }
    }

    fun findById(taskId: Int) : Task? {
        return list.find { it.id == taskId }
    }

    fun deleteTask(task: Task) {
        list.remove(task)
    }
}