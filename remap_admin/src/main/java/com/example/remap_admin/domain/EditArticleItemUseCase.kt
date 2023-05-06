package com.example.remap_admin.domain

class EditArticleItemUseCase(private val articleItemRepository: ArticleListRepository) {
    fun editArticleItem(articleItem: ArticleItem) {
        articleItemRepository.editArticleItem(articleItem)
    }
}