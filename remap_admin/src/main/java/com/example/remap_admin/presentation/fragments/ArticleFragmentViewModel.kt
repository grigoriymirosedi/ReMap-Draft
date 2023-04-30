package com.example.remap_admin.presentation.fragments

import androidx.lifecycle.ViewModel
import com.example.remap_admin.data.ArticleListRepositoryImpl
import com.example.remap_admin.domain.GetArticleListUseCase

class ArticleFragmentViewModel: ViewModel() {
    private val repository = ArticleListRepositoryImpl

    private val getArticleListUseCase = GetArticleListUseCase(repository)

    val articleList = getArticleListUseCase.getArticleList()
}