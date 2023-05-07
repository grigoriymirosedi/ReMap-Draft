package com.example.remap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remap.models.Article
import com.squareup.picasso.Picasso


class ArticleAdapter(private val articleList: ArrayList<Article>, private val recyclerViewInterface: RecyclerViewInterface) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    var recycleViewInterface : RecyclerViewInterface = recyclerViewInterface

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageResource: ImageView = itemView.findViewById(R.id.image_resource)
        val textTitle: TextView = itemView.findViewById(R.id.title_text)
    }

    //Создаёт новый объект ArticleViewHolder всякий раз, когда RecycleView нуждается в этом
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(itemView)
    }

    //Принимает объект ArticleViewHolder и устанавливает необходимые данные для соответствующих строк
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = articleList[position]

        Picasso.get().load(currentItem.articleIconImageURL).resize(250,0).into(holder.imageResource)
        holder.textTitle.text = currentItem.articleTitle

        holder.itemView.setOnClickListener{
            recycleViewInterface.onItemClick(position)
        }
    }

    //Возвращает общее кол-во элементов списка
    override fun getItemCount(): Int {
        return articleList.size
    }
}