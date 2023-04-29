package com.example.remap_admin.data

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remap_admin.domain.MapItem
import com.example.remap_admin.domain.MapListRepository
import com.google.firebase.database.*

object MapListRepositoryImpl: MapListRepository {

    private val mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Test")

    private val mapListLD = MutableLiveData<List<MapItem>>()
    private val mapList = ArrayList<MapItem>()

    init {
        getData()
    }

    override fun addMapItem(mapItem: MapItem) {
        mapList.add(mapItem)
        UpdateList()
    }

    override fun editMapItem(mapItem: MapItem) {
        val oldElement = getMapItem(mapItem.property_name)
        mapList.remove(oldElement)
        addMapItem(mapItem)
    }

    override fun deleteMapItem(mapItem: MapItem) {
        mapList.remove(mapItem)
        UpdateList()
    }

    override fun getMapItem(mapItemName: String): MapItem {
        return mapList.find { it.property_name == mapItemName } ?: throw RuntimeException("Element with name $mapItemName not found")
    }

    override fun getMapList(): LiveData<List<MapItem>> {
        return mapListLD
    }

    private fun UpdateList() {
        mapListLD.value = mapList.toList()
    }

    private fun getData() {
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