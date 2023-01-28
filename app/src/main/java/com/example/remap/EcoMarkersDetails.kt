package com.example.remap

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.remap.models.EcoMarkers
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class EcoMarkersDetails : AppCompatActivity() {

    lateinit var ecoMarkerImage: ImageView
    lateinit var ecoMarkerTitle: TextView
    lateinit var ecoMarkerExamplesTitle: TextView
    lateinit var ecoMarkerExamples: TextView
    lateinit var ecoMarkerDescription: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_eco_markers_details)

        val description = intent.getParcelableExtra<EcoMarkers>("MARKER")

        ecoMarkerImage = findViewById(R.id.ecoMarkerImage)
        //ecoMarkerImage.setImageResource(description!!.imageContentURL)
        Picasso.get().load(description!!.imageContentURL).into(ecoMarkerImage)

        ecoMarkerTitle = findViewById(R.id.ecoMarkerTitle)
        ecoMarkerTitle.text = description.ecoTitle

        ecoMarkerExamplesTitle = findViewById(R.id.ecoMarkerExampleTitle)
        ecoMarkerExamplesTitle.text = description.ecoExamplesTitle

        ecoMarkerExamples = findViewById(R.id.ecoMarkerExamples)
        ecoMarkerExamples.text = description.ecoExamples

        ecoMarkerDescription = findViewById(R.id.ecoMarkerDescription)
        ecoMarkerDescription.text = description.ecoDescription

    }
}