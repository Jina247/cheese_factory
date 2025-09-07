package com.example.cheesefactory

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class DetailCheese : Fragment() {
    private val viewModel: CheeseViewModel by lazy {
        ViewModelProvider(requireActivity())[CheeseViewModel::class.java]
    }

    companion object {
        const val TAG = "DETAIL"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_cheese, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backBtn: ImageButton = view.findViewById(R.id.backButton)
        val likeBtn: ImageButton = view.findViewById(R.id.favoriteButton)
        val image: ImageView = view.findViewById(R.id.cheeseDetailImage)
        val name: TextView = view.findViewById(R.id.cheeseDetailName)
        val origin: TextView = view.findViewById(R.id.cheeseOrigin)
        val milkType: TextView = view.findViewById(R.id.cheeseMilkType)
        val age: TextView = view.findViewById(R.id.cheeseAging)
        val desc: TextView = view.findViewById(R.id.cheeseDescription)
        val foodPair: TextView = view.findViewById(R.id.foodPairing)
        val winePair: TextView = view.findViewById(R.id.wineParning)

        viewModel.selectedCheese.observe(viewLifecycleOwner) { cheeseData ->
            cheeseData?.let { cheese ->
                // Populate UI with cheese data
                image.setImageResource(cheese.image)
                name.text = cheese.name
                origin.text = cheese.origin
                milkType.text = cheese.milkSource
                age.text = cheese.age
                desc.text = cheese.longDescription
                foodPair.text = cheese.foodPairing
                winePair.text = cheese.winePairing

                // Update like button appearance based on current state
                updateLikeButton(likeBtn, cheese.isLiked)

                // Handle back button
                backBtn.setOnClickListener {
                    parentFragmentManager.popBackStack()
                }

                // Handle like button - FIXED LOGIC
                likeBtn.setOnClickListener {
                    val currentFavs = viewModel.favouriteCheeseList.value ?: mutableListOf()
                    viewModel.doLike(cheese, currentFavs)

                    // Show toast message
                    val message = if (cheese.isLiked) {
                        "${cheese.name} added to favorites!"
                    } else {
                        "${cheese.name} removed from favorites"
                    }
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                    // Update button appearance
                    updateLikeButton(likeBtn, cheese.isLiked)
                }
            }
        }
    }

    private fun updateLikeButton(button: ImageButton, isLiked: Boolean) {
        if (isLiked) {
            button.setImageResource(R.drawable.liked)
        } else {
            button.setImageResource(R.drawable.like)
        }
    }
}