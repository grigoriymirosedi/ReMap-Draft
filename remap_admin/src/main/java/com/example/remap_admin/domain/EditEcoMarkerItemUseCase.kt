package com.example.remap_admin.domain

import com.example.remap_admin.EcoMarkerItem

class EditEcoMarkerItemUseCase(private val ecoMarkerListRepository: EcoMarkerListRepository) {
    fun editEcoMarkerItem(ecoMarkerItem: EcoMarkerItem) {
        ecoMarkerListRepository.editEcoMarkerItem(ecoMarkerItem)
    }
}