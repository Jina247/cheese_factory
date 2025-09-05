package com.example.cheesefactory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavouriteCheeseFrag : Fragment() {
    private var cheeseFavAdapter: CheeseAdapter? = null
    private lateinit var viewModel: CheeseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite_cheese, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[CheeseViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCheeseFavouriteView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.favouriteCheeseList.observe(viewLifecycleOwner) { favouriteCheeseData ->

            if (favouriteCheeseData.isEmpty()) {
                Toast.makeText(requireContext(), "No favourite cheeses yet!", Toast.LENGTH_SHORT).show()
            }

            cheeseFavAdapter = CheeseAdapter(favouriteCheeseData, requireContext()) { clickedCheese ->
                // Handle like/unlike in favorites
                val message = "${clickedCheese.name} removed from favorites"

                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                val currentFavs = viewModel.favouriteCheeseList.value ?: mutableListOf()
                viewModel.doLike(clickedCheese, currentFavs)
            }
            recyclerView.adapter = cheeseFavAdapter
        }
    }
}