package com.example.remap_admin.domain

import androidx.lifecycle.LiveData

class GetMapListUseCase(private val mapItemRepository: MapListRepository) {

    fun getMapList(): LiveData<List<MapItem>> {
        return mapItemRepository.getMapList()
    }
}