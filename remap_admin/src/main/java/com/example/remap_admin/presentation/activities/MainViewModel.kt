package com.example.remap_admin.presentation.activities

import androidx.lifecycle.ViewModel
import com.example.remap_admin.data.MapListRepositoryImpl
import com.example.remap_admin.domain.GetMapItemUseCase
import com.example.remap_admin.domain.GetMapListUseCase

class MainViewModel: ViewModel() {

    private val repository = MapListRepositoryImpl

    private val getMapListUseCase = GetMapListUseCase(repository)

    val mapList = getMapListUseCase.getMapList()

}