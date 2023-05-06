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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remap_admin.R
import com.example.remap_admin.presentation.SwipeToDeleteCallback
import com.example.remap_admin.presentation.activities.AddArticleItemActivity
import com.example.remap_admin.presentation.activities.AddMapItemActivity
import com.example.remap_admin.presentation.activities.EditArticleItemActivity
import com.example.remap_admin.presentation.adapters.ArticleItemAdapter
import com.example.remap_admin.presentation.adapters.RecyclerViewInterface
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


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

        setUpAddArticleItemClickListener()

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                swipeDeleteArticleItem(position)
                articleItemAdapter.articleList.removeAt(position)
                articleListRV.adapter?.notifyItemRemoved(position)
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(articleListRV)

    }

    private fun swipeDeleteArticleItem(position: Int) {
        var articleItemDeleteQuery = database.child("ArticleDetails").orderByChild("articleTitle").equalTo(articleItemAdapter.articleList[position].articleTitle)
        articleItemDeleteQuery.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(articleItemDeleteSnapshot in snapshot.children) {
                    articleItemDeleteSnapshot.ref.removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onItemClick(position: Int) {
        var editIntent = Intent(requireContext(), EditArticleItemActivity::class.java)
        editIntent.putExtra("Article", articleFragmentViewModel.articleList.value?.get(position))
        startActivity(editIntent)
    }

    private fun setUpAddArticleItemClickListener() {
        addArticleItemBtn.setOnClickListener {
            var addIntent = Intent(requireContext(), AddArticleItemActivity::class.java)
            startActivity(addIntent)
        }
    }

}