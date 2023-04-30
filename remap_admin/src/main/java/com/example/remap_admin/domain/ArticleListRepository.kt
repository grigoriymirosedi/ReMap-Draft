package com.example.remap_admin.domain

import androidx.lifecycle.LiveData

interface ArticleListRepository {

    fun addArticleItem(articleItem: ArticleItem)

    fun editArticleItem(articleItem: ArticleItem)

    fun deleteArticleItem(articleItem: ArticleItem)

    fun getArticleItem(articleItem: String): ArticleItem

    fun getArticleList(): LiveData<List<ArticleItem>>

}