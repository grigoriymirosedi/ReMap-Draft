package com.example.remap_admin.presentation.fragments

import android.content.Intent
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remap_admin.R
import com.example.remap_admin.presentation.activities.EditArticleItemActivity
import com.example.remap_admin.presentation.adapters.ArticleItemAdapter
import com.example.remap_admin.presentation.adapters.RecyclerViewInterface
import com.google.firebase.database.FirebaseDatabase


class ArticleFragment : Fragment(), RecyclerViewInterface {

    private lateinit var articleListRV: RecyclerView
    private lateinit var articleItemAdapter: ArticleItemAdapter
    private lateinit var articleFragmentViewModel: ArticleFragmentViewModel
    private lateinit var addArticleItemBtn: ImageButton
    private var database = FirebaseDatabase.getInstance().getReference()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        articleListRV = view.findViewById(R.id.articleRV)
        addArticleItemBtn = view.findViewById(R.id.addArticleItemButton)
        articleListRV.layoutManager = LinearLayoutManager(requireContext())
        articleItemAdapter = ArticleItemAdapter(this@ArticleFragment)
        articleListRV.adapter = articleItemAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleFragmentViewModel = ViewModelProvider(this)[ArticleFragmentViewModel::class.java]
        articleFragmentViewModel.articleList.observe(viewLifecycleOwner) {
            articleItemAdapter.articleList = it.toMutableList()
        }
    }

    override fun onItemClick(position: Int) {
        var editIntent = Intent(requireContext(), EditArticleItemActivity::class.java)
        editIntent.putExtra("Article", articleFragmentViewModel.articleList.value?.get(position))
        startActivity(editIntent)
    }

}