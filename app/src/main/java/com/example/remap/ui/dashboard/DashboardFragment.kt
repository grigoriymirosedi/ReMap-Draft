package com.example.remap.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remap.models.Article
import com.example.remap.ArticleAdapter
import com.example.remap.ArticleDetailActivity
import com.example.remap.R
import com.example.remap.RecyclerViewInterface
import com.example.remap.databinding.FragmentDashboardBinding




class DashboardFragment : Fragment(), RecyclerViewInterface{

    private var _binding: FragmentDashboardBinding? = null

    private lateinit var adapter: ArticleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleArrayList: ArrayList<Article>

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
        InitializeData()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ArticleAdapter(articleArrayList, this)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun InitializeData(){
        articleArrayList = arrayListOf<Article>(
            //https://www.ingos.ru/company/blog/2020/sorting/#part-1
            //https://vk.com/wall-188593703_148
            Article(R.drawable.article_tip_80, "Как подготовить вторсырьё к переработке", "Test description"),
            Article(R.drawable.article_paper_80, "Переработка бумаги", "Test description"),
            Article(R.drawable.article_plastic_80, "Переработка пластика", "Test description"),
            Article(R.drawable.article_metal_80, "Переработка металла", "Test description"),
            Article(R.drawable.article_glass_80, "Переработка стекла", "Test description"),
            Article(R.drawable.article_forbidden_80, "Что не принимается на переработку", "Test description"),
            Article(R.drawable.article_myth_80, "Эко-Мифы", "Test description"),
        )
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(requireContext(), ArticleDetailActivity::class.java)
        intent.putExtra("ARTICLE", articleArrayList[position].description)
        startActivity(intent)
    }
}