package com.example.todo_cleanarch.fragments

import android.app.Application
import android.graphics.Color.blue
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todo_cleanarch.R
import com.example.todo_cleanarch.data.models.Priority
import com.example.todo_cleanarch.data.models.ToDoData

class SharedViewModel(application: Application):AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(todoData: List<ToDoData>) {
        emptyDatabase.value = todoData.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object:
        AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                0 -> {(parent?.getChildAt(0) as TextView)?.setTextColor(
                    ContextCompat.getColor(application, R.color.red)
                )}
                1 -> {(parent?.getChildAt(0) as TextView)?.setTextColor(
                    ContextCompat.getColor(application, R.color.yellow)
                )}
                2 -> {(parent?.getChildAt(0) as TextView)?.setTextColor(
                    ContextCompat.getColor(application, R.color.green)
                )}
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }

    fun verifyDataFromUser(title: String, descrition: String): Boolean {
        return !(title.isEmpty() || descrition.isEmpty())
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