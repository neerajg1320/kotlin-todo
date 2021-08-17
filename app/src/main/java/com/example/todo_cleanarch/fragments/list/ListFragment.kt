package com.example.todo_cleanarch.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.example.todo_cleanarch.R
import com.example.todo_cleanarch.TAG
import com.example.todo_cleanarch.data.models.ToDoData
import com.example.todo_cleanarch.data.viewmodels.ToDoViewModel
import com.example.todo_cleanarch.data.viewmodels.observeOnce
import com.example.todo_cleanarch.databinding.FragmentListBinding
import com.example.todo_cleanarch.fragments.SharedViewModel
import com.example.todo_cleanarch.fragments.list.adapter.ListAdapter
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {
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

        // Set Menu
        setHasOptionsMenu(true)

        // return view
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery:String = "%$query%"

        mToDoViewModel.searchDatabase(searchQuery).observeOnce(viewLifecycleOwner, Observer { list ->
            list?.let {
                Log.d(TAG, "searchThroughDatabase: $searchQuery")
                adapter.setData(it)
            }
        })
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        // recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        // recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        // swipe to delete
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object: SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]

                // Delete Item
                mToDoViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                //Toast.makeText(requireContext(), "Successfully removed '${deletedItem.title}'", Toast.LENGTH_SHORT).show()

                // Restore delete item
                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: ToDoData, position: Int) {
        val snackbar = Snackbar.make(view, "Delete '${deletedItem.title}'", Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo") {
            mToDoViewModel.insertData(deletedItem)
            // Removed after StaggeredGridLayoutManager caused crash in 'Undo Delete'
            // adapter.notifyItemChanged(position)
        }
        snackbar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.menu_priority_high -> mToDoViewModel.sortByHighPriority().observe(viewLifecycleOwner, Observer {
                adapter.setData(it)
            })
            R.id.menu_priority_low -> mToDoViewModel.sortByLowPriority().observe(viewLifecycleOwner, Observer {
                adapter.setData(it)
            })
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