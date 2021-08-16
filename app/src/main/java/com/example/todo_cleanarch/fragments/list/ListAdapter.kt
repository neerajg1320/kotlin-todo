package com.example.todo_cleanarch.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_cleanarch.R
import com.example.todo_cleanarch.data.models.Priority
import com.example.todo_cleanarch.data.models.ToDoData

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    var dataList = emptyList<ToDoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var toDoDataItem = dataList[position]

        holder.itemView.findViewById<TextView>(R.id.title_txt).text = toDoDataItem.title

        holder.itemView.findViewById<TextView>(R.id.description_txt).text = toDoDataItem.description
        val priority = toDoDataItem.priority
        when (priority) {
            Priority.HIGH -> holder.itemView
                .findViewById<CardView>(R.id.priority_indicator)
                .setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            Priority.MEDIUM -> holder.itemView
                .findViewById<CardView>(R.id.priority_indicator)
                .setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
            Priority.LOW -> holder.itemView
                .findViewById<CardView>(R.id.priority_indicator)
                .setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        }

        holder.itemView.findViewById<ConstraintLayout>(R.id.row_background).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            // holder.itemView.findNavController().navigate(R.id.action_listFragment_to_updateFragment)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoDataList: List<ToDoData>) {
        this.dataList = toDoDataList
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}