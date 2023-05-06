package com.example.remap_admin.domain

import androidx.lifecycle.LiveData
import com.example.remap_admin.EcoMarkerItem

interface EcoMarkerListRepository {

    fun addEcoMarkerItem(ecoMarkerItem: EcoMarkerItem)

    fun editEcoMarkerItem(ecoMarkerItem: EcoMarkerItem)

    fun deleteEcoMarkerItem(ecoMarkerItem: EcoMarkerItem)

    fun getEcoMarkerItem(ecoMarkerItem: String): EcoMarkerItem

    fun getEcoMarkerList(): LiveData<List<EcoMarkerItem>>

}