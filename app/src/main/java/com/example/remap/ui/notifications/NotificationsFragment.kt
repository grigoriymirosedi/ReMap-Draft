package com.example.remap.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remap.*
import com.example.remap.databinding.FragmentNotificationsBinding
import com.example.remap.models.EcoMarkers

class NotificationsFragment : Fragment(), RecyclerViewInterface {

    private var _binding: FragmentNotificationsBinding? = null

    private lateinit var adapter: EcoMarkersAdapter
    private lateinit var ecoRecyclerView: RecyclerView
    private lateinit var ecoMarkersArrayList: ArrayList<EcoMarkers>

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
        InitializeMarkers()
        val layoutManager = LinearLayoutManager(context)
        ecoRecyclerView = view.findViewById(R.id.ecoMarkersRV)
        ecoRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        ecoRecyclerView.setHasFixedSize(true)
        adapter = EcoMarkersAdapter(ecoMarkersArrayList, this)
        ecoRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun InitializeMarkers(){
        ecoMarkersArrayList = arrayListOf<EcoMarkers>(
            //https://rsbor.ru/where-to-start/kak-razobratsya-v-markirovkax/
            //https://rsbor-msk.ru/markirovka/
            EcoMarkers(R.drawable.eco_pet, "Пластик с маркировкой 1", "Описание пластика"),
            EcoMarkers(R.drawable.eco_pehd, "Пластик с маркировкой 2", "Описание пластика"),
            EcoMarkers(R.drawable.eco_pvc, "Пластик с маркировкой 3", "Описание пластика"),
            EcoMarkers(R.drawable.eco_peld, "Пластик с маркировкой 4", "Описание пластика"),
            EcoMarkers(R.drawable.eco_pp, "Пластик с маркировкой 5", "Описание пластика"),
            EcoMarkers(R.drawable.eco_ps, "Пластик с маркировкой 6", "Описание пластика"),
            EcoMarkers(R.drawable.eco_other, "Пластик с маркировкой 7", "Описание пластика"),
            EcoMarkers(R.drawable.eco_alu, "Алюминий", "Описание алюминия"),
            EcoMarkers(R.drawable.eco_fe, "Металл", "Описание металла"),
        )
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(requireContext(), EcoMarkersDetails::class.java)
        intent.putExtra("MARKER", ecoMarkersArrayList[position].description)
        startActivity(intent)
    }
}