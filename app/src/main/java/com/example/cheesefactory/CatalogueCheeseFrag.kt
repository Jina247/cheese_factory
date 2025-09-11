package com.example.cheesefactory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import java.util.Locale

class CatalogueCheeseFrag : Fragment() {
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
        setUpCheeseHelper()

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

        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                filterList(p0)
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        // Initialize views
        val filterSection: LinearLayout = view.findViewById(R.id.filterSection)
        val milkChipGroup: ChipGroup = view.findViewById(R.id.milkChipGrp)
        val flavourChipGroup: ChipGroup = view.findViewById(R.id.flavourChipGrp)
        val textureChipGroup: ChipGroup = view.findViewById(R.id.textureChipGrp)
        val ageChipGroup: ChipGroup = view.findViewById(R.id.ageChipGrp)

        val milkTitle: LinearLayout = view.findViewById(R.id.milkTitle)
        val textureTitle: LinearLayout = view.findViewById(R.id.textureTitle)
        val flavourTitle: LinearLayout = view.findViewById(R.id.flavourTitle)
        val ageTitle: LinearLayout = view.findViewById(R.id.ageTitle)
        val filterButton: CardView = view.findViewById(R.id.filterCard)

        // Set initial visibility - hide everything except filter section
        filterSection.isGone = true
        milkChipGroup.isGone = true
        flavourChipGroup.isGone = true
        textureChipGroup.isGone = true
        ageChipGroup.isGone = true

        // Set up click listeners
        filterButton.setOnClickListener {
            toggleButton(filterSection)
            // When filter section opens, make sure chip groups are hidden initially
            if (filterSection.isVisible) {
                milkChipGroup.isGone = true
                flavourChipGroup.isGone = true
                textureChipGroup.isGone = true
                ageChipGroup.isGone = true
            }
        }

        milkTitle.setOnClickListener {
            println("CheeseFragment: Milk title clicked - current visibility: ${milkChipGroup.visibility}")

            // Simple direct approach
            if (milkChipGroup.visibility == View.VISIBLE) {
                milkChipGroup.visibility = View.GONE
                println("CheeseFragment: Set milk chip group to GONE")
            } else {
                milkChipGroup.visibility = View.VISIBLE
                println("CheeseFragment: Set milk chip group to VISIBLE")
            }

            // Force parent to re-layout
            filterSection.requestLayout()
        }

        flavourTitle.setOnClickListener {
            println("CheeseFragment: Flavour title clicked - current visibility: ${flavourChipGroup.visibility}")

            if (flavourChipGroup.visibility == View.VISIBLE) {
                flavourChipGroup.visibility = View.GONE
            } else {
                flavourChipGroup.visibility = View.VISIBLE
            }
            filterSection.requestLayout()
        }

        textureTitle.setOnClickListener {
            println("CheeseFragment: Texture title clicked - current visibility: ${textureChipGroup.visibility}")

            if (textureChipGroup.visibility == View.VISIBLE) {
                textureChipGroup.visibility = View.GONE
            } else {
                textureChipGroup.visibility = View.VISIBLE
            }
            filterSection.requestLayout()
        }

        ageTitle.setOnClickListener {
            println("CheeseFragment: Age title clicked - current visibility: ${ageChipGroup.visibility}")

            if (ageChipGroup.visibility == View.VISIBLE) {
                ageChipGroup.visibility = View.GONE
            } else {
                ageChipGroup.visibility = View.VISIBLE
            }
            filterSection.requestLayout()
        }
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<CheeseData>()
            for (i in viewModel.cheeseList.value!!) {
                if (i.name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)) ) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No cheese is found", Toast.LENGTH_SHORT)
                    .show()
                cheeseAdapter.updateList(emptyList())
            } else {
                cheeseAdapter.updateList(filteredList)
            }
        }
    }

    fun setUpCheeseHelper() {
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
            R.drawable.mozzarella_di_bufala,
            R.drawable.mozzarella,
            R.drawable.parmesan,
            R.drawable.pecorino_romano,
            R.drawable.roquefort
        )
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
    }

    // Enhanced toggle function with debugging
    private fun toggleButton(view: View) {
        view.isGone = !view.isGone
        // Optional: Add logging to debug
        // Log.d("CheeseFragment", "Toggled ${view.id}: isGone = ${view.isGone}")
    }

    // Alternative toggle function if the above doesn't work
    private fun toggleButtonAlternative(view: View) {
        if (view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}