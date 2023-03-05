package com.example.remap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.remap.models.Article
import com.squareup.picasso.Picasso

class ArticleDetails : AppCompatActivity() {

    private lateinit var articleTitle: TextView
    private lateinit var articleIconImageURL: ImageView
    private lateinit var articleContentImageURL: ImageView
    private lateinit var articlePar1: TextView
    private lateinit var articlePar2: TextView
    private lateinit var articlePar3: TextView
    private lateinit var articlePar4: TextView
    private lateinit var articlePar5: TextView
    private lateinit var articlePar6: TextView
    private lateinit var articlePar7: TextView
    private lateinit var articlePar8: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_article_detail)

        val description = intent.getParcelableExtra<Article>("ARTICLE")

        articleTitle = findViewById(R.id.articleTitle)
        articleTitle.text = description?.articleTitle

        articleContentImageURL = findViewById(R.id.articleContentImageURL)
        Picasso.get().load(description!!.articleContentImageURL).into(articleContentImageURL)

        articlePar1 = findViewById(R.id.articlePar1)
        articlePar1.text = description.article_par1

        articlePar2 = findViewById(R.id.articlePar2)
        articlePar2.text = description.article_par2

        articlePar3 = findViewById(R.id.articlePar3)
        articlePar3.text = description.article_par3

        articlePar4 = findViewById(R.id.articlePar4)
        articlePar4.text = description.article_par4

        articlePar5 = findViewById(R.id.articlePar5)
        articlePar5.text = description.article_par5

        articlePar6 = findViewById(R.id.articlePar6)
        articlePar6.text = description.article_par6

        articlePar7 = findViewById(R.id.articlePar7)
        articlePar7.text = description.article_par7

        articlePar8 = findViewById(R.id.articlePar8)
        articlePar8.text = description.article_par8

    }
}