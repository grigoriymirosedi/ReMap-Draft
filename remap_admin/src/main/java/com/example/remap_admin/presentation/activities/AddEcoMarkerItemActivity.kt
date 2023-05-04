package com.example.remap_admin.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.remap_admin.EcoMarkerItem
import com.example.remap_admin.R
import com.google.firebase.database.FirebaseDatabase

class AddEcoMarkerItemActivity : AppCompatActivity() {

    //EdiText`s for MapItem fields
    private lateinit var etAddEcoMarkerItemTitle: EditText
    private lateinit var etAddEcoMarkerItemDescription: EditText
    private lateinit var etAddEcoMarkerItemExamplesTitle: EditText
    private lateinit var etAddEcoMarkerItemExamples: EditText
    private lateinit var etAddEcoMarkerItemContentURL: EditText
    private lateinit var etAddEcoMarkerItemIconURL: EditText
    private lateinit var ecoMarkerAddButton: Button

    private var database = FirebaseDatabase.getInstance().getReference()
    private var addEcoMarkerItemDB = database.child("TestEcoMarker")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_eco_marker_item)
        supportActionBar?.hide()

        defineEcoMarkerViews()

        ecoMarkerAddButton.setOnClickListener {
            if(!inputValidate())
                return@setOnClickListener
            addEcoMarkerItemDB.push().setValue(createMapItem())
            Toast.makeText(this, "Успешно!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun createMapItem(): EcoMarkerItem {
        return EcoMarkerItem(
            ecoTitle = etAddEcoMarkerItemTitle.text.toString(),
            ecoDescription = etAddEcoMarkerItemDescription.text.toString(),
            ecoExamplesTitle = etAddEcoMarkerItemExamples.text.toString(),
            ecoExamples = etAddEcoMarkerItemExamples.text.toString(),
            imageIconURL = etAddEcoMarkerItemIconURL.text.toString(),
            imageContentURL = etAddEcoMarkerItemContentURL.text.toString()
        )
    }

    private fun defineEcoMarkerViews() {
        etAddEcoMarkerItemTitle = findViewById(R.id.et_add_ecomarker_item_title)
        etAddEcoMarkerItemDescription = findViewById(R.id.et_add_ecomarker_item_description)
        etAddEcoMarkerItemExamplesTitle = findViewById(R.id.et_add_ecomarker_item_examples_title)
        etAddEcoMarkerItemExamples = findViewById(R.id.et_add_ecomarker_item_examples)
        etAddEcoMarkerItemContentURL = findViewById(R.id.et_add_ecomarker_item_content_image)
        etAddEcoMarkerItemIconURL = findViewById(R.id.et_add_ecomarker_item_icon_image)
        ecoMarkerAddButton = findViewById(R.id.ecomarker_item_add_button)
    }

    private fun inputValidate(): Boolean {
        var isValid = true
        if(TextUtils.isEmpty(etAddEcoMarkerItemTitle.text.toString())) {
            etAddEcoMarkerItemTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddEcoMarkerItemDescription.text.toString())) {
            etAddEcoMarkerItemDescription.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddEcoMarkerItemExamplesTitle.text.toString())) {
            etAddEcoMarkerItemExamplesTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddEcoMarkerItemExamples.text.toString())) {
            etAddEcoMarkerItemExamples.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddEcoMarkerItemIconURL.text.toString())) {
            etAddEcoMarkerItemIconURL.setError("Ссылка не может быть пустой")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddEcoMarkerItemContentURL.text.toString())) {
            etAddEcoMarkerItemContentURL.setError("Ссылка не может быть пустой")
            isValid = false
        }
        return isValid
    }
}