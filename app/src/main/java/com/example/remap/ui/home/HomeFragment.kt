package com.example.remap.ui.home

import android.app.backup.RestoreObserver
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
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.navigation.fragment.findNavController
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
import com.google.android.material.shape.MarkerEdgeTreatment
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

    var EcomobCategory = arrayListOf<MarkerOptions>()
    var PlasticCategory = arrayListOf<MarkerOptions>()
    var BatteriesCategory = arrayListOf<MarkerOptions>()
    var ClothesCategory = arrayListOf<MarkerOptions>()
    var GlassCategory = arrayListOf<MarkerOptions>()
    var LampsCategory = arrayListOf<MarkerOptions>()
    var MetalCategory = arrayListOf<MarkerOptions>()
    var PaperCategory = arrayListOf<MarkerOptions>()

    //Перенёс объявление кнопок сюда, так как иначе бы функция ResetFilterButtons не подцепляла кнопки
    private lateinit var filterBtnEcomob: Button
    private lateinit var filterBtnPlastic: Button
    private lateinit var filterBtnClothes: Button
    private lateinit var filterBtnBatteries: Button
    private lateinit var filterBtnGlass: Button
    private lateinit var filterBtnPaper: Button
    private lateinit var filterBtnMetal: Button
    private lateinit var filterBtnLight: Button

    //Флаги, которые будут проверять, была ли до этого нажата кнопка
    //и если это так, то при нажатии все фильтры сбросятся и появится карта со всеми метками
    var isClickedBtnEcomob = false
    var isClickedBtnPlastic = false
    var isClickedBtnClothes = false
    var isClickedBtnBatteries = false
    var isClickedBtnGlass = false
    var isClickedBtnPaper = false
    var isClickedBtnMetal = false
    var isClickedBtnLight = false

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

        filterBtnEcomob = root.findViewById(R.id.fBtnEcomob)
        filterBtnEcomob.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(!isClickedBtnEcomob){
                    ResetAllFilterFlags()
                    isClickedBtnEcomob = !isClickedBtnEcomob
                    ResetFilterButtons()
                    filterBtnEcomob.background.setTint(ContextCompat.getColor(requireContext(), R.color.light_ecomob_green))
                    mMap.clear()
                    for (Ecomob in EcomobCategory)
                        mMap.addMarker(Ecomob)
                }
                else{
                    isClickedBtnEcomob = !isClickedBtnEcomob
                    ResetFilterButtons()
                    readData()
                }
            }
        })

        filterBtnPlastic = root.findViewById(R.id.fBtnPlastic)
        filterBtnPlastic.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isClickedBtnPlastic){
                    ResetAllFilterFlags()
                    isClickedBtnPlastic = !isClickedBtnPlastic
                    ResetFilterButtons()
                    filterBtnPlastic.background.setTint(ContextCompat.getColor(requireContext(), R.color.light_blue))
                    mMap.clear()
                    for (Plastic in PlasticCategory)
                        mMap.addMarker(Plastic)
                }
                else{
                    isClickedBtnPlastic = !isClickedBtnPlastic
                    ResetFilterButtons()
                    readData()
                }
            }
        })

        filterBtnClothes = root.findViewById(R.id.fBtnClothes)
        filterBtnClothes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(!isClickedBtnClothes){
                    ResetAllFilterFlags()
                    isClickedBtnPlastic = !isClickedBtnPlastic
                    ResetFilterButtons()
                    filterBtnClothes.background.setTint(ContextCompat.getColor(requireContext(), R.color.clothes_color))
                    mMap.clear()
                    for (Clothes in ClothesCategory)
                        mMap.addMarker(Clothes)
                }
                else{
                    isClickedBtnPlastic = !isClickedBtnPlastic
                    ResetFilterButtons()
                    readData()
                }
            }
        })

        filterBtnBatteries = root.findViewById(R.id.fBtnBatteries)
        filterBtnBatteries.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(!isClickedBtnBatteries){
                    ResetAllFilterFlags()
                    isClickedBtnBatteries = !isClickedBtnBatteries
                    ResetFilterButtons()
                    filterBtnBatteries.background.setTint(ContextCompat.getColor(requireContext(), R.color.batteries_color))
                    mMap.clear()
                    for (Batteries in BatteriesCategory)
                        mMap.addMarker(Batteries)
                }
                else{
                    isClickedBtnBatteries = !isClickedBtnBatteries
                    ResetFilterButtons()
                    readData()
                }
            }
        })

        filterBtnGlass = root.findViewById(R.id.fBtnGlass)
        filterBtnGlass.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(!isClickedBtnGlass){
                    ResetAllFilterFlags()
                    isClickedBtnGlass = !isClickedBtnGlass
                    ResetFilterButtons()
                    filterBtnGlass.background.setTint(ContextCompat.getColor(requireContext(), R.color.glass_color))
                    mMap.clear()
                    for (Glass in GlassCategory)
                        mMap.addMarker(Glass)
                }
                else{
                    isClickedBtnGlass = !isClickedBtnGlass
                    ResetFilterButtons()
                    readData()
                }
            }
        })

        filterBtnPaper = root.findViewById(R.id.fBtnPaper)
        filterBtnPaper.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isClickedBtnPaper){
                    ResetAllFilterFlags()
                    isClickedBtnPaper = !isClickedBtnPaper
                    ResetFilterButtons()
                    filterBtnPaper.background.setTint(ContextCompat.getColor(requireContext(), R.color.paper_color))
                    mMap.clear()
                    for (Paper in PaperCategory)
                        mMap.addMarker(Paper)
                }
                else{
                    isClickedBtnPaper = !isClickedBtnPaper
                    ResetFilterButtons()
                    readData()
                }
            }
        })

        filterBtnMetal = root.findViewById(R.id.fBtnMetal)
        filterBtnMetal.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isClickedBtnMetal){
                    ResetAllFilterFlags()
                    isClickedBtnMetal = !isClickedBtnMetal
                    ResetFilterButtons()
                    filterBtnMetal.background.setTint(ContextCompat.getColor(requireContext(), R.color.dark_grey))
                    mMap.clear()
                    for (Metal in MetalCategory)
                        mMap.addMarker(Metal)
                }
                else{
                    isClickedBtnMetal = !isClickedBtnMetal
                    ResetFilterButtons()
                    readData()
                }
            }
        })

        filterBtnLight = root.findViewById(R.id.fBtnLight)
        filterBtnLight.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(!isClickedBtnLight){
                    ResetAllFilterFlags()
                    isClickedBtnLight = !isClickedBtnLight
                    ResetFilterButtons()
                    filterBtnLight.background.setTint(ContextCompat.getColor(requireContext(), R.color.light_yellow))
                    mMap.clear()
                    for (Light in LampsCategory)
                        mMap.addMarker(Light)
                }
                else{
                    isClickedBtnLight = !isClickedBtnLight
                    ResetFilterButtons()
                    readData()
                }
            }
        })

        readData()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)


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

    //Сбрасывает фон всех кнопок к первоначальному
    fun ResetFilterButtons(
        ecomob_filter_button: Button = filterBtnEcomob,
        light_filter_button: Button = filterBtnLight,
        plastic_filter_button: Button = filterBtnPlastic,
        clothes_filter_button: Button = filterBtnClothes,
        glass_filter_button: Button = filterBtnGlass,
        batteries_filter_button: Button = filterBtnBatteries,
        metal_filter_button: Button = filterBtnMetal,
        paper_filter_button : Button = filterBtnPaper
    ){
        ecomob_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        light_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        plastic_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        clothes_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        glass_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        batteries_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        metal_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.white))
        paper_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.white))
    }

    fun ResetAllFilterFlags(){
        isClickedBtnPlastic = false
        isClickedBtnEcomob = false
        isClickedBtnLight = false
        isClickedBtnMetal = false
        isClickedBtnBatteries = false
        isClickedBtnPaper = false
        isClickedBtnGlass = false
        isClickedBtnClothes = false
    }

    //Определённо этот код с кучами строк нужно будет поменять, но пока пусть будет так
    fun PropertyFilter(property: Properties){
        if (property.categoryEcomob) {
            EcomobCategory.add(
                MarkerOptions()
                    .title(property.property_name)
                    .snippet(
                        property.property_description +
                                "\n\nГрафик работы: " + property.property_office_hours +
                                "\nКонтакты: " + property.property_contacts +
                                "\nАдрес: " + property.property_adress
                    )
                    .position(LatLng(property.property_latitude, property.property_longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            )
        }
        else if (property.categoryPlastic){
            PlasticCategory.add(
                MarkerOptions()
                    .title(property.property_name)
                    .snippet(
                        property.property_description +
                                "\n\nГрафик работы: " + property.property_office_hours +
                                "\nКонтакты: " + property.property_contacts +
                                "\nАдрес: " + property.property_adress
                    )
                    .position(LatLng(property.property_latitude, property.property_longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            )
        }
        else if (property.categoryBatteries){
            BatteriesCategory.add(
                MarkerOptions()
                    .title(property.property_name)
                    .snippet(
                        property.property_description +
                                "\n\nГрафик работы: " + property.property_office_hours +
                                "\nКонтакты: " + property.property_contacts +
                                "\nАдрес: " + property.property_adress
                    )
                    .position(LatLng(property.property_latitude, property.property_longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            )
        }
        else if (property.categoryClothes){
            ClothesCategory.add(
                MarkerOptions()
                    .title(property.property_name)
                    .snippet(
                        property.property_description +
                                "\n\nГрафик работы: " + property.property_office_hours +
                                "\nКонтакты: " + property.property_contacts +
                                "\nАдрес: " + property.property_adress
                    )
                    .position(LatLng(property.property_latitude, property.property_longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            )
        }
        else if (property.categoryGlass){
            GlassCategory.add(
                MarkerOptions()
                    .title(property.property_name)
                    .snippet(
                        property.property_description +
                                "\n\nГрафик работы: " + property.property_office_hours +
                                "\nКонтакты: " + property.property_contacts +
                                "\nАдрес: " + property.property_adress
                    )
                    .position(LatLng(property.property_latitude, property.property_longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            )
        }
        else if (property.categoryLamps){
            LampsCategory.add(
                MarkerOptions()
                    .title(property.property_name)
                    .snippet(
                        property.property_description +
                                "\n\nГрафик работы: " + property.property_office_hours +
                                "\nКонтакты: " + property.property_contacts +
                                "\nАдрес: " + property.property_adress
                    )
                    .position(LatLng(property.property_latitude, property.property_longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            )
        }
        else if (property.categoryMetal){
            MetalCategory.add(
                MarkerOptions()
                    .title(property.property_name)
                    .snippet(
                        property.property_description +
                                "\n\nГрафик работы: " + property.property_office_hours +
                                "\nКонтакты: " + property.property_contacts +
                                "\nАдрес: " + property.property_adress
                    )
                    .position(LatLng(property.property_latitude, property.property_longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            )
        }
        else if (property.categoryPaper){
            PaperCategory.add(
                MarkerOptions()
                    .title(property.property_name)
                    .snippet(
                        property.property_description +
                                "\n\nГрафик работы: " + property.property_office_hours +
                                "\nКонтакты: " + property.property_contacts +
                                "\nАдрес: " + property.property_adress
                    )
                    .position(LatLng(property.property_latitude, property.property_longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            )
        }

    }

    fun readData(){
        firebaseDatabase.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                PropertyList.clear()
                for (ds in dataSnapshot.children){
                    var Property = ds.getValue(Properties::class.java)
                    PropertyList.add(Property!!)
                }
                for(property in PropertyList){
                    PropertyFilter(property)
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

