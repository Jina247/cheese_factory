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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.Locale

class CatalogueCheeseFrag : Fragment() {
    private val viewModel: CheeseViewModel by lazy {
        ViewModelProvider(requireActivity())[CheeseViewModel::class.java]
    }
    private lateinit var cheeseAdapter: CheeseAdapter
    private val tempMilk = mutableSetOf<String>()
    private val tempTexture = mutableSetOf<String>()
    private val tempFlavour = mutableSetOf<String>()
    private val tempAge = mutableSetOf<String>()

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
                    val currentFav = viewModel.favouriteCheeseList.value ?: mutableListOf()
                    viewModel.doLike(clickedCheese, currentFav)
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
                searchList(p0)
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchList(p0)
                return true
            }
        })

        val filterSection: LinearLayout = view.findViewById(R.id.filterSection)
        val milkChipGroup: ChipGroup = view.findViewById(R.id.milkChipGrp)
        val flavourChipGroup: ChipGroup = view.findViewById(R.id.flavourChipGrp)
        val textureChipGroup: ChipGroup = view.findViewById(R.id.textureChipGrp)
        val ageChipGroup: ChipGroup = view.findViewById(R.id.ageChipGrp)

        val milkTitle: LinearLayout = view.findViewById(R.id.milkTitle)
        val textureTitle: LinearLayout = view.findViewById(R.id.textureTitle)
        val flavourTitle: LinearLayout = view.findViewById(R.id.flavourTitle)
        val ageTitle: LinearLayout = view.findViewById(R.id.ageTitle)

        filterSection.isGone = true
        milkChipGroup.isGone = true
        flavourChipGroup.isGone = true
        textureChipGroup.isGone = true
        ageChipGroup.isGone = true

        val filterButton: CardView = view.findViewById(R.id.filterCard)
        filterButton.setOnClickListener {
            toggleButton(filterSection)
            if (filterSection.isVisible) {
                milkChipGroup.isGone = true
                flavourChipGroup.isGone = true
                textureChipGroup.isGone = true
                ageChipGroup.isGone = true
            }
        }

        milkTitle.setOnClickListener {
            toggleButton(milkChipGroup)
            setUpMilkChips(milkChipGroup)
        }

        flavourTitle.setOnClickListener {
            toggleButton(flavourChipGroup)
            setUpFlavourChips(flavourChipGroup)
        }

        textureTitle.setOnClickListener {
            toggleButton(textureChipGroup)
            setUpTextureChips(textureChipGroup)
        }

        ageTitle.setOnClickListener {
            toggleButton(ageChipGroup)
            setUpAgedChips(ageChipGroup)
        }

        val submit: LinearLayout = view.findViewById(R.id.submitBtn)
        submit.setOnClickListener {
            viewModel.setFilters(tempMilk, tempTexture, tempFlavour, tempAge)
            viewModel.filteredList.observe(viewLifecycleOwner) { cheeseData ->
                cheeseAdapter.updateList(cheeseData)
                filterSection.isGone = true
            }
        }
    }

    private fun searchList(query: String?) {
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
                cheeseAdapter.updateList(mutableListOf())
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
    fun toggleButton(view: View) {
        view.isGone = !view.isGone
    }

    fun setUpMilkChips(chipGroup: ChipGroup) {
        val cow: Chip = chipGroup.findViewById(R.id.cow)
        val buffalo: Chip = chipGroup.findViewById(R.id.buffalo)
        val sheep: Chip = chipGroup.findViewById(R.id.sheep)
        val goat: Chip = chipGroup.findViewById(R.id.goat)
        val mixed: Chip = chipGroup.findViewById(R.id.mixed)

        cow.setOnCheckedChangeListener { _, isChecked ->
            tempMilk.setUpHelper("Cow\'s Milk", isChecked)
        }

        buffalo.setOnCheckedChangeListener { _, isChecked ->
            tempMilk.setUpHelper("Buffalo\'s Milk", isChecked)
        }

        sheep.setOnCheckedChangeListener { _, isChecked ->
            tempMilk.setUpHelper("Sheep\'s Milk", isChecked)
        }

        goat.setOnCheckedChangeListener { _, isChecked ->
            tempMilk.setUpHelper("Goat\'s Milk", isChecked)
        }

        mixed.setOnCheckedChangeListener { _, isChecked ->
            tempMilk.setUpHelper("Mixed Milk", isChecked)
        }
    }

    fun setUpTextureChips(chipGroup: ChipGroup) {
        val soft: Chip = chipGroup.findViewById(R.id.soft)
        val s_soft: Chip = chipGroup.findViewById(R.id.s_soft)
        val s_hard: Chip = chipGroup.findViewById(R.id.s_hard)
        val hard: Chip = chipGroup.findViewById(R.id.hard)
        val crumbly: Chip = chipGroup.findViewById(R.id.crumbly)
        val spreadable: Chip = chipGroup.findViewById(R.id.spread)

        soft.setOnCheckedChangeListener { _, isChecked ->
            tempTexture.setUpHelper("Soft", isChecked)
        }

        s_soft.setOnCheckedChangeListener { _, isChecked ->
            tempTexture.setUpHelper("Semi-soft", isChecked)
        }

        s_hard.setOnCheckedChangeListener { _, isChecked ->
            tempTexture.setUpHelper("Semi-hard", isChecked)
        }

        hard.setOnCheckedChangeListener { _, isChecked ->
            tempTexture.setUpHelper("Hard", isChecked)
        }

        crumbly.setOnCheckedChangeListener { _, isChecked ->
            tempTexture.setUpHelper("Crumbly", isChecked)
        }

        spreadable.setOnCheckedChangeListener { _, isChecked ->
            tempTexture.setUpHelper("Spreadable", isChecked)
        }
    }

    fun setUpFlavourChips(chipGroup: ChipGroup) {
        val mild: Chip = chipGroup.findViewById(R.id.mild)
        val buttery: Chip = chipGroup.findViewById(R.id.buttery)
        val nutty: Chip = chipGroup.findViewById(R.id.nutty)
        val tangy: Chip = chipGroup.findViewById(R.id.tangy)
        val sharp: Chip = chipGroup.findViewById(R.id.sharp)
        val pungent: Chip = chipGroup.findViewById(R.id.pungent)
        val sweet: Chip = chipGroup.findViewById(R.id.sweet)

        mild.setOnCheckedChangeListener { _, isChecked ->
            tempFlavour.setUpHelper("Mild", isChecked)
        }

        buttery.setOnCheckedChangeListener { _, isCheck ->
            tempFlavour.setUpHelper("Buttery", isCheck)
        }

        nutty.setOnCheckedChangeListener { _, isCheck ->
            tempFlavour.setUpHelper("Nutty", isCheck)
        }

        tangy.setOnCheckedChangeListener { _, isCheck ->
            tempFlavour.setUpHelper("Tangy", isCheck)
        }

        sharp.setOnCheckedChangeListener { _, isCheck ->
            tempFlavour.setUpHelper("Sharp", isCheck)
        }

        pungent.setOnCheckedChangeListener { _, isCheck ->
            tempFlavour.setUpHelper("Pungent", isCheck)
        }
        sweet.setOnCheckedChangeListener { _, isCheck ->
            tempFlavour.setUpHelper("Sweet", isCheck)
        }
    }

    fun setUpAgedChips(chipGroup: ChipGroup) {
        val fresh: Chip = chipGroup.findViewById(R.id.fresh)
        val young: Chip = chipGroup.findViewById(R.id.young)
        val matured: Chip = chipGroup.findViewById(R.id.matured)
        val aged: Chip = chipGroup.findViewById(R.id.aged)
        val xAged: Chip = chipGroup.findViewById(R.id.extraAged)

        fresh.setOnCheckedChangeListener { _, isChecked ->
            tempAge.setUpHelper("Fresh (0-2 weeks)", isChecked)
        }

        young.setOnCheckedChangeListener { _, isChecked ->
            tempAge.setUpHelper("Young (2-8 weeks)", isChecked)
        }

        matured.setOnCheckedChangeListener { _, isChecked ->
            tempAge.setUpHelper("Matured (2-6 months)", isChecked)
        }

        aged.setOnCheckedChangeListener { _, isChecked ->
            tempAge.setUpHelper("Aged (6 months-1 year)", isChecked)
        }

        xAged.setOnCheckedChangeListener { _, isChecked ->
            tempAge.setUpHelper("Extra Aged (1+ years)", isChecked)
        }

    }

    private fun MutableSet<String>.setUpHelper(value: String, isChecked: Boolean) {
        if (isChecked) add(value) else remove(value)
    }


}