package com.example.cheesefactory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CheeseAdapter(
    private val cheese: List<CheeseData>,
    private val context: Context
): RecyclerView.Adapter<CheeseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cheese_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val cheese = cheese[position]
        holder.cheeseName.text = cheese.name
        holder.shortDes.text = cheese.shortDescription
        holder.cheeseImg.setImageResource(cheese.image)
    }

    override fun getItemCount(): Int {
        return cheese.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cheeseName: TextView = view.findViewById(R.id.cheeseName)
        val shortDes: TextView = view.findViewById(R.id.cheeseDescription)
        val cheeseImg: ImageView = view.findViewById(R.id.cheeseImage)
    }


}