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
import com.google.firebase.database.FirebaseDatabase

class AddArticleItemActivity : AppCompatActivity() {

    //EdiText for Article item`s fields
    private lateinit var etAddArticleItemTitle: EditText
    private lateinit var etAddArticleItemIconImage: EditText
    private lateinit var etAddArticleItemContentImage: EditText
    private lateinit var etAddArticleItemPar1: EditText
    private lateinit var etAddArticleItemPar2: EditText
    private lateinit var etAddArticleItemPar3: EditText
    private lateinit var etAddArticleItemPar4: EditText
    private lateinit var etAddArticleItemPar5: EditText
    private lateinit var etAddArticleItemPar6: EditText
    private lateinit var etAddArticleItemPar7: EditText
    private lateinit var etAddArticleItemPar8: EditText
    private lateinit var articleItemAddBtn: Button

    var database = FirebaseDatabase.getInstance().getReference()
    var addArticleItemDB = database.child("TestArticle")

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
            etAddArticleItemTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddArticleItemIconImage.toString())) {
            etAddArticleItemIconImage.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddArticleItemContentImage.toString())) {
            etAddArticleItemContentImage.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddArticleItemPar1.toString())) {
            etAddArticleItemPar1.setError("Статья должна содержать хотя бы 2 параграфа")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddArticleItemPar2.toString())) {
            etAddArticleItemPar2.setError("Статья должна содержать хотя бы 2 параграфа")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddArticleItemIconImage.toString())) {
            etAddArticleItemIconImage.setError("Ссылка на картинку не может быть пустой")
            isValid = false
        }
        if(TextUtils.isEmpty(etAddArticleItemContentImage.toString())) {
            etAddArticleItemContentImage.setError("Ссылка на картинку не может быть пустой")
            isValid = false
        }
        return isValid
    }
}