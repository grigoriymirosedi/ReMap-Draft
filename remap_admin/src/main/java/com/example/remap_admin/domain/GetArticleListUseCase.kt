package com.example.remap_admin.domain

import androidx.lifecycle.LiveData

class GetArticleListUseCase(private val articleListRepository: ArticleListRepository) {
    fun getArticleList(): LiveData<List<ArticleItem>> {
        return articleListRepository.getArticleList()
    }
}