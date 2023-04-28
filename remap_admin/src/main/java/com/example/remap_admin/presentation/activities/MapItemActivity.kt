package com.example.remap_admin.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import com.example.remap_admin.R
import com.example.remap_admin.domain.MapItem

class MapItemActivity : AppCompatActivity() {

    private lateinit var etMapTitle: EditText
    private lateinit var etStreetName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_item)

        val data = intent.getParcelableExtra<MapItem>("Property")

        etMapTitle = findViewById(R.id.et_map_title)
        etStreetName = findViewById(R.id.et_street_name)

        etMapTitle.setText(data?.property_name)
        etStreetName.setText(data?.property_adress)

    }
}