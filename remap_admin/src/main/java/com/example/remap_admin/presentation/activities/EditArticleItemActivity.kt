package com.example.remap_admin.presentation.activities

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.remap_admin.R
import com.example.remap_admin.domain.ArticleItem
import com.example.remap_admin.domain.MapItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditArticleItemActivity : AppCompatActivity() {

    //EdiText for Article item`s fields
    private lateinit var etArticleItemTitle: EditText
    private lateinit var etArticleItemIconImage: EditText
    private lateinit var etArticleItemContentImage: EditText
    private lateinit var etArticleItemPar1: EditText
    private lateinit var etArticleItemPar2: EditText
    private lateinit var etArticleItemPar3: EditText
    private lateinit var etArticleItemPar4: EditText
    private lateinit var etArticleItemPar5: EditText
    private lateinit var etArticleItemPar6: EditText
    private lateinit var etArticleItemPar7: EditText
    private lateinit var etArticleItemPar8: EditText

    private lateinit var intentData: ArticleItem
    private lateinit var applyArticleItemBtn: Button
    private lateinit var articleItemMap: HashMap<String, ArticleItem>

    var database = FirebaseDatabase.getInstance().getReference()
    var editdb = database.child("TestArticle")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_article_item)
        supportActionBar?.hide()

        articleItemMap = HashMap()
        intentData = intent.getParcelableExtra<ArticleItem>("Article")!!
        var articleItemQuery = database.child("TestArticle").orderByChild("articleTitle").equalTo(intentData.articleTitle)

        defineArticleItemView()
        setArticleItemData()

        applyArticleItemBtn.setOnClickListener {
            if (!inputValidate())
                return@setOnClickListener
            articleItemQuery.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(mapItemSnapshot in snapshot.children) {
                        articleItemMap.put(mapItemSnapshot.key.toString(), applySettings())
                    }
                    editdb.updateChildren(articleItemMap as Map<String, Any>)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            Toast.makeText(this, "Изменения внесены", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun applySettings(): ArticleItem {
        return ArticleItem(
            articleTitle = etArticleItemTitle.text.toString(),
            articleIconImageURL = etArticleItemIconImage.text.toString(),
            articleContentImageURL = etArticleItemContentImage.text.toString(),
            article_par1 = etArticleItemPar1.text.toString(),
            article_par2 = etArticleItemPar2.text.toString(),
            article_par3 = etArticleItemPar3.text.toString(),
            article_par4 = etArticleItemPar4.text.toString(),
            article_par5 = etArticleItemPar5.text.toString(),
            article_par6 = etArticleItemPar6.text.toString(),
            article_par7 = etArticleItemPar7.text.toString(),
            article_par8 = etArticleItemPar8.text.toString()
        )
    }

    private fun defineArticleItemView() {
        etArticleItemTitle = findViewById(R.id.et_article_item_title)
        etArticleItemIconImage = findViewById(R.id.et_article_icon_image)
        etArticleItemContentImage = findViewById(R.id.et_article_content_image)
        etArticleItemPar1 = findViewById(R.id.et_article_item_par1)
        etArticleItemPar2 = findViewById(R.id.et_article_item_par2)
        etArticleItemPar3 = findViewById(R.id.et_article_item_par3)
        etArticleItemPar4 = findViewById(R.id.et_article_item_par4)
        etArticleItemPar5 = findViewById(R.id.et_article_item_par5)
        etArticleItemPar6 = findViewById(R.id.et_article_item_par6)
        etArticleItemPar7 = findViewById(R.id.et_article_item_par7)
        etArticleItemPar8 = findViewById(R.id.et_article_item_par8)
        applyArticleItemBtn = findViewById(R.id.article_item_apply_button)
    }

    private fun setArticleItemData() {
        etArticleItemTitle.setText(intentData.articleTitle)
        etArticleItemIconImage.setText(intentData.articleIconImageURL)
        etArticleItemContentImage.setText(intentData.articleContentImageURL)
        etArticleItemPar1.setText(intentData.article_par1)
        etArticleItemPar2.setText(intentData.article_par2)
        etArticleItemPar3.setText(intentData.article_par3)
        etArticleItemPar4.setText(intentData.article_par4)
        etArticleItemPar5.setText(intentData.article_par5)
        etArticleItemPar6.setText(intentData.article_par6)
        etArticleItemPar7.setText(intentData.article_par7)
        etArticleItemPar8.setText(intentData.article_par8)
    }

    private fun inputValidate(): Boolean {
        var isValid = true
        if(TextUtils.isEmpty(etArticleItemTitle.text.toString())) {
            etArticleItemTitle.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etArticleItemIconImage.toString())) {
            etArticleItemIconImage.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etArticleItemContentImage.toString())) {
            etArticleItemContentImage.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etArticleItemPar1.toString())) {
            etArticleItemPar1.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etArticleItemPar2.toString())) {
            etArticleItemPar2.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etArticleItemPar3.toString())) {
            etArticleItemPar3.setError("Пустая строка")
            isValid = false
        }
        if(TextUtils.isEmpty(etArticleItemPar4.toString())) {
            etArticleItemPar4.setError("Пустая строка")
            isValid = false
        }

        if(TextUtils.isEmpty(etArticleItemPar5.toString())) {
            etArticleItemPar5.setError("Пустая строка")
            isValid = false
        }

        if(TextUtils.isEmpty(etArticleItemPar6.toString())) {
            etArticleItemPar6.setError("Пустая строка")
            isValid = false
        }

        if(TextUtils.isEmpty(etArticleItemPar7.toString())) {
            etArticleItemPar7.setError("Пустая строка")
            isValid = false
        }

        if(TextUtils.isEmpty(etArticleItemPar8.toString())) {
            etArticleItemPar8.setError("Пустая строка")
            isValid = false
        }
        return isValid
    }
}