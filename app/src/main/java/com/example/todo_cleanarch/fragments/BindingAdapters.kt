package com.example.todo_cleanarch.fragments

import android.view.View
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.todo_cleanarch.R
import com.example.todo_cleanarch.data.models.Priority
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("android:navigateToAddFragment")
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("android:emptyDatabase")
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("android:parsePriorityToInt")
        fun parsePriorityToInt(spinner: Spinner, priority: Priority) {

                return when(priority) {
                    Priority.HIGH -> {spinner.setSelection(0)}
                    Priority.MEDIUM -> {spinner.setSelection(1)}
                    Priority.LOW -> {spinner.setSelection(2)}
                }

        }
    }
}