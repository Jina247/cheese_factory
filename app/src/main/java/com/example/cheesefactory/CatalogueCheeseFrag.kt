package com.example.cheesefactory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text


class CatalogueCheeseFrag : Fragment() {
    private lateinit var viewModel: CheeseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogue_cheese, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cheeseImage : ImageView = view.findViewById(R.id.cheeseImage)
        val cheeseName : TextView = view.findViewById(R.id.cheeseName)
        val cheeseShortDesc : Text = view.findViewById(R.id.cheeseDescription)
        val like: ImageButton = view.findViewById(R.id.likeButton)
        viewModel = ViewModelProvider(requireActivity())[CheeseViewModel::class.java]
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCheeseCatalogView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        viewModel.cheeseList.observe(viewLifecycleOwner) { cheeseData ->
            recyclerView.adapter = CheeseAdapter(cheeseData, requireContext())
        }
    }
}