package com.example.remap_admin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remap_admin.EcoMarkerItem
import com.example.remap_admin.domain.ArticleItem
import com.example.remap_admin.domain.EcoMarkerListRepository
import com.google.firebase.database.*

object EcoMarkerRepositoryImpl: EcoMarkerListRepository {

    private val mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("TestEcoMarker")

    private val ecoMarkerListLD = MutableLiveData<List<EcoMarkerItem>>()
    private val ecoMarkerList = ArrayList<EcoMarkerItem>()

    init {
        getArticleItemData()
    }

    override fun addEcoMarkerItem(ecoMarkerItem: EcoMarkerItem) {
        ecoMarkerList.add(ecoMarkerItem)
        UpdateList()
    }

    override fun editEcoMarkerItem(ecoMarkerItem: EcoMarkerItem) {
        val oldElement = getEcoMarkerItem(ecoMarkerItem.ecoTitle)
        ecoMarkerList.remove(oldElement)
        addEcoMarkerItem(ecoMarkerItem)
    }

    override fun deleteEcoMarkerItem(ecoMarkerItem: EcoMarkerItem) {
        ecoMarkerList.remove(ecoMarkerItem)
        UpdateList()
    }

    override fun getEcoMarkerItem(ecoMarkerItemTitle: String): EcoMarkerItem {
        return ecoMarkerList.find { it.ecoTitle == ecoMarkerItemTitle } ?: throw RuntimeException("Element with title $ecoMarkerItemTitle not found")
    }

    override fun getEcoMarkerList(): LiveData<List<EcoMarkerItem>> {
        return ecoMarkerListLD
    }

    private fun UpdateList() {
        ecoMarkerListLD.value = ecoMarkerList.toList()
    }

    private fun getArticleItemData() {
        mDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                ecoMarkerList.clear()
                for (ds in dataSnapshot.children){
                    var ecoMarkerItem = ds.getValue(EcoMarkerItem::class.java)
                    ecoMarkerList.add(ecoMarkerItem!!)
                    UpdateList()
                }
            }
            override fun onCancelled(dataSnapshot: DatabaseError) {
                //TODO
            }
        })
    }

}