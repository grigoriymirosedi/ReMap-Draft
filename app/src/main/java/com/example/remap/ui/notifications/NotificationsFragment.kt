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

    //Перенести содержимое отсюда в Firebase, первые две иконки уже там
    //не забыть что-нибудь сделать с ecoExamples в Firebase
    fun InitializeMarkers(){
        /*
        ecoMarkersArrayList = arrayListOf<EcoMarkers>(
            //https://rsbor.ru/where-to-start/kak-razobratsya-v-markirovkax/
            //https://rsbor-msk.ru/markirovka/
            EcoMarkers(R.drawable.eco_pet,
                R.drawable.plastic_bottles_pet,
                "Пластик с маркировкой 1, он же PET или ПЭТ (полиэтилентерефталат)",
                "Примеры изделий из ПЭТ:",
                "бутылки из-под напитков (воды, молока, газировки) и растительного масла;\n" +
                        "- прозрачные одноразовые контейнеры из-под фруктов, ягод и печенья;\n" +
                        "- емкости от косметических и бытовых средств;\n" +
                        "- коробки от игрушек и зубных щеток.",
                "Проще всего сдать на переработку бутылки из-под напитков. Пункты приёма можно посмотреть на нашей карте. " +
                        "Прочий твердый («небутылочный») ПЭТ принимают редко. От упаковки из небутылочного ПЭТа лучше, по возможности, отказаться."
            ),
            EcoMarkers(R.drawable.eco_pehd,
                R.drawable.detergent,
                "Пластик с маркировкой 2, он же HDPE или ПНД (полиэтилен низкого давления)",
                "Примеры изделий из ПНД:",
                "- флаконы от бытовой химии, шампуней, гелей для душа;\n" +
                        "- канистры, бидоны;\n" +
                        "- крышки от бутылок для напитков и упаковок тетрапак.",
                "Твёрдые флаконы и бутылки сдать на переработку относительно несложно. Пункты приёма можно найти на нашей карте. " +
                        "Иногда маркировку «2» можно найти на мягком пластике — пакетах, плёнке и т.п. Её тоже можно сдать, хотя придется поискать пункты."
            ),
            EcoMarkers(R.drawable.eco_pvc,
                R.drawable.food_container,
                "Пластик с маркировкой 3, он же PVC или ПВХ (поливинилхлорид)",
                "Примеры изделий из ПВХ:",
                "- игрушки;\n" +
                        "- одноразовые контейнеры;\n" +
                        "- подложки для тортов и конфет;\n" +
                        "- трубы, оконные рамы, садовые шланги;\n" +
                        "- круги и матрасы для плавания;\n" +
                        "- занавески для ванной;\n" +
                        "- термоусадочная пленка (повторяет форму емкости даже после снятия).",
                "ПВХ принимают на переработку очень редко. По возможности выбирайте другой материал. " +
                        "Если в хозяйстве обнаружились предметы из ПВХ - на нашей карте вы можете найти пункты переработки, которые его принимают."),
            EcoMarkers(R.drawable.eco_peld,
                R.drawable.plastic_bag_2,
                "Пластик с маркировкой 4, он же LDPE или ПВД (полиэтилен высокого давления)",
                "Примеры изделий из ПВД:",
                "- плотные пакеты;\n" +
                        "- пупырчатый полиэтилен;\n" +
                        "- стрейч-пленка;\n" +
                        "- мусорные мешки;\n" +
                        "- упаковка из-под бытовой техники, порошка, подгузников, туалетной бумаги, ватных дисков и палочек.",
                "Этот материал, как правило, используют для производства мягкого пластика, жёсткие изделия встречаются реже. " +
                        "Список пунктов приёма Вы можете найти на нашей карте."
            ),
            EcoMarkers(R.drawable.eco_pp,
                R.drawable.plastic_bucket,
                "Пластик с маркировкой 5, он же PP или ПП (полипропилен)",
                "Примеры изделий из ПП:",
                "- многоразовые контейнеры, тазы, вёдра;\n" +
                        "- контейнеры из-под сметаны, морепродуктов, солений;\n" +
                        "- детские игрушки и яйца от Киндер Сюрприза;\n" +
                        "- крышки от кремов и бытовой химии;\n" +
                        "- трубочки для напитков;\n" +
                        "- пакеты из-под круп, сахара, хлеба.",
                "На переработку принимают в основном твердый полипропилен, мягкий — реже. " +
                        "Пункты приёма пакетов и плёнки Вы можете найти на нашей карте."
            ),
            EcoMarkers(R.drawable.eco_ps,
                R.drawable.food_package,
                "Пластик с маркировкой 6, он же PS или ПС (полистирол)",
                "Примеры изделий из ПС:",
                "- одноразовая посуда и контейнеры;\n" +
                        "- стаканчики для йогуртов;\n" +
                        "- вспененные подложки для мяса, овощей, сладостей;\n" +
                        "- вспененные упаковки для яиц;\n" +
                        "- крышки от одноразовых стаканчиков;\n" +
                        "- коробки от CD и DVD дисков;\n" +
                        "- пенопласт.",
                "Полистирол редко принимают в переработку. По возможности лучше отказаться от этого материала."),
            EcoMarkers(R.drawable.eco_other,
                R.drawable.eco_friendly,
                "Пластик с маркировкой 7, он же O (OTHER) или РС",
                "Примеры изделий из «семерки»:",
                "- металлизированная упаковка (например, от чипсов, шоколадных батончиков);\n" +
                        "- дой-паки и паучи;\n" +
                        "- тюбики от кремов и зубных паст;\n" +
                        "- «биоразлагаемые» пакеты;\n" +
                        "- упаковка от кормов для животных, кофе;\n" +
                        "- вакуумная упаковка для сыров и мясных изделий.",
                "Пластик типа 7 на переработку не принимают!"),
            /*EcoMarkers(R.drawable.eco_alu, "Алюминий", "Описание"),
            EcoMarkers(R.drawable.eco_fe, "Металл", "Описание"),*/
        )*/
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(requireContext(), EcoMarkersDetails::class.java)
        intent.putExtra("MARKER", ecoMarkersArrayList[position])
        startActivity(intent)
    }
}