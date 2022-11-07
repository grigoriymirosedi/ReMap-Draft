package com.example.remap.ui.home

import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.remap.databinding.FragmentHomeBinding
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.remap.R
import com.example.remap.models.Properties
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap

    var INITIALIZE_POSITION = LatLng(47.23,39.72)
    //Ссылка на нашу базу данных: https://console.firebase.google.com/project/remap-1faaf/database/remap-1faaf-default-rtdb/data
    //для того, чтобы получить доступ, отправьте в лс мне свой email и я вам открою доступ, по другому никак :(
    var firebaseDatabase = FirebaseDatabase.getInstance().getReference("Properties")
    var PropertyList = arrayListOf<Properties>()



    private var lastTouchedMarker: Marker? = null;

    internal inner class CustomInfoWindowAdapter: GoogleMap.InfoWindowAdapter {

        private val window: View = layoutInflater.inflate(R.layout.custom_info_window, null)
        private val contents: View = layoutInflater.inflate(R.layout.custom_info_window, null)


        override fun getInfoContents(marker: Marker): View? {
            render(marker, window)
            return window
        }

        override fun getInfoWindow(marker: Marker): View? {
            render(marker, window)
            return window
        }

        private fun render(marker: Marker, view: View){
            var title = marker.title
            var tvTitle = view.findViewById<TextView>(R.id.titleText)

            if (!title.equals("")){
                tvTitle?.setText(title)
            }

            var snippet = marker.snippet
            var tvSnippet = view.findViewById<TextView>(R.id.snippet)

            if (!snippet.equals("")){
                tvSnippet?.setText(snippet)
            }
        }
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        //Initializing map...
        mapFragment.getMapAsync(this)

        readData()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Initialize the map
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter())
        mMap.setOnMarkerClickListener(this)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(INITIALIZE_POSITION, 14.5f))

        mMap.uiSettings.setMapToolbarEnabled(false)
    }

    /* Changes the color of selected marker */
    override fun onMarkerClick(marker: Marker): Boolean {
        if (lastTouchedMarker == null){
            lastTouchedMarker = marker
        }
        else{
            lastTouchedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            lastTouchedMarker = marker
        }

        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dark_green_pin_40))

        var container_height = 700;

        var projection = mMap.getProjection();

        var markerScreenPosition = projection.toScreenLocation(marker.getPosition());
        var pointHalfScreenAbove = Point(markerScreenPosition.x,(markerScreenPosition.y - (container_height / 2)).toInt());

        var aboveMarkerLatLng = projection.fromScreenLocation(pointHalfScreenAbove);

        marker.showInfoWindow();
        var center = CameraUpdateFactory.newLatLng(aboveMarkerLatLng);
        mMap.moveCamera(center);


        return true
    }

    fun readData(){
        firebaseDatabase.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                PropertyList.clear()
                Log.d("DATA", "Listerner has invoked")
                for (ds in dataSnapshot.children){
                    var Property = ds.getValue(Properties::class.java)
                    PropertyList.add(Property!!)
                }
                for(property in PropertyList){
                    mMap.addMarker(MarkerOptions()
                        .title(property.property_name)
                        .snippet(property.property_description +
                                "\n\nГрафик работы: " + property.property_office_hours +
                                "\nКонтакты: " + property.property_contacts +
                                "\nАдрес: " + property.property_adress)
                        .position(LatLng(property.property_latitude, property.property_longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40)))
                }
            }
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
        })
    }



}

