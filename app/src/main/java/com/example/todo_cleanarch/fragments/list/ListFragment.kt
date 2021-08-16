package com.example.todo_cleanarch.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_cleanarch.R
import com.example.todo_cleanarch.TAG
import com.example.todo_cleanarch.data.viewmodels.ToDoViewModel
import com.example.todo_cleanarch.databinding.FragmentListBinding
import com.example.todo_cleanarch.fragments.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {
    private val mToDoViewModel:ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding:FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment. Not used when binding is used.
        // val view =  inflater.inflate(R.layout.fragment_list, container, false)

        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

//        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        setupRecyclerView()

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { dataList ->
            mSharedViewModel.checkIfDatabaseEmpty(dataList)
            Log.d(TAG, "mToDoViewModel.Observer(): Setting Adapter dataList")
            adapter.setData(dataList)
        })
        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })

// We have support this using BindingAdapter android:navigateToAddFragment
//        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
//            findNavController().navigate(R.id.action_listFragment_to_addFragment)
//        }

        // Set Menu
        setHasOptionsMenu(true)

        // return view
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.findViewById<ImageView>(R.id.no_data_imageView)?.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.no_data_textView)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<ImageView>(R.id.no_data_imageView)?.visibility = View.INVISIBLE
            view?.findViewById<TextView>(R.id.no_data_textView)?.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_all) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully Removed all items", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ -> }
        builder.setTitle("Delete All")
        builder.setMessage("Are you sure you want to delete all items?")
        builder.create().show()
    }


}