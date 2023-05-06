package com.example.remap_admin.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remap_admin.R
import com.example.remap_admin.domain.ArticleItem
import com.squareup.picasso.Picasso

class ArticleItemAdapter(private val recyclerViewInterface: RecyclerViewInterface): RecyclerView.Adapter<ArticleItemAdapter.ArticleItemViewHolder>() {

    var articleList = mutableListOf<ArticleItem>()
        set(value) {
            field = value
            notifyDataSetChanged() //В будущем поменять!!!
        }

    var onArticleItemClickListener: ((ArticleItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ArticleItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ArticleItemViewHolder, position: Int) {
        val articleItem = articleList[position]
        viewHolder.articleItemTitle.text = articleItem.articleTitle
        Picasso.get().load(articleItem.articleIconImageURL).resize(0, 250).into(viewHolder.articleIconImage)
        viewHolder.view.setOnClickListener {
            recyclerViewInterface.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    class ArticleItemViewHolder(val view: View): RecyclerView.ViewHolder(view)  {
        val articleItemTitle = view.findViewById<TextView>(R.id.article_title_text)
        var articleIconImage = view.findViewById<ImageView>(R.id.article_image)
    }

}