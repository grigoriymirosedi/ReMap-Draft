package com.example.remap_admin.domain

import com.example.remap_admin.EcoMarkerItem

class DeleteEcoMarkerItemUseCase(private val ecoMarkerListRepository: EcoMarkerListRepository) {
    fun deleteEcoMarker(ecoMarkerItem: EcoMarkerItem) {
        ecoMarkerListRepository.deleteEcoMarkerItem(ecoMarkerItem)
    }
}