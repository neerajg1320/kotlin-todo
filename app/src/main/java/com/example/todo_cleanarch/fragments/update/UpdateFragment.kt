package com.example.todo_cleanarch.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo_cleanarch.R
import com.example.todo_cleanarch.data.models.Priority
import com.example.todo_cleanarch.data.models.ToDoData
import com.example.todo_cleanarch.data.viewmodels.ToDoViewModel
import com.example.todo_cleanarch.fragments.SharedViewModel


class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel:SharedViewModel by viewModels()
    private val mToDoViewModel:ToDoViewModel by viewModels()

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
        current_spinner.setSelection(mSharedViewModel.parsePriority(args.currentItem.priority))
        current_spinner.onItemSelectedListener = mSharedViewModel.listener


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(), "Successfully Removed: ${args.currentItem.title}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete ${args.currentItem.title}")
        builder.setMessage("Are you sure you want to delete '${args.currentItem.title}'?")
        builder.create().show()
    }

    private fun deleteItem() {

    }

    private fun updateItem() {
        val view = requireView()
        val mTitle = view.findViewById<EditText>(R.id.current_title_et).text.toString()
        val mPriority = view.findViewById<Spinner>(R.id.current_priorities_spinner).selectedItem.toString()
        val mDescription = view.findViewById<EditText>(R.id.current_description_et).text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            val updatedData = ToDoData(args.currentItem.id, mTitle, mSharedViewModel.parsePriority(mPriority), mDescription)
            mToDoViewModel.updateData(updatedData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

            //Navigate back to ListFragment
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show()
        }

    }
}