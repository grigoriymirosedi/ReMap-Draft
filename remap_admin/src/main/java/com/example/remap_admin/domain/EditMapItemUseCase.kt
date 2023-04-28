package com.example.remap_admin.domain

class EditMapItemUseCase(private val mapItemRepository: MapListRepository) {

    fun editMapItem(mapItem: MapItem) {
        mapItemRepository.editMapItem(mapItem)
    }
}