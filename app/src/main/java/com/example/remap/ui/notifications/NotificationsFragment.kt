package com.example.remap.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
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
import java.util.*
import kotlin.collections.ArrayList

class NotificationsFragment : Fragment(), RecyclerViewInterface {

    private var _binding: FragmentNotificationsBinding? = null

    var mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("EcoMarkersDetails")

    private lateinit var searchView: SearchView

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

        searchView = view.findViewById(R.id.searchView)

        val layoutManager = LinearLayoutManager(context)
        ecoRecyclerView = view.findViewById(R.id.ecoMarkersRV)
        ecoRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        ecoRecyclerView.setHasFixedSize(true)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(message: String): Boolean {
                searchFilter(message)
                return true
            }

        })

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

    fun searchFilter(message: String) {
        val filteredlist: ArrayList<EcoMarkers> = ArrayList()

        // running a for loop to compare elements.
        for (item in ecoMarkersArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.ecoTitle.lowercase(Locale.getDefault()).contains(message.lowercase(Locale.getDefault()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.

        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(requireContext(), EcoMarkersDetails::class.java)
        intent.putExtra("MARKER", ecoMarkersArrayList[position])
        startActivity(intent)
    }
}