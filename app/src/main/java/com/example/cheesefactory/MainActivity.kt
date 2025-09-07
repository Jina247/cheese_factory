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
    private val catalogueCheeseFrag: CatalogueCheeseFrag =  CatalogueCheeseFrag()
    private val favouriteCheeseFrag: FavouriteCheeseFrag = FavouriteCheeseFrag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Show catalogue fragment initially
        supportFragmentManager.beginTransaction()
            .add(R.id.mainContent, catalogueCheeseFrag, "CATALOGUE")
            .commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.mainContent, favouriteCheeseFrag, "FAVOURITE")
            .hide(favouriteCheeseFrag)
            .commit()


        val favBtn: ImageButton = findViewById(R.id.favouriteBtn)
        val favTv: TextView = findViewById(R.id.favoriteTxt)
        val catalogueBtn: ImageButton = findViewById(R.id.catalogBtn)
        val catalogueTv: TextView = findViewById(R.id.catalogTxt)

        favBtn.setOnClickListener {
            favBtn.setColorFilter(ContextCompat.getColor(this, R.color.timber))
            favTv.setTextColor(ContextCompat.getColor(this, R.color.timber))
            catalogueBtn.setColorFilter(ContextCompat.getColor(this, android.R.color.darker_gray))
            catalogueTv.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))

            supportFragmentManager.beginTransaction()
                .hide(catalogueCheeseFrag)
                .show(favouriteCheeseFrag)
                .commit()
        }

        catalogueBtn.setOnClickListener {
            catalogueBtn.setColorFilter(ContextCompat.getColor(this, R.color.timber))
            catalogueTv.setTextColor(ContextCompat.getColor(this, R.color.timber))
            favBtn.setColorFilter(ContextCompat.getColor(this, android.R.color.darker_gray))
            favTv.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))

            supportFragmentManager.beginTransaction()
                .hide(favouriteCheeseFrag)
                .show(catalogueCheeseFrag)
                .commit()
        }

    }
}