package com.example.remap_admin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remap_admin.domain.ArticleItem
import com.example.remap_admin.domain.ArticleListRepository
import com.google.firebase.database.*

object ArticleListRepositoryImpl: ArticleListRepository {

    private val mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("ArticleDetails")

    private val articleListLD = MutableLiveData<List<ArticleItem>>()
    private val articleList = ArrayList<ArticleItem>()

    init {
        getArticleItemData()
    }

    override fun addArticleItem(articleItem: ArticleItem) {
        articleList.add(articleItem)
        UpdateList()
    }

    override fun editArticleItem(articleItem: ArticleItem) {
        val oldElement = getArticleItem(articleItem.articleTitle)
        articleList.remove(oldElement)
        addArticleItem(articleItem)
    }

    override fun deleteArticleItem(articleItem: ArticleItem) {
        articleList.remove(articleItem)
        UpdateList()
    }

    override fun getArticleItem(articleItem: String): ArticleItem {
        return articleList.find { it.articleTitle == articleItem } ?: throw RuntimeException("Item with title $articleItem not found")
    }

    override fun getArticleList(): LiveData<List<ArticleItem>> {
        return articleListLD
    }

    private fun UpdateList() {
        articleListLD.value = articleList.toList()
    }

    private fun getArticleItemData() {
        mDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                articleList.clear()
                for (ds in dataSnapshot.children){
                    var articleItem = ds.getValue(ArticleItem::class.java)
                    articleList.add(articleItem!!)
                    UpdateList()
                }
            }
            override fun onCancelled(dataSnapshot: DatabaseError) {
                //TODO
            }
        })
    }
}