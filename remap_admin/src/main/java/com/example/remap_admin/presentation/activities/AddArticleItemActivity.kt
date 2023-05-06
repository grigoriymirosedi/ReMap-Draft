package com.example.remap_admin.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.remap_admin.R
import com.example.remap_admin.domain.ArticleItem
import com.example.remap_admin.domain.MapItem
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class AddArticleItemActivity : AppCompatActivity() {

    //EdiText for Article item`s fields
    private lateinit var etAddArticleItemTitle: TextInputEditText
    private lateinit var etAddArticleItemIconImage: TextInputEditText
    private lateinit var etAddArticleItemContentImage: TextInputEditText
    private lateinit var etAddArticleItemPar1: TextInputEditText
    private lateinit var etAddArticleItemPar2: TextInputEditText
    private lateinit var etAddArticleItemPar3: TextInputEditText
    private lateinit var etAddArticleItemPar4: TextInputEditText
    private lateinit var etAddArticleItemPar5: TextInputEditText
    private lateinit var etAddArticleItemPar6: TextInputEditText
    private lateinit var etAddArticleItemPar7: TextInputEditText
    private lateinit var etAddArticleItemPar8: TextInputEditText
    private lateinit var articleItemAddBtn: Button

    var database = FirebaseDatabase.getInstance().getReference()
    var addArticleItemDB = database.child("ArticleDetails")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_article_item)
        supportActionBar?.hide()

        defineArticleItemView()

        articleItemAddBtn.setOnClickListener {
            if(!inputValidate())
                return@setOnClickListener
            addArticleItemDB.push().setValue(createArticleItem())
            Toast.makeText(this, "Успешно!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun defineArticleItemView() {
        etAddArticleItemTitle = findViewById(R.id.et_add_article_item_title)
        etAddArticleItemIconImage = findViewById(R.id.et_add_article_icon_image)
        etAddArticleItemContentImage = findViewById(R.id.et_add_article_content_image)
        etAddArticleItemPar1 = findViewById(R.id.et_add_article_item_par1)
        etAddArticleItemPar2 = findViewById(R.id.et_add_article_item_par2)
        etAddArticleItemPar3 = findViewById(R.id.et_add_article_item_par3)
        etAddArticleItemPar4 = findViewById(R.id.et_add_article_item_par4)
        etAddArticleItemPar5 = findViewById(R.id.et_add_article_item_par5)
        etAddArticleItemPar6 = findViewById(R.id.et_add_article_item_par6)
        etAddArticleItemPar7 = findViewById(R.id.et_add_article_item_par7)
        etAddArticleItemPar8 = findViewById(R.id.et_add_article_item_par8)
        articleItemAddBtn = findViewById(R.id.article_item_add_button)
    }

    private fun createArticleItem(): ArticleItem {
        return ArticleItem(
            articleTitle = etAddArticleItemTitle.text.toString(),
            articleIconImageURL = etAddArticleItemIconImage.text.toString(),
            articleContentImageURL = etAddArticleItemContentImage.text.toString(),
            article_par1 = etAddArticleItemPar1.text.toString(),
            article_par2 = etAddArticleItemPar2.text.toString(),
            article_par3 = etAddArticleItemPar3.text.toString(),
            article_par4 = etAddArticleItemPar4.text.toString(),
            article_par5 = etAddArticleItemPar5.text.toString(),
            article_par6 = etAddArticleItemPar6.text.toString(),
            article_par7 = etAddArticleItemPar7.text.toString(),
            article_par8 = etAddArticleItemPar8.text.toString()
        )
    }

    private fun inputValidate(): Boolean {
        var isValid = true
        if(TextUtils.isEmpty(etAddArticleItemTitle.text.toString())) {
            etAddArticleItemTitle.setError("Заголовок не может быть пустой")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddArticleItemPar1.text.toString())) {
            etAddArticleItemPar1.setError("Заголовок должен содержать как минимум 2 строки")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddArticleItemPar2.text.toString())) {
            etAddArticleItemPar2.setError("Заголовок должен содержать как минимум 2 строки")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddArticleItemIconImage.text.toString())) {
            etAddArticleItemIconImage.setError("Ссылка на картинку не может быть пустой")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddArticleItemContentImage.text.toString())) {
            etAddArticleItemContentImage.setError("Ссылка на картинку не может быть пустой")
            isValid = false
        }
        return isValid
    }
}