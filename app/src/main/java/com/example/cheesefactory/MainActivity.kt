package com.example.cheesefactory

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var catalogueCheeseFrag: CatalogueCheeseFrag
    private lateinit var favouriteCheeseFrag: FavouriteCheeseFrag
    private var currentTab = "CATALOGUE"
    private lateinit var catalogueBtn: ImageButton
    private lateinit var favouriteBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        createFragments()
        navigateButton()
    }
    private fun createFragments() {
        catalogueCheeseFrag = CatalogueCheeseFrag()
        favouriteCheeseFrag = FavouriteCheeseFrag()
        // Add both fragments in one transaction
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContent, catalogueCheeseFrag, "CATALOGUE")
            .add(R.id.mainContent, favouriteCheeseFrag, "FAVOURITE")
            .hide(favouriteCheeseFrag)
            .commit()
    }

    private fun switchTab(tabName: String) {
        if (currentTab == tabName) return
        when (tabName) {
            "CATALOGUE" ->
                supportFragmentManager.beginTransaction()
                    .show(catalogueCheeseFrag)
                    .hide(favouriteCheeseFrag)
                    .commit()
            "FAVOURITE" ->
                supportFragmentManager.beginTransaction()
                    .show(favouriteCheeseFrag)
                    .hide(catalogueCheeseFrag)
                    .commit()
        }
        currentTab = tabName
        updateTabColors()
    }
    private fun navigateButton() {
        catalogueBtn = findViewById(R.id.catalogBtn)
        favouriteBtn = findViewById(R.id.favouriteBtn)
        catalogueBtn.setOnClickListener {
            switchTab("CATALOGUE")
        }
        favouriteBtn.setOnClickListener {
            switchTab("FAVOURITE")
        }
    }

    private fun updateTabColors() {
        catalogueBtn = findViewById(R.id.catalogBtn)
        favouriteBtn = findViewById(R.id.favouriteBtn)
        val catalogueTxt = findViewById<TextView>(R.id.catalogTxt)
        val favouriteTxt = findViewById<TextView>(R.id.favoriteTxt)

        val activeColor = ContextCompat.getColor(this, R.color.timber)
        val inactiveColor = ContextCompat.getColor(this, android.R.color.darker_gray)

        if (currentTab == "CATALOGUE") {
            catalogueBtn.setColorFilter(activeColor)
            catalogueTxt.setTextColor(activeColor)
            favouriteBtn.setColorFilter(inactiveColor)
            favouriteTxt.setTextColor(inactiveColor)
        } else {
            favouriteBtn.setColorFilter(activeColor)
            favouriteTxt.setTextColor(activeColor)
            catalogueBtn.setColorFilter(inactiveColor)
            catalogueTxt.setTextColor(inactiveColor)
        }
    }
}