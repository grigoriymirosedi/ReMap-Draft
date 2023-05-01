package com.example.remap_admin.presentation.fragments

import android.content.Intent
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
import com.example.remap_admin.presentation.activities.AddEcoMarkerItemActivity
import com.example.remap_admin.presentation.activities.AddMapItemActivity
import com.example.remap_admin.presentation.activities.EditEcoMarkerItemActivity
import com.example.remap_admin.presentation.activities.EditMapItemActivity
import com.example.remap_admin.presentation.adapters.EcoMarkerItemAdapter
import com.example.remap_admin.presentation.adapters.MapItemAdapter
import com.example.remap_admin.presentation.adapters.RecyclerViewInterface
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EcoMarkerFragment : Fragment(), RecyclerViewInterface {

    private lateinit var ecoMarkerListRV: RecyclerView
    private lateinit var ecoMarkerItemAdapter: EcoMarkerItemAdapter
    private lateinit var ecoMarkerFragmentViewModel: EcoMarkerFragmentViewModel
    private lateinit var addEcoMarkerItemBtn: ImageButton
    private var database = FirebaseDatabase.getInstance().getReference()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_eco_marker, container, false)
        ecoMarkerListRV = view.findViewById(R.id.ecoMarkerRV)
        addEcoMarkerItemBtn = view.findViewById(R.id.addEcoMarkerItemButton)
        ecoMarkerListRV.layoutManager = LinearLayoutManager(requireContext())
        ecoMarkerItemAdapter = EcoMarkerItemAdapter(this@EcoMarkerFragment)
        ecoMarkerListRV.adapter = ecoMarkerItemAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ecoMarkerFragmentViewModel = ViewModelProvider(this)[EcoMarkerFragmentViewModel::class.java]
        ecoMarkerFragmentViewModel.ecoMarkerList.observe(viewLifecycleOwner) {
            ecoMarkerItemAdapter.ecoMarkerList = it.toMutableList()
        }

        setUpAddMapItemClickListener()

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                swipeDeleteMapItem(position)
                ecoMarkerItemAdapter.ecoMarkerList.removeAt(position)
                ecoMarkerListRV.adapter?.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(ecoMarkerListRV)

    }

    private fun swipeDeleteMapItem(position: Int) {
        var ecoMarkerItemDeleteQuery = database.child("TestEcoMarker").orderByChild("ecoTitle").equalTo(ecoMarkerItemAdapter.ecoMarkerList[position].ecoTitle)
        ecoMarkerItemDeleteQuery.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ecoMarkerItemDeleteSnapshot in snapshot.children) {
                    ecoMarkerItemDeleteSnapshot.ref.removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setUpAddMapItemClickListener() {
        addEcoMarkerItemBtn.setOnClickListener {
            var addIntent = Intent(requireContext(), AddEcoMarkerItemActivity::class.java)
            startActivity(addIntent)
        }
    }

    override fun onItemClick(position: Int) {
        var editIntent = Intent(requireContext(), EditEcoMarkerItemActivity::class.java)
        editIntent.putExtra("EcoMarker", ecoMarkerFragmentViewModel.ecoMarkerList.value?.get(position))
        startActivity(editIntent)
    }
}