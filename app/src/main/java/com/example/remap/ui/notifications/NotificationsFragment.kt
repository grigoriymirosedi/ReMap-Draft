package com.example.remap.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remap.*
import com.example.remap.R
import com.example.remap.databinding.FragmentNotificationsBinding
import com.example.remap.models.EcoMarkers
import com.google.firebase.database.*

class NotificationsFragment : Fragment(), RecyclerViewInterface {

    private var _binding: FragmentNotificationsBinding? = null

    var mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("EcoMarkersDetails")

    private lateinit var adapter: EcoMarkersAdapter
    private lateinit var ecoRecyclerView: RecyclerView
    var ecoMarkersArrayList: ArrayList<EcoMarkers> = arrayListOf<EcoMarkers>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readEcoMarkersData()

        val layoutManager = LinearLayoutManager(context)
        ecoRecyclerView = view.findViewById(R.id.ecoMarkersRV)
        ecoRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        ecoRecyclerView.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun readEcoMarkersData() {
        mDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                ecoMarkersArrayList.clear()
                for (ds in dataSnapshot.children){
                    var ecoMarkers = ds.getValue(EcoMarkers::class.java)
                    ecoMarkersArrayList.add(ecoMarkers!!)
                }
                adapter = EcoMarkersAdapter(ecoMarkersArrayList, this@NotificationsFragment)
                ecoRecyclerView.adapter = adapter
            }
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Toast.makeText(requireContext(), dataSnapshot.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }




    override fun onItemClick(position: Int) {
        val intent = Intent(requireContext(), EcoMarkersDetails::class.java)
        intent.putExtra("MARKER", ecoMarkersArrayList[position])
        startActivity(intent)
    }
}