package com.example.todo_cleanarch.fragments

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import com.example.todo_cleanarch.data.models.Priority

class SharedViewModel(application: Application):AndroidViewModel(application) {

    fun verifyDataFromUser(title: String, descrition: String): Boolean {
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(descrition)){
            false
        } else !(title.isEmpty() || descrition.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when(priority) {
            "High" -> {
                Priority.HIGH}
            "Medium" -> {
                Priority.MEDIUM}
            "Low" -> {
                Priority.LOW}
            else -> Priority.LOW
        }
    }
}