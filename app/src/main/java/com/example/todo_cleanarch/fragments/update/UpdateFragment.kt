package com.example.todo_cleanarch.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.todo_cleanarch.R
import com.example.todo_cleanarch.data.models.Priority
import com.example.todo_cleanarch.fragments.SharedViewModel


class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel:SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        setHasOptionsMenu(true)

        view.findViewById<EditText>(R.id.current_title_et).setText(args.currentItem.title)
        view.findViewById<EditText>(R.id.current_description_et).setText(args.currentItem.description)

        val current_spinner = view.findViewById<Spinner>(R.id.current_priorities_spinner)
        current_spinner.setSelection(parsePriority(args.currentItem.priority))
        current_spinner.onItemSelectedListener = mSharedViewModel.listener


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    private fun parsePriority(priority: Priority): Int {
        return when(priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }
}