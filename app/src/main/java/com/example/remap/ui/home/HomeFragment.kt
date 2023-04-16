package com.example.remap.ui.home

import android.app.backup.RestoreObserver
import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.content.res.Configuration
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.remap.databinding.FragmentHomeBinding
import android.util.Log
import android.view.ContextMenu
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.marginBottom
import androidx.navigation.fragment.findNavController
import com.example.remap.R
import com.example.remap.models.Properties
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.MarkerEdgeTreatment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class HomeFragment : BottomSheetDialogFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    var mMap: GoogleMap? = null

    var INITIALIZE_POSITION = LatLng(47.23,39.72)
    //Ссылка на нашу базу данных: https://console.firebase.google.com/project/remap-1faaf/database/remap-1faaf-default-rtdb/data
    //для того, чтобы получить доступ, отправьте в лс мне свой email и я вам открою доступ, по другому никак :(
    var firebaseDatabase = FirebaseDatabase.getInstance().getReference("Properties")
    var PropertyList = arrayListOf<Properties>()

    //Поля внутри BottomSheetDialogue
    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetName: TextView
    private lateinit var bottomSheetDescription: TextView


    //Отвечает за Day/Night Mode
    private lateinit var DayNightSwitch: Switch
    private lateinit var DayNightImage: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    var isNightModeOn: Boolean = false

    //ArrayList`ы для фильтрации
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
    private var markerIsClicked: Boolean = false

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



        DayNightSwitch = root.findViewById(R.id.DayNightSwitch)
        DayNightImage = root.findViewById(R.id.DayNightImage)
        sharedPreferences = this.activity!!.getSharedPreferences("MODE", Context.MODE_PRIVATE)
        isNightModeOn = sharedPreferences.getBoolean("night", false)

        initDayNightSwitch()

        filterBtnEcomob = root.findViewById(R.id.fBtnEcomob)
        filterBtnEcomob.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(!isClickedBtnEcomob){
                    ResetAllFilterFlags()
                    isClickedBtnEcomob = !isClickedBtnEcomob
                    ResetFilterButtons()
                    filterBtnEcomob.background.setTint(ContextCompat.getColor(requireContext(), R.color.light_ecomob_green))
                    mMap?.clear()
                    for (Ecomob in EcomobCategory)
                        mMap?.addMarker(Ecomob)
                }
                else{
                    isClickedBtnEcomob = !isClickedBtnEcomob
                    ResetFilterButtons()
                    readData()
                }
            }
        })

        DayNightSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                DayNightImage.setImageResource(R.drawable.icons8_night_64);
                editor = sharedPreferences.edit()
                editor.putBoolean("night", true)
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                DayNightImage.setImageResource(R.drawable.icons8_sun_64);
                editor = sharedPreferences.edit()
                editor.putBoolean("night", false)
            }
            editor.apply()
        }

        filterBtnPlastic = root.findViewById(R.id.fBtnPlastic)
        filterBtnPlastic.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (!isClickedBtnPlastic){
                    ResetAllFilterFlags()
                    isClickedBtnPlastic = !isClickedBtnPlastic
                    ResetFilterButtons()
                    filterBtnPlastic.background.setTint(ContextCompat.getColor(requireContext(), R.color.light_blue))
                    mMap?.clear()
                    for (Plastic in PlasticCategory)
                        mMap?.addMarker(Plastic)
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
                    mMap?.clear()
                    for (Clothes in ClothesCategory)
                        mMap?.addMarker(Clothes)
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
                    mMap?.clear()
                    for (Batteries in BatteriesCategory)
                        mMap?.addMarker(Batteries)
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
                    mMap?.clear()
                    for (Glass in GlassCategory)
                        mMap?.addMarker(Glass)
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
                    mMap?.clear()
                    for (Paper in PaperCategory)
                        mMap?.addMarker(Paper)
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
                    mMap?.clear()
                    for (Metal in MetalCategory)
                        mMap?.addMarker(Metal)
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
                    mMap?.clear()
                    for (Light in LampsCategory)
                        mMap?.addMarker(Light)
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



    fun initDayNightSwitch() {
        DayNightSwitch.isChecked = isNightModeOn
        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            DayNightImage.setImageResource(R.drawable.icons8_night_64)
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DayNightImage.setImageResource(R.drawable.icons8_sun_64)
        }
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

        if ((context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) ==  Configuration.UI_MODE_NIGHT_YES) {
            mMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json))
        }

        mMap?.setInfoWindowAdapter(CustomInfoWindowAdapter())
        mMap?.setOnMarkerClickListener(this)

        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(INITIALIZE_POSITION, 14.5f))

        readData()

        mMap?.uiSettings?.setMapToolbarEnabled(false)
        mMap?.uiSettings?.setMapToolbarEnabled(false)
    }

    /* Changes the color of selected marker */
    override fun onMarkerClick(marker: Marker): Boolean {
        val dialog = BottomSheetDialog(requireContext())
        bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        bottomSheetName = bottomSheetView.findViewById(R.id.bottomSheetName)
        bottomSheetDescription = bottomSheetView.findViewById(R.id.bottomSheetDescription)
        dialog.setContentView(bottomSheetView)
        dialog.behavior.peekHeight = 400
        dialog.behavior.isFitToContents = true
        bottomSheetName.text = marker.title
        bottomSheetDescription.text = marker.snippet
        dialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        dialog.show()
        //dialog = BottomSheetDialog(requireContext(), )
        if (!markerIsClicked ){
            lastTouchedMarker = marker
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dark_green_pin_40))
            markerIsClicked = !markerIsClicked
        }
        else{
            lastTouchedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            lastTouchedMarker = marker
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dark_green_pin_40))
        }
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
        ecomob_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.default_button_color))
        light_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.default_button_color))
        plastic_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.default_button_color))
        clothes_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.default_button_color))
        glass_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.default_button_color))
        batteries_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.default_button_color))
        metal_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.default_button_color))
        paper_filter_button.background.setTint(ContextCompat.getColor(requireContext(), R.color.default_button_color))
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
        markerIsClicked = false
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
        if (property.categoryPlastic){
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
        if (property.categoryBatteries){
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
        if (property.categoryClothes){
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
        if (property.categoryGlass){
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
        if (property.categoryLamps){
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
        if (property.categoryMetal){
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
        if (property.categoryPaper){
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


    //Считывает данные с Firebase
    fun readData(){
        firebaseDatabase.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                PropertyList.clear()
                for (ds in dataSnapshot.children){
                    var Property = ds.getValue(Properties::class.java)
                    PropertyList.add(Property!!)
                }
                for(property in PropertyList){
                    mMap?.addMarker(MarkerOptions()
                        .title(property.property_name)
                        .snippet(property.property_description +
                                "\n\nГрафик работы: " + property.property_office_hours +
                                "\nКонтакты: " + property.property_contacts +
                                "\nАдрес: " + property.property_adress)
                        .position(LatLng(property.property_latitude, property.property_longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40)))
                    PropertyFilter(property)
                }
            }
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
        })
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

}

