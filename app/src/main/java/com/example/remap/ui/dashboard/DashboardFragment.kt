package com.example.remap.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remap.*
import com.example.remap.R
import com.example.remap.models.Article
import com.example.remap.databinding.FragmentDashboardBinding
import com.google.firebase.database.*


class DashboardFragment : Fragment(), RecyclerViewInterface{

    private var _binding: FragmentDashboardBinding? = null

    var mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("ArticleDetails")

    private lateinit var adapter: ArticleAdapter
    private lateinit var articleRecyclerView: RecyclerView
    var articleArrayList: ArrayList<Article> = arrayListOf<Article>()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readArticleData()

        articleRecyclerView = view.findViewById(R.id.articleRV)
        articleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        articleRecyclerView.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun readArticleData() {
        mDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                articleArrayList.clear()
                for (ds in dataSnapshot.children){
                    var articles = ds.getValue(Article::class.java)
                    articleArrayList.add(articles!!)
                }
                adapter = ArticleAdapter(articleArrayList, this@DashboardFragment)
                articleRecyclerView.adapter = adapter
            }
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Toast.makeText(requireContext(), dataSnapshot.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }



    override fun onItemClick(position: Int) {
        val intent = Intent(requireContext(), ArticleDetails::class.java)
        intent.putExtra("ARTICLE", articleArrayList[position])
        startActivity(intent)
    }
}