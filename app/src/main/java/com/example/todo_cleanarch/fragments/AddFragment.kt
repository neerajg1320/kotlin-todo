package com.example.todo_cleanarch.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*

import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todo_cleanarch.R
import com.example.todo_cleanarch.data.models.Priority
import com.example.todo_cleanarch.data.models.ToDoData
import com.example.todo_cleanarch.data.viewmodels.ToDoViewModel

class AddFragment : Fragment() {

    private val mToDoViewModel:ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val view = requireView()
        val mTitle = view.findViewById<EditText>(R.id.title_et).text.toString()
        val mPriority = view.findViewById<Spinner>(R.id.priorities_spinner).selectedItem.toString()
        val mDescription = view.findViewById<EditText>(R.id.description_et).text.toString()

        val validation = verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            val newData = ToDoData(0, mTitle, parsePriority(mPriority), mDescription)
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

            //Navigate back to ListFragment
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun verifyDataFromUser(title: String, descrition: String): Boolean {
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(descrition)){
            false
        } else !(title.isEmpty() || descrition.isEmpty())
    }

    private fun parsePriority(priority: String): Priority {
        return when(priority) {
            "High" -> {Priority.HIGH}
            "Medium" -> {Priority.MEDIUM}
            "Low" -> {Priority.LOW}
            else -> Priority.LOW
        }
    }
}