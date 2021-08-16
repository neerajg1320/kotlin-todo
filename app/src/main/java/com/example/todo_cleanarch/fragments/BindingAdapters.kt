package com.example.todo_cleanarch.fragments

import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.todo_cleanarch.R
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
    }
}