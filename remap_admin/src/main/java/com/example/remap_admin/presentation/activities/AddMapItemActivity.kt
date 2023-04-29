package com.example.remap_admin.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.example.remap_admin.R
import com.example.remap_admin.domain.MapItem
import com.google.firebase.database.FirebaseDatabase

class AddMapItemActivity : AppCompatActivity() {

    //EdiText`s for MapItem fields
    private lateinit var etAddMapItemTitle: EditText
    private lateinit var etAddMapItemDescription: EditText
    private lateinit var etAddMapItemAdress: EditText
    private lateinit var etAddMapItemOfficeHours: EditText
    private lateinit var etAddMapItemContacts: EditText
    private lateinit var etAddMapItemLatitude: EditText
    private lateinit var etAddMapItemLongitude: EditText

    //Switch buttons for MapItem category fields
    private lateinit var switchAddMapItemEcomob: Switch
    private lateinit var switchAddMapItemPlastic: Switch
    private lateinit var switchAddMapItemGlass: Switch
    private lateinit var switchAddMapItemPaper: Switch
    private lateinit var switchAddMapItemBatteries: Switch
    private lateinit var switchAddMapItemClothes: Switch
    private lateinit var switchAddMapItemLamps: Switch
    private lateinit var switchAddMapItemMetal: Switch
    private lateinit var mapItemAddButton: Button
    private lateinit var mapItemMap: HashMap<String, MapItem>

    var database = FirebaseDatabase.getInstance().getReference()
    var addDB= database.child("Test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_map_item)
        supportActionBar?.hide()

        defineMapItemViews()
        mapItemAddButton.setOnClickListener {
            if(!inputValidate())
                return@setOnClickListener
            addDB.push().setValue(createMapItem())
            Toast.makeText(this, "Успешно!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun createMapItem(): MapItem {
        return MapItem(
            property_name = etAddMapItemTitle.text.toString(),
            property_description = etAddMapItemDescription.text.toString(),
            property_adress = etAddMapItemAdress.text.toString(),
            property_contacts = etAddMapItemContacts.text.toString(),
            property_office_hours = etAddMapItemOfficeHours.text.toString(),
            property_latitude = etAddMapItemLatitude.text.toString().toDouble(),
            property_longitude = etAddMapItemLongitude.text.toString().toDouble(),
            categoryEcomob = switchAddMapItemEcomob.isChecked,
            categoryPlastic = switchAddMapItemPlastic.isChecked,
            categoryGlass = switchAddMapItemGlass.isChecked,
            categoryPaper = switchAddMapItemPaper.isChecked,
            categoryBatteries = switchAddMapItemBatteries.isChecked,
            categoryClothes = switchAddMapItemClothes.isChecked,
            categoryLamps = switchAddMapItemLamps.isChecked,
            categoryMetal = switchAddMapItemMetal.isChecked,
        )
    }

    private fun defineMapItemViews() {
        etAddMapItemTitle = findViewById(R.id.et_add_map_item_title)
        etAddMapItemDescription = findViewById(R.id.et_add_map_item_description)
        etAddMapItemAdress = findViewById(R.id.et_add_map_item_adress)
        etAddMapItemOfficeHours = findViewById(R.id.et_add_map_item_office_hours)
        etAddMapItemContacts = findViewById(R.id.et_add_map_item_contacts)
        etAddMapItemLatitude = findViewById(R.id.et_add_map_item_latitude)
        etAddMapItemLongitude = findViewById(R.id.et_add_map_item_longitude)

        switchAddMapItemEcomob = findViewById(R.id.switch_add_map_item_ecomob)
        switchAddMapItemPlastic = findViewById(R.id.switch_add_map_item_plastic)
        switchAddMapItemGlass = findViewById(R.id.switch_add_map_item_glass)
        switchAddMapItemPaper = findViewById(R.id.switch_add_map_item_paper)
        switchAddMapItemBatteries = findViewById(R.id.switch_add_map_item_batteries)
        switchAddMapItemClothes = findViewById(R.id.switch_add_map_item_clothes)
        switchAddMapItemLamps = findViewById(R.id.switch_add_map_item_lamps)
        switchAddMapItemMetal = findViewById(R.id.switch_add_map_item_metal)
        mapItemAddButton = findViewById(R.id.map_item_add_button)
    }

    private fun inputValidate(): Boolean {
        var isValid = true
        if(TextUtils.isEmpty(etAddMapItemTitle.text.toString())) {
            etAddMapItemTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddMapItemDescription.toString())) {
            etAddMapItemTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddMapItemAdress.toString())) {
            etAddMapItemTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddMapItemContacts.toString())) {
            etAddMapItemTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddMapItemLatitude.toString())) {
            etAddMapItemTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddMapItemLongitude.toString())) {
            etAddMapItemTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddMapItemOfficeHours.toString())) {
            etAddMapItemTitle.setError("Пустая строка")
            isValid = false
        }
        return isValid
    }
}