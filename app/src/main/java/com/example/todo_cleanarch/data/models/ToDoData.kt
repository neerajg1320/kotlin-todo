package com.example.todo_cleanarch.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
        var id:Int,
        var titleval : String,
        var process: Priority,
        var description: String
)