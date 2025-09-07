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
    private val cheeseImg = arrayOf(
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
        R.drawable.mozzarella_di_bufala,
        R.drawable.mozzarella,
        R.drawable.parmesan,
        R.drawable.pecorino_romano,
        R.drawable.roquefort
    )
    private val viewModel: CheeseViewModel by lazy {
        ViewModelProvider(requireActivity())[CheeseViewModel::class.java]
    }

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
        val cheeseName = resources.getStringArray(R.array.cheese_names)
        val cheeseShortDesc = resources.getStringArray(R.array.cheese_short_desc)
        val cheeseLongDesc = resources.getStringArray(R.array.cheese_descriptions)
        val cheeseOrigin = resources.getStringArray(R.array.cheese_origins)
        val cheeseMilkSource = resources.getStringArray(R.array.cheese_milk_source)
        val cheeseTexture = resources.getStringArray(R.array.cheese_texture)
        val cheeseAge = resources.getStringArray(R.array.cheese_aging)
        val cheeseFlavour = resources.getStringArray(R.array.cheese_flavor)
        val cheeseFoodPairing = resources.getStringArray(R.array.food_pairings)
        val cheeseWinePairing = resources.getStringArray(R.array.wine_pairings)
        if (viewModel.cheeseList.value.isNullOrEmpty()) {
            viewModel.setUpCheese(
                cheeseImg,
                cheeseName,
                cheeseShortDesc,
                cheeseLongDesc,
                cheeseOrigin,
                cheeseMilkSource,
                cheeseTexture,
                cheeseAge,
                cheeseFlavour,
                cheeseFoodPairing,
                cheeseWinePairing
            )
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCheeseCatalogView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())


        viewModel.cheeseList.observe(viewLifecycleOwner) { cheeseData ->
            cheeseAdapter = CheeseAdapter(
                cheeseData,
                requireContext(),
                onLikeClick = { clickedCheese ->
                    val message = if (clickedCheese.isLiked) {
                        "${clickedCheese.name} removed from favorites"
                    } else {
                        "${clickedCheese.name} added to favorites!"
                    }
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    val currentFavs = viewModel.favouriteCheeseList.value ?: mutableListOf()
                    viewModel.doLike(clickedCheese, currentFavs)
                },
                onItemClick = { clickedCheese ->
                    // Save clicked cheese in ViewModel
                    viewModel.selectCheese(clickedCheese)

                    parentFragmentManager.beginTransaction()
                        .hide(parentFragmentManager.findFragmentByTag("CATALOGUE")!!)
                        .add(R.id.mainContent, DetailCheese(), "DETAIL")
                        .addToBackStack("DETAIL")
                        .commit()

                }
            )
            recyclerView.adapter = cheeseAdapter
        }

    }
}