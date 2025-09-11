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
    private lateinit var cheeseFavAdapter: CheeseAdapter
    private val viewModel: CheeseViewModel by lazy {
        ViewModelProvider(requireActivity())[CheeseViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite_cheese, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCheeseFavouriteView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.favouriteCheeseList.observe(viewLifecycleOwner) { favouriteCheeseData ->

            if (favouriteCheeseData.isEmpty()) {

            }

            cheeseFavAdapter = CheeseAdapter(favouriteCheeseData, requireContext(),
            onLikeClick = { clickedCheese ->
                val message = "${clickedCheese.name} removed from favorites"

                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                val currentFav = viewModel.favouriteCheeseList.value ?: mutableListOf()
                viewModel.doLike(clickedCheese, currentFav)

            }, onItemClick = { clickedCheese ->
                    viewModel.selectCheese(clickedCheese)
                    // Navigate to detail fragment
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.mainContent, DetailCheese())
                        .addToBackStack(null)
                        .commit()
            })
            recyclerView.adapter = cheeseFavAdapter
        }
    }
}