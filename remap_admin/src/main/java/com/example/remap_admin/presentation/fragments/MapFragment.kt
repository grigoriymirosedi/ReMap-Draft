package com.example.remap_admin.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.remap_admin.domain.MapItem
import com.example.remap_admin.presentation.SwipeToDeleteCallback
import com.example.remap_admin.presentation.activities.AddMapItemActivity
import com.example.remap_admin.presentation.activities.EditMapItemActivity
import com.example.remap_admin.presentation.adapters.MapItemAdapter
import com.example.remap_admin.presentation.adapters.RecyclerViewInterface
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MapFragment : Fragment(), RecyclerViewInterface {

    private lateinit var mapListRV: RecyclerView
    private lateinit var mapItemAdapter: MapItemAdapter
    private lateinit var mapFragmentViewModel: MapFragmentViewModel
    private lateinit var addMapItemBtn: ImageButton
    private var database = FirebaseDatabase.getInstance().getReference()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapListRV = view.findViewById(R.id.mapRV)
        addMapItemBtn = view.findViewById(R.id.addMapItemButton)
        mapListRV.layoutManager = LinearLayoutManager(requireContext())
        mapItemAdapter = MapItemAdapter(this@MapFragment)
        mapListRV.adapter = mapItemAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragmentViewModel = ViewModelProvider(this)[MapFragmentViewModel::class.java]
        mapFragmentViewModel.mapList.observe(viewLifecycleOwner) {
            mapItemAdapter.mapList = it.toMutableList()
        }

        setUpAddMapItemClickListener()

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                deleteMapItem(position)
                mapItemAdapter.mapList.removeAt(position)
                mapListRV.adapter?.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(mapListRV)
    }

    private fun deleteMapItem(position: Int) {
        var mapItemDeleteQuery = database.child("Test").orderByChild("property_adress").equalTo(mapItemAdapter.mapList[position].property_adress)
        mapItemDeleteQuery.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(mapItemDeleteSnapshot in snapshot.children) {
                    mapItemDeleteSnapshot.ref.removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setUpAddMapItemClickListener() {
        addMapItemBtn.setOnClickListener {
            var addIntent = Intent(requireContext(), AddMapItemActivity::class.java)
            startActivity(addIntent)
        }
    }

    override fun onItemClick(position: Int) {
        var editIntent = Intent(requireContext(), EditMapItemActivity::class.java)
        editIntent.putExtra("Property", mapFragmentViewModel.mapList.value?.get(position))
        startActivity(editIntent)
    }

}