package com.example.cheesefactory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CatalogueCheeseFrag : Fragment() {
    val cheeseImg = arrayOf(
        R.drawable.brie,
        R.drawable.burrata,
        R.drawable.cabrales,
        R.drawable.camembert,
        R.drawable.cheddar,
        R.drawable.chevre,
        R.drawable.colby,
        R.drawable.cottage,
        R.drawable.emmental,
        R.drawable.garrotxa,
        R.drawable.gouda,
        R.drawable.gru,
        R.drawable.havarti,
        R.drawable.manchego,
        R.drawable.mascarpone,
        R.drawable.mozzarella,
        R.drawable.mozzarella_di_bufala,
        R.drawable.parmesan,
        R.drawable.pecorino_romano,
        R.drawable.roquefort
    )
    private lateinit var viewModel: CheeseViewModel
    private lateinit var cheeseAdapter: CheeseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogue_cheese, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CheeseViewModel::class.java]
        val cheeseName = resources.getStringArray(R.array.cheese_names)
        val cheeseShortDesc = resources.getStringArray(R.array.cheese_short_desc)
        viewModel.setUpCheese(cheeseImg, cheeseName, cheeseShortDesc)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCheeseCatalogView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.cheeseList.observe(viewLifecycleOwner) { cheeseData ->
            cheeseAdapter = CheeseAdapter(
                cheeseData,
                requireContext()
            ) { clickedCheese ->
                val message = if (clickedCheese.isLiked) {
                    "${clickedCheese.name} removed from favorites"
                } else {
                    "${clickedCheese.name} added to favorites!"
                }

                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                val currentFavs = viewModel.favouriteCheeseList.value ?: mutableListOf()
                viewModel.doLike(clickedCheese, currentFavs)
            }
            recyclerView.adapter = cheeseAdapter
        }
    }
}