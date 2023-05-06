package com.example.remap_admin.domain

class AddMapItemUseCase(private val mapItemRespository: MapListRepository) {

    fun addMapItem(mapItem: MapItem) {
        mapItemRespository.addMapItem(mapItem)
    }

}