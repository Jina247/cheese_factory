package com.example.cheesefactory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class CheeseAdapter(
    private var cheese: List<CheeseData>,
    private val context: Context,
    private val onLikeClick: (CheeseData) -> Unit,
    private val onItemClick: (CheeseData) -> Unit
): RecyclerView.Adapter<CheeseAdapter.ViewHolder>() {

    fun updateList(cList: List<CheeseData>) {
        this.cheese = cList
        notifyDataSetChanged()
    }
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
        val cheeseItem = cheese[position]
        holder.cheeseName.text = cheeseItem.name
        holder.shortDes.text = cheeseItem.shortDescription
        holder.cheeseImg.setImageResource(cheeseItem.image)

        if (!cheeseItem.isLiked) {
            holder.likeBtn.setImageResource(R.drawable.like)
        } else {
            holder.likeBtn.setImageResource(R.drawable.liked)
        }

        holder.likeBtn.setOnClickListener {
            onLikeClick(cheeseItem)
        }

        holder.selectedCheese.setOnClickListener {
            onItemClick(cheeseItem)
        }

    }

    override fun getItemCount(): Int {
        return cheese.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cheeseName: TextView = view.findViewById(R.id.cheeseName)
        val shortDes: TextView = view.findViewById(R.id.cheeseDescription)
        val cheeseImg: ImageView = view.findViewById(R.id.cheeseImage)
        val likeBtn: ImageButton = view.findViewById(R.id.likeButton)
        val selectedCheese: CardView = view.findViewById(R.id.cheeseCard)
    }


}