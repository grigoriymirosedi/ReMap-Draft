package com.example.remap.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.remap.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap


    private lateinit var PlasticCard: CardView
    private lateinit var PlasticImage: ImageView

    private lateinit var GlassCard: CardView
    private lateinit var GlassImage: ImageView

    private lateinit var MetalCard: CardView
    private lateinit var MetalImage: ImageView

    private lateinit var LightCard: CardView
    private lateinit var LightImage: ImageView

    private lateinit var BatteryCard: CardView
    private lateinit var BatteryImage: ImageView

    private lateinit var ClothesCard: CardView
    private lateinit var ClothesImage: ImageView

    var plasticFlag = false;
    var glassFlag = false;
    var metalFlag = false;
    var lightFlag = false;
    var batteryFlag = false;
    var clothesFlag = false;

    var PropertyList = arrayListOf<Properties>()

    val TAG = "DEBUGVERSION"

    var firebaseDatabase = FirebaseDatabase.getInstance().getReference("Properties")

    private var lastTouchedMarker: Marker? = null


    internal inner class CustomInfoWindowAdapter: InfoWindowAdapter {

        private val window: View = layoutInflater.inflate(R.layout.custom_info_window, null)


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        readData()


        PlasticCard = findViewById(R.id.PlasticCard)
        PlasticImage = findViewById(R.id.plasticImage)

        GlassCard = findViewById(R.id.GlassCard)
        GlassImage = findViewById(R.id.glassImage)

        MetalCard = findViewById(R.id.MetalCard)
        MetalImage = findViewById(R.id.metalImage)

        LightCard = findViewById(R.id.LampCard)
        LightImage = findViewById(R.id.lightImage)

        BatteryCard = findViewById(R.id.BatteryCard)
        BatteryImage = findViewById(R.id.batteryImage)

        ClothesCard = findViewById(R.id.ClothesCard)
        ClothesImage = findViewById(R.id.clothesImage)

        PlasticCard.setOnClickListener{
            if (plasticFlag == false){
                Log.d(TAG, "Enable")
                PlasticImage.setImageResource(R.drawable.is_enable_plastic_50)
                plasticFlag = !plasticFlag
            }
            else{
                Log.d(TAG, "Disable")
                PlasticImage.setImageResource(R.drawable.is_disable_plastic_50)
                plasticFlag = !plasticFlag
            }
        }

        GlassCard.setOnClickListener{
            if (glassFlag == false){
                Log.d(TAG, "Enable")
                GlassImage.setImageResource(R.drawable.is_enable_glass_50)
                glassFlag = !glassFlag
            }
            else{
                Log.d(TAG, "Disable")
                GlassImage.setImageResource(R.drawable.is_disable_glass_50)
                glassFlag = !glassFlag
            }
        }

        MetalCard.setOnClickListener{
            if (metalFlag == false){
                Log.d(TAG, "Enable")
                MetalImage.setImageResource(R.drawable.is_enable_metal_50)
                metalFlag = !metalFlag
            }
            else{
                Log.d(TAG, "Disable")
                MetalImage.setImageResource(R.drawable.is_disable_metal_50)
                metalFlag = !metalFlag
            }
        }

        LightCard.setOnClickListener{
            if (lightFlag == false){
                Log.d(TAG, "Enable")
                LightImage.setImageResource(R.drawable.is_enable_light_50)
                lightFlag = !lightFlag
            }
            else{
                Log.d(TAG, "Disable")
                LightImage.setImageResource(R.drawable.is_disable_light_50)
                lightFlag = !lightFlag
            }
        }

        BatteryCard.setOnClickListener{
            if (batteryFlag == false){
                Log.d(TAG, "Enable")
                BatteryImage.setImageResource(R.drawable.is_enable_battery_50)
                batteryFlag = !batteryFlag
            }
            else{
                Log.d(TAG, "Disable")
                BatteryImage.setImageResource(R.drawable.is_disable_battery_50)
                batteryFlag = !batteryFlag
            }
        }

        ClothesCard.setOnClickListener{
            if (clothesFlag == false){
                Log.d(TAG, "Enable")
                ClothesImage.setImageResource(R.drawable.is_enable_clothes_50)
                clothesFlag = !clothesFlag
            }
            else{
                Log.d(TAG, "Disable")
                ClothesImage.setImageResource(R.drawable.is_disable_clothes_50)
                clothesFlag = !clothesFlag
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter())

        mMap.setOnMarkerClickListener(this)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(47.234, 39.700), 14.5f))

        mMap.uiSettings.setMapToolbarEnabled(false)
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
                        .snippet(property.property_description)
                        .position(LatLng(property.property_latitude, property.property_longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40)))
                }
            }
            override fun onCancelled(dataSnapshot: DatabaseError) {

            }
        })
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

        return false
    }

}