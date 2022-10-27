package com.example.remap.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.remap.databinding.FragmentHomeBinding
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.remap.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment() {
    private lateinit var mMap: GoogleMap

    val rostov = LatLng(47.233, 39.700)

    val second_marker = LatLng(47.234, 39.700)

    val TAG = "DEBUGVERSION"

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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter())

        mMap.addMarker(
            MarkerOptions()
                .position(second_marker)
                .title("Second Marker")
                .snippet("This is the second marker")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40)))

        mMap.addMarker(
            MarkerOptions()
                .position(rostov)
                .title("Rostov")
                .snippet("Hello World")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40)))

        mMap.setOnMarkerClickListener(this)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rostov, 16f))

        mMap.uiSettings.setMapToolbarEnabled(false)
    }

    /* Changes the color of selected marker */
    fun onMarkerClick(marker: Marker): Boolean {
        if (lastTouchedMarker == null){
            lastTouchedMarker = marker
        }
        else{
            lastTouchedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            lastTouchedMarker = marker
        }

        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dark_green_pin_40))

        return false
    }


}
private fun GoogleMap.setOnMarkerClickListener(homeFragment: HomeFragment) {
    TODO("Not yet implemented")
}

