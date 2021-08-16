package com.example.todo_cleanarch.fragments.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.todo_cleanarch.R


class UpdateFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }
}