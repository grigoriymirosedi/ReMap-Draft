package com.example.remap_admin.domain

import androidx.lifecycle.LiveData
import com.example.remap_admin.EcoMarkerItem

class GetEcoMarkerListUseCase(private val ecoMarkerListRepository: EcoMarkerListRepository) {
    fun getEcoMarkerList(): LiveData<List<EcoMarkerItem>> {
        return ecoMarkerListRepository.getEcoMarkerList()
    }
}