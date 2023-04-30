package com.example.remap_admin.domain

import com.example.remap_admin.EcoMarkerItem

class AddEcoMarkerItemUseCase(private val ecoMarkerListRepository: EcoMarkerListRepository) {
    fun addEcoMarker(ecoMarkerItem: EcoMarkerItem) {
        ecoMarkerListRepository.addEcoMarkerItem(ecoMarkerItem)
    }
}