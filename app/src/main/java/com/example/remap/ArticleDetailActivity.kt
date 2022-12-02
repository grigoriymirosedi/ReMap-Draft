package com.example.remap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var article_description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_article_detail)

        val descripton = intent.getStringExtra("ARTICLE")
    }
}