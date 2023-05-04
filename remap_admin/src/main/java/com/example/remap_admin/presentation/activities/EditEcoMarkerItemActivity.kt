package com.example.remap_admin.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.remap_admin.EcoMarkerItem
import com.example.remap_admin.R
import com.example.remap_admin.domain.MapItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditEcoMarkerItemActivity : AppCompatActivity() {

    //EdiText`s for MapItem fields
    private lateinit var etEcoMarkerItemTitle: EditText
    private lateinit var etEcoMarkerItemDescription: EditText
    private lateinit var etEcoMarkerItemExamplesTitle: EditText
    private lateinit var etEcoMarkerItemExamples: EditText
    private lateinit var etEcoMarkerItemContentURL: EditText
    private lateinit var etEcoMarkerItemIconURL: EditText

    private lateinit var intentData: EcoMarkerItem
    private lateinit var ecoMarkerItemApplyButton: Button
    private lateinit var ecoMarkerItemMap: HashMap<String, EcoMarkerItem>

    var database = FirebaseDatabase.getInstance().getReference()
    var editdb = database.child("TestEcoMarker")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_eco_marker_item)
        supportActionBar?.hide()

        ecoMarkerItemMap = HashMap()
        intentData = intent.getParcelableExtra<EcoMarkerItem>("EcoMarker")!!
        var mapItemQuery = database.child("TestEcoMarker").orderByChild("ecoTitle").equalTo(intentData.ecoTitle)

        defineEcoMarkerItemViews()
        setEcoMarkerItemData()

        ecoMarkerItemApplyButton.setOnClickListener {
            if (!inputValidate())
                return@setOnClickListener
            mapItemQuery.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(mapItemSnapshot in snapshot.children) {
                        ecoMarkerItemMap.put(mapItemSnapshot.key.toString(), applySettings())
                    }
                    editdb.updateChildren(ecoMarkerItemMap as Map<String, Any>)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            Toast.makeText(this, "Изменения внесены", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun applySettings(): EcoMarkerItem{
        return EcoMarkerItem(
            ecoTitle = etEcoMarkerItemTitle.text.toString(),
            ecoDescription = etEcoMarkerItemDescription.text.toString(),
            ecoExamplesTitle = etEcoMarkerItemExamplesTitle.text.toString(),
            ecoExamples = etEcoMarkerItemExamples.text.toString(),
            imageIconURL = etEcoMarkerItemIconURL.text.toString(),
            imageContentURL = etEcoMarkerItemContentURL.text.toString(),
        )
    }

    private fun defineEcoMarkerItemViews() {
        etEcoMarkerItemTitle = findViewById(R.id.et_ecomarker_item_title)
        etEcoMarkerItemDescription = findViewById(R.id.et_ecomarker_item_description)
        etEcoMarkerItemExamplesTitle = findViewById(R.id.et_ecomarker_item_examples_title)
        etEcoMarkerItemExamples = findViewById(R.id.et_ecomarker_item_examples)
        etEcoMarkerItemIconURL = findViewById(R.id.et_ecomarker_item_icon_image)
        etEcoMarkerItemContentURL = findViewById(R.id.et_ecomarker_item_content_image)
        ecoMarkerItemApplyButton = findViewById(R.id.ecomarker_item_apply_button)
    }

    private fun setEcoMarkerItemData() {
        etEcoMarkerItemTitle.setText(intentData.ecoTitle)
        etEcoMarkerItemDescription.setText(intentData.ecoDescription)
        etEcoMarkerItemExamplesTitle.setText(intentData.ecoExamplesTitle)
        etEcoMarkerItemExamples.setText(intentData.ecoExamples)
        etEcoMarkerItemIconURL.setText(intentData.imageIconURL)
        etEcoMarkerItemContentURL.setText(intentData.imageContentURL)
    }

    private fun inputValidate(): Boolean {
        var isValid = true
        if(TextUtils.isEmpty(etEcoMarkerItemTitle.text.toString())) {
            etEcoMarkerItemTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etEcoMarkerItemDescription.text.toString())) {
            etEcoMarkerItemDescription.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etEcoMarkerItemExamplesTitle.text.toString())) {
            etEcoMarkerItemExamplesTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etEcoMarkerItemExamples.text.toString())) {
            etEcoMarkerItemExamples.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etEcoMarkerItemIconURL.text.toString())) {
            etEcoMarkerItemIconURL.setError("Ссылка не может быть пустой")
            isValid = false
        }
        if(TextUtils.isEmpty(etEcoMarkerItemContentURL.text.toString())) {
            etEcoMarkerItemContentURL.setError("Ссылка не может быть пустой")
            isValid = false
        }
        return isValid
    }

}