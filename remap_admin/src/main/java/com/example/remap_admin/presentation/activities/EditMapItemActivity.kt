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
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditMapItemActivity : AppCompatActivity() {

    //EdiText`s for MapItem fields
    private lateinit var etMapItemTitle: EditText
    private lateinit var etMapItemDescription: EditText
    private lateinit var etMapItemAdress: EditText
    private lateinit var etMapItemOfficeHours: EditText
    private lateinit var etMapItemContacts: EditText
    private lateinit var etMapItemLatitude: EditText
    private lateinit var etMapItemLongitude: EditText

    //Switch buttons for MapItem category fields
    private lateinit var switchMapItemEcomob: SwitchMaterial
    private lateinit var switchMapItemPlastic: SwitchMaterial
    private lateinit var switchMapItemGlass: SwitchMaterial
    private lateinit var switchMapItemPaper: SwitchMaterial
    private lateinit var switchMapItemBatteries: SwitchMaterial
    private lateinit var switchMapItemClothes: SwitchMaterial
    private lateinit var switchMapItemLamps: SwitchMaterial
    private lateinit var switchMapItemMetal: SwitchMaterial
    private lateinit var intentData: MapItem
    private lateinit var mapItemApplyButton: Button
    private lateinit var mapItemMap: HashMap<String, MapItem>

    var database = FirebaseDatabase.getInstance().getReference()
    var editdb = database.child("Properties")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_map_item)
        supportActionBar?.hide()

        mapItemMap = HashMap()
        intentData = intent.getParcelableExtra<MapItem>("Property")!!
        var mapItemQuery = database.child("Properties").orderByChild("property_adress").equalTo(intentData.property_adress)

        defineMapItemViews()
        setMapItemData()

        mapItemApplyButton.setOnClickListener {
            if (!inputValidate())
                return@setOnClickListener
            mapItemQuery.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(mapItemSnapshot in snapshot.children) {
                        mapItemMap.put(mapItemSnapshot.key.toString(), applySettings())
                    }
                    editdb.updateChildren(mapItemMap as Map<String, Any>)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            Toast.makeText(this, "Изменения внесены", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun inputValidate(): Boolean {
        var isValid = true
        if(TextUtils.isEmpty(etMapItemTitle.text.toString())) {
            etMapItemTitle.setError("Название не может быть пустым")
            isValid = false
        }
        if(TextUtils.isEmpty(etMapItemDescription.text.toString())) {
            etMapItemDescription.setError("Описание не может быть пустым")
            isValid = false
        }
        if(TextUtils.isEmpty(etMapItemAdress.text.toString())) {
            etMapItemAdress.setError("Адрес не может быть пустым")
            isValid = false
        }
        if(TextUtils.isEmpty(etMapItemContacts.text.toString())) {
            etMapItemContacts.setError("Контакты не могут быть пустыми")
            isValid = false
        }
        if(TextUtils.isEmpty(etMapItemLatitude.text.toString())) {
            etMapItemLatitude.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etMapItemLongitude.text.toString())) {
            etMapItemLongitude.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etMapItemOfficeHours.text.toString())) {
            etMapItemOfficeHours.setError("Пустая строка")
            isValid = false
        }
        return isValid
    }

    private fun applySettings(): MapItem {
        return MapItem(
            property_name = etMapItemTitle.text.toString(),
            property_description = etMapItemDescription.text.toString(),
            property_adress = etMapItemAdress.text.toString(),
            property_contacts = etMapItemContacts.text.toString(),
            property_office_hours = etMapItemOfficeHours.text.toString(),
            property_latitude = etMapItemLatitude.text.toString().toDouble(),
            property_longitude = etMapItemLongitude.text.toString().toDouble(),
            categoryEcomob = switchMapItemEcomob.isChecked,
            categoryPlastic = switchMapItemPlastic.isChecked,
            categoryGlass = switchMapItemGlass.isChecked,
            categoryPaper = switchMapItemPaper.isChecked,
            categoryBatteries = switchMapItemBatteries.isChecked,
            categoryClothes = switchMapItemClothes.isChecked,
            categoryLamps = switchMapItemLamps.isChecked,
            categoryMetal = switchMapItemMetal.isChecked,
        )
    }

    private fun defineMapItemViews() {
        etMapItemTitle = findViewById(R.id.et_map_item_title)
        etMapItemDescription = findViewById(R.id.et_map_item_description)
        etMapItemAdress = findViewById(R.id.et_map_item_adress)
        etMapItemOfficeHours = findViewById(R.id.et_map_item_office_hours)
        etMapItemContacts = findViewById(R.id.et_map_item_contacts)
        etMapItemLatitude = findViewById(R.id.et_map_item_latitude)
        etMapItemLongitude = findViewById(R.id.et_map_item_longitude)

        switchMapItemEcomob = findViewById(R.id.switch_map_item_ecomob)
        switchMapItemPlastic = findViewById(R.id.switch_map_item_plastic)
        switchMapItemGlass = findViewById(R.id.switch_map_item_glass)
        switchMapItemPaper = findViewById(R.id.switch_map_item_paper)
        switchMapItemBatteries = findViewById(R.id.switch_map_item_batteries)
        switchMapItemClothes = findViewById(R.id.switch_map_item_clothes)
        switchMapItemLamps = findViewById(R.id.switch_map_item_lamps)
        switchMapItemMetal = findViewById(R.id.switch_map_item_metal)
        mapItemApplyButton = findViewById(R.id.map_item_apply_button)
    }

    private fun setMapItemData() {
        etMapItemTitle.setText(intentData.property_name)
        etMapItemDescription.setText(intentData.property_description)
        etMapItemAdress.setText(intentData.property_adress)
        etMapItemOfficeHours.setText(intentData.property_office_hours)
        etMapItemContacts.setText(intentData.property_contacts)
        etMapItemLatitude.setText(intentData.property_latitude.toString())
        etMapItemLongitude.setText(intentData.property_longitude.toString())

        switchMapItemEcomob.isChecked = intentData.categoryEcomob
        switchMapItemPlastic.isChecked = intentData.categoryPlastic
        switchMapItemGlass.isChecked = intentData.categoryGlass
        switchMapItemPaper.isChecked = intentData.categoryPaper
        switchMapItemBatteries.isChecked = intentData.categoryBatteries
        switchMapItemClothes.isChecked = intentData.categoryClothes
        switchMapItemLamps.isChecked = intentData.categoryLamps
        switchMapItemMetal.isChecked = intentData.categoryMetal
    }
}