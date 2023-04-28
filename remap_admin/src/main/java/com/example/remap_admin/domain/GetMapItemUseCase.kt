package com.example.remap_admin.domain

class GetMapItemUseCase(private val mapItemRepository: MapListRepository) {

    fun getMapItem(mapItemName: String) {
        mapItemRepository.getMapItem(mapItemName)
    }

}