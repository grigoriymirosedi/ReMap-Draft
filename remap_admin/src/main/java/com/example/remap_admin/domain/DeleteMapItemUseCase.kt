package com.example.remap_admin.domain

class DeleteMapItemUseCase(private val mapItemRepository: MapListRepository) {

    fun deleteMapItem(mapItem: MapItem) {
        mapItemRepository.deleteMapItem(mapItem)
    }

}