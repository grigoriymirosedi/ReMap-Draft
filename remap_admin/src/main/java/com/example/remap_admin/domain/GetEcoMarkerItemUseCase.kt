package com.example.remap_admin.domain

import com.example.remap_admin.EcoMarkerItem

class GetEcoMarkerItemUseCase(private val ecoMarkerListRepository: EcoMarkerListRepository) {
    fun getEcoMarkerItem(ecoMarkerItemTitle: String): EcoMarkerItem {
        return ecoMarkerListRepository.getEcoMarkerItem(ecoMarkerItemTitle)
    }
}