package com.example.remap

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.remap.R
import com.example.remap.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityMainBinding

//    private lateinit var mMap: GoogleMap

//    private lateinit var PlasticCard: CardView
//    private lateinit var PlasticImage: ImageView
//
//    private lateinit var GlassCard: CardView
//    private lateinit var GlassImage: ImageView
//
//    private lateinit var MetalCard: CardView
//    private lateinit var MetalImage: ImageView
//
//    private lateinit var LightCard: CardView
//    private lateinit var LightImage: ImageView
//
//    private lateinit var BatteryCard: CardView
//    private lateinit var BatteryImage: ImageView
//
//    private lateinit var ClothesCard: CardView
//    private lateinit var ClothesImage: ImageView
//
//    var plasticFlag = false;
//    var glassFlag = false;
//    var metalFlag = false;
//    var lightFlag = false;
//    var batteryFlag = false;
//    var clothesFlag = false;
//
//    val rostov = LatLng(47.233, 39.700)
//
//    val second_marker = LatLng(47.234, 39.700)
//
//    val TAG = "DEBUGVERSION"
//
//    private var lastTouchedMarker: Marker? = null;
//
//    internal inner class CustomInfoWindowAdapter: InfoWindowAdapter {
//
//        private val window: View = layoutInflater.inflate(R.layout.custom_info_window, null)
//        private val contents: View = layoutInflater.inflate(R.layout.custom_info_window, null)
//
//
//        override fun getInfoContents(marker: Marker): View? {
//            render(marker, window)
//            return window
//        }
//
//        override fun getInfoWindow(marker: Marker): View? {
//            render(marker, window)
//            return window
//        }
//
//        private fun render(marker: Marker, view: View){
//            var title = marker.title
//            var tvTitle = view.findViewById<TextView>(R.id.titleText)
//
//            if (!title.equals("")){
//                tvTitle?.setText(title)
//            }
//
//            var snippet = marker.snippet
//            var tvSnippet = view.findViewById<TextView>(R.id.snippet)
//
//            if (!snippet.equals("")){
//                tvSnippet?.setText(snippet)
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        // Здесь происходит ошибка
        com.example.remap.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(com.example.remap.binding.root)

        val navView: BottomNavigationView = com.example.remap.binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    // Заменить
    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
    // Заменить
    override fun onMarkerClick(p0: Marker): Boolean {
        TODO("Not yet implemented")
    }
//        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//        PlasticCard = findViewById(R.id.PlasticCard)
//        PlasticImage = findViewById(R.id.plasticImage)
//
//        GlassCard = findViewById(R.id.GlassCard)
//        GlassImage = findViewById(R.id.glassImage)
//
//        MetalCard = findViewById(R.id.MetalCard)
//        MetalImage = findViewById(R.id.metalImage)
//
//        LightCard = findViewById(R.id.LampCard)
//        LightImage = findViewById(R.id.lightImage)
//
//        BatteryCard = findViewById(R.id.BatteryCard)
//        BatteryImage = findViewById(R.id.batteryImage)
//
//        ClothesCard = findViewById(R.id.ClothesCard)
//        ClothesImage = findViewById(R.id.clothesImage)
//
//        PlasticCard.setOnClickListener{
//            if (plasticFlag == false){
//                Log.d(TAG, "Enable")
//                PlasticImage.setImageResource(R.drawable.is_enable_plastic_50)
//                plasticFlag = !plasticFlag
//            }
//            else{
//                Log.d(TAG, "Disable")
//                PlasticImage.setImageResource(R.drawable.is_disable_plastic_50)
//                plasticFlag = !plasticFlag
//            }
//        }
//
//        GlassCard.setOnClickListener{
//            if (glassFlag == false){
//                Log.d(TAG, "Enable")
//                GlassImage.setImageResource(R.drawable.is_enable_glass_50)
//                glassFlag = !glassFlag
//            }
//            else{
//                Log.d(TAG, "Disable")
//                GlassImage.setImageResource(R.drawable.is_disable_glass_50)
//                glassFlag = !glassFlag
//            }
//        }
//
//        MetalCard.setOnClickListener{
//            if (metalFlag == false){
//                Log.d(TAG, "Enable")
//                MetalImage.setImageResource(R.drawable.is_enable_metal_50)
//                metalFlag = !metalFlag
//            }
//            else{
//                Log.d(TAG, "Disable")
//                MetalImage.setImageResource(R.drawable.is_disable_metal_50)
//                metalFlag = !metalFlag
//            }
//        }
//
//        LightCard.setOnClickListener{
//            if (lightFlag == false){
//                Log.d(TAG, "Enable")
//                LightImage.setImageResource(R.drawable.is_enable_light_50)
//                lightFlag = !lightFlag
//            }
//            else{
//                Log.d(TAG, "Disable")
//                LightImage.setImageResource(R.drawable.is_disable_light_50)
//                lightFlag = !lightFlag
//            }
//        }
//
//        BatteryCard.setOnClickListener{
//            if (batteryFlag == false){
//                Log.d(TAG, "Enable")
//                BatteryImage.setImageResource(R.drawable.is_enable_battery_50)
//                batteryFlag = !batteryFlag
//            }
//            else{
//                Log.d(TAG, "Disable")
//                BatteryImage.setImageResource(R.drawable.is_disable_battery_50)
//                batteryFlag = !batteryFlag
//            }
//        }
//
//        ClothesCard.setOnClickListener{
//            if (clothesFlag == false){
//                Log.d(TAG, "Enable")
//                ClothesImage.setImageResource(R.drawable.is_enable_clothes_50)
//                clothesFlag = !clothesFlag
//            }
//            else{
//                Log.d(TAG, "Disable")
//                ClothesImage.setImageResource(R.drawable.is_disable_clothes_50)
//                clothesFlag = !clothesFlag
//            }
//        }
//
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter())
//
//        mMap.addMarker(
//            MarkerOptions()
//                .position(second_marker)
//                .title("Second Marker")
//                .snippet("This is the second marker")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40)))
//
//        mMap.addMarker(
//            MarkerOptions()
//                .position(rostov)
//                .title("Rostov")
//                .snippet("Hello World")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40)))
//
//        mMap.setOnMarkerClickListener(this)
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rostov, 16f))
//
//        mMap.uiSettings.setMapToolbarEnabled(false)
//    }
//
//    /* Changes the color of selected marker */
//    override fun onMarkerClick(marker: Marker): Boolean {
//        if (lastTouchedMarker == null){
//            lastTouchedMarker = marker
//        }
//        else{
//            lastTouchedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
//            lastTouchedMarker = marker
//        }
//
//        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dark_green_pin_40))
//
//        return false
//    }

}