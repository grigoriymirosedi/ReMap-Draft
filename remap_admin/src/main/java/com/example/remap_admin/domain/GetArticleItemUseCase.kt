package com.example.remap_admin.domain

class GetArticleItemUseCase(private val articleItemRepository: ArticleListRepository) {
    fun getArticleItem(articleItemTitle: String): ArticleItem {
        return articleItemRepository.getArticleItem(articleItemTitle)
    }
}