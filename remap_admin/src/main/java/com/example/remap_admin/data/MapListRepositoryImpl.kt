package com.example.remap_admin.data

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remap_admin.domain.MapItem
import com.example.remap_admin.domain.MapListRepository
import com.google.firebase.database.*

object MapListRepositoryImpl: MapListRepository {

    private val mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Properties")

    private val mapListLD = MutableLiveData<List<MapItem>>()
    private val mapList = ArrayList<MapItem>()

    init {
        getMapItemData()
    }

    override fun addMapItem(mapItem: MapItem) {
        mapList.add(mapItem)
        UpdateList()
    }

    override fun editMapItem(mapItem: MapItem) {
        val oldElement = getMapItem(mapItem.property_adress)
        mapList.remove(oldElement)
        addMapItem(mapItem)
    }

    override fun deleteMapItem(mapItem: MapItem) {
        mapList.remove(mapItem)
        UpdateList()
    }

    override fun getMapItem(mapItemAdress: String): MapItem {
        return mapList.find { it.property_adress == mapItemAdress } ?: throw RuntimeException("Element with adress $mapItemAdress not found")
    }

    override fun getMapList(): LiveData<List<MapItem>> {
        return mapListLD
    }

    private fun UpdateList() {
        mapListLD.value = mapList.toList()
    }

    private fun getMapItemData() {
        mDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                mapList.clear()
                for (ds in dataSnapshot.children){
                    var mapItem = ds.getValue(MapItem::class.java)
                    mapList.add(mapItem!!)
                    UpdateList()
                }
            }
            override fun onCancelled(dataSnapshot: DatabaseError) {
                //TODO
            }
        })
    }
}