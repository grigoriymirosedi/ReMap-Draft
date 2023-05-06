package com.example.remap_admin.presentation.fragments

import androidx.lifecycle.ViewModel
import com.example.remap_admin.data.EcoMarkerRepositoryImpl
import com.example.remap_admin.data.MapListRepositoryImpl
import com.example.remap_admin.domain.GetEcoMarkerListUseCase
import com.example.remap_admin.domain.GetMapListUseCase

class EcoMarkerFragmentViewModel: ViewModel() {
    private val repository = EcoMarkerRepositoryImpl

    private val getEcoMarkerListUseCase = GetEcoMarkerListUseCase(repository)

    val ecoMarkerList = getEcoMarkerListUseCase.getEcoMarkerList()
}