package com.example.remap_admin.presentation.fragments

import android.content.Intent
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
import com.example.remap_admin.presentation.activities.EditMapItemActivity
import com.example.remap_admin.presentation.adapters.MapItemAdapter
import com.example.remap_admin.presentation.adapters.RecyclerViewInterface


class MapFragment : Fragment(), RecyclerViewInterface {

    private lateinit var mapListRV: RecyclerView
    private lateinit var mapItemAdapter: MapItemAdapter
    private lateinit var mapFragmentViewModel: MapFragmentViewModel
    private lateinit var addMapItemBtn: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapListRV = view.findViewById(R.id.mapRV)
        mapListRV.layoutManager = LinearLayoutManager(requireContext())
        mapItemAdapter = MapItemAdapter(this@MapFragment)
        mapListRV.adapter = mapItemAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragmentViewModel = ViewModelProvider(this)[MapFragmentViewModel::class.java]
        mapFragmentViewModel.mapList.observe(viewLifecycleOwner) {
            mapItemAdapter.mapList = it
        }
        setUpClickListener()
        addMapItemBtn.setOnClickListener {
        }
    }

    private fun setUpClickListener() {
        mapItemAdapter.onMapItemClickListener = {
        }
    }

    override fun onItemClick(position: Int) {
        var editIntent = Intent(requireContext(), EditMapItemActivity::class.java)
        editIntent.putExtra("Property", mapFragmentViewModel.mapList.value?.get(position))
        startActivity(editIntent)
    }

}