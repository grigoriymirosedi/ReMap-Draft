package com.example.remap_admin.presentation.fragments

import androidx.lifecycle.ViewModel
import com.example.remap_admin.data.MapListRepositoryImpl
import com.example.remap_admin.domain.GetMapListUseCase

class MapFragmentViewModel: ViewModel() {

    private val repository = MapListRepositoryImpl

    private val getMapListUseCase = GetMapListUseCase(repository)

    val mapList = getMapListUseCase.getMapList()

}