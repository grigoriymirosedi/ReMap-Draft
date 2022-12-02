package com.example.remap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EcoMarkersDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_eco_markers_details)

        val description = intent.getStringExtra("MARKER")
    }
}