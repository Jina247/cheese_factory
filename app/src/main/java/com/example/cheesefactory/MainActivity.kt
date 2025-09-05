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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val catalogueFragment = CatalogueCheeseFrag()
        val favouriteFragment = FavouriteCheeseFrag()

        // Show catalogue fragment initially
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, catalogueFragment)
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
                .replace(R.id.mainContent, favouriteFragment)
                .commit()
        }

        catalogueBtn.setOnClickListener {
            catalogueBtn.setColorFilter(ContextCompat.getColor(this, R.color.timber))
            catalogueTv.setTextColor(ContextCompat.getColor(this, R.color.timber))
            favBtn.setColorFilter(ContextCompat.getColor(this, android.R.color.darker_gray))
            favTv.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))

            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContent, catalogueFragment)
                .commit()
        }
    }
}