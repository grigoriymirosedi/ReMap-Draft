package com.example.remap_admin.domain

class AddArticleItemUseCase(private val articleItemRepository: ArticleListRepository) {
    fun addArticleItem(articleItem: ArticleItem) {
        articleItemRepository.addArticleItem(articleItem)
    }
}