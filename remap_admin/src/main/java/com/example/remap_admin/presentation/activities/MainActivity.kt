package com.example.remap_admin.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.remap_admin.R
import com.example.remap_admin.presentation.fragments.ArticleFragment
import com.example.remap_admin.presentation.fragments.EcoMarkerFragment
import com.example.remap_admin.presentation.fragments.MapFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mapFragment: Fragment
    private lateinit var articleFragment: Fragment
    private lateinit var ecoMarkerFragment: EcoMarkerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        mapFragment = MapFragment()
        articleFragment = ArticleFragment()
        ecoMarkerFragment = EcoMarkerFragment()

        setCurrentFragment(mapFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.miMap -> setCurrentFragment(mapFragment)
                R.id.miArticles -> setCurrentFragment(articleFragment)
                R.id.miEcoMarkers -> setCurrentFragment(ecoMarkerFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}