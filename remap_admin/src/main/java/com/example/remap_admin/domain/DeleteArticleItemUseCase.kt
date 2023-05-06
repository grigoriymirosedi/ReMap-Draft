package com.example.remap_admin.domain

class DeleteArticleItemUseCase(private val articleItemRepository: ArticleListRepository) {
    fun deleteArticleItem(articleItem: ArticleItem) {
        articleItemRepository.deleteArticleItem(articleItem)
    }
}