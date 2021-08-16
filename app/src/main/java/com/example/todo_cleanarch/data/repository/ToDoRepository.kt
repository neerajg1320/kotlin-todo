package com.example.todo_cleanarch.data.repository

import androidx.lifecycle.LiveData
import com.example.todo_cleanarch.data.ToDoDao
import com.example.todo_cleanarch.data.models.ToDoData

class ToDoRepository (private val todoDao: ToDoDao){
    val getAllData: LiveData<List<ToDoData>> = todoDao.getAllData()

    suspend fun insertData(todoData: ToDoData) {
        todoDao.insertData(todoData)
    }

    suspend fun updateData(todoData: ToDoData) {
        todoDao.updateData(todoData)
    }

    suspend fun deleteItem(todoData: ToDoData) {
        todoDao.deleteItem(todoData)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }
}