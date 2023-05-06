package com.example.remap_admin.domain

import androidx.lifecycle.LiveData

interface MapListRepository {

    fun addMapItem(mapItem: MapItem)

    fun editMapItem(mapItem: MapItem)

    fun deleteMapItem(mapItem: MapItem)

    fun getMapItem(mapItemName: String): MapItem

    fun getMapList(): LiveData<List<MapItem>>

}