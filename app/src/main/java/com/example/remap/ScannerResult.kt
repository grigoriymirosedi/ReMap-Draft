package com.example.remap

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.remap.models.EcoMarkers
import com.example.remap.ui.notifications.NotificationsFragment
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ScannerResult : AppCompatActivity() {

    var mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("EcoMarkersDetails")
    var ecoMarkersArrayList: ArrayList<EcoMarkers> = arrayListOf<EcoMarkers>()

    private lateinit var resultText: TextView

    private lateinit var firstCardView: CardView
    private lateinit var firstResultImageView: ImageView
    private lateinit var firstResultTV: TextView

    private lateinit var secondCardView: CardView
    private lateinit var secondResultImageView: ImageView
    private lateinit var secondResultTV: TextView

    private lateinit var thirdCardView: CardView
    private lateinit var thirdResultImageView: ImageView
    private lateinit var thirdResultTextView: TextView

    private lateinit var showFullEcoMarkerList: Button

    private lateinit var notificationsFragment: NotificationsFragment

    //private lateinit var ecoMarkersFragmentContainer: FragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner_result)
        getSupportActionBar()?.hide();

        var first_result = intent.getIntExtra("FirstResult",0)
        var second_result = intent.getIntExtra("SecondResult",1)
        var third_result = intent.getIntExtra("ThirdResult",2)

        notificationsFragment = NotificationsFragment()

        readEcoMarkersData()

        resultText = findViewById(R.id.resultText)

        firstCardView = findViewById(R.id.firstCardView)
        firstResultImageView = findViewById(R.id.firstResultImageView)
        firstResultTV = findViewById(R.id.firstResultTV)

        secondCardView = findViewById(R.id.secondCardView)
        secondResultImageView = findViewById(R.id.secondResultImageView)
        secondResultTV = findViewById(R.id.secondResultTV)

        thirdCardView = findViewById(R.id.thirdCardView)
        thirdResultImageView = findViewById(R.id.thirdResultImageView)
        thirdResultTextView = findViewById(R.id.thirdResultTV)


        showFullEcoMarkerList = findViewById(R.id.showFullListBtn)

        showFullEcoMarkerList.setOnClickListener {
            hideViews()
            supportFragmentManager.beginTransaction()
                .add(R.id.ecoMarkersFragmentContainter, notificationsFragment).addToBackStack(null).commit()
        }

        firstCardView.setOnClickListener {
            val intent = Intent(this, EcoMarkersDetails::class.java)
            intent.putExtra("MARKER", ecoMarkersArrayList[first_result])
            startActivity(intent)
        }

        secondCardView.setOnClickListener {
            val intent = Intent(this, EcoMarkersDetails::class.java)
            intent.putExtra("MARKER", ecoMarkersArrayList[second_result])
            startActivity(intent)
        }

        thirdCardView.setOnClickListener {
            val intent = Intent(this, EcoMarkersDetails::class.java)
            intent.putExtra("MARKER", ecoMarkersArrayList[third_result])
            startActivity(intent)
        }

    }

    fun bindViewsWithResult(ecoMarkerImage: ImageView, ecoMarkerText: TextView, resultPosition: Int) {
        Picasso.get().load(ecoMarkersArrayList[resultPosition].imageIconURL).fit().into(ecoMarkerImage)
        ecoMarkerText.text = ecoMarkersArrayList[resultPosition].ecoTitle
    }


    fun showView() {
        resultText.visibility = View.VISIBLE

        firstResultImageView.visibility = View.VISIBLE
        firstResultTV.visibility = View.VISIBLE

        secondResultImageView.visibility = View.VISIBLE
        secondResultTV.visibility = View.VISIBLE

        thirdResultImageView.visibility = View.VISIBLE
        thirdResultTextView.visibility = View.VISIBLE

        showFullEcoMarkerList.visibility = View.VISIBLE
    }

    fun hideViews() {
        resultText.visibility = View.GONE

        firstResultImageView.visibility = View.GONE
        firstResultTV.visibility = View.GONE

        secondResultImageView.visibility = View.GONE
        secondResultTV.visibility = View.GONE

        thirdResultImageView.visibility = View.GONE
        thirdResultTextView.visibility = View.GONE

        showFullEcoMarkerList.visibility = View.GONE
    }

    fun readEcoMarkersData() {
        mDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                ecoMarkersArrayList.clear()
                for (ds in dataSnapshot.children){
                    var ecoMarkers = ds.getValue(EcoMarkers::class.java)
                    ecoMarkersArrayList.add(ecoMarkers!!)
                }
                bindViewsWithResult(firstResultImageView, firstResultTV, intent.getIntExtra("FirstResult",0))
                bindViewsWithResult(secondResultImageView, secondResultTV, intent.getIntExtra("SecondResult", 1) )
                bindViewsWithResult(thirdResultImageView, thirdResultTextView, intent.getIntExtra("ThirdResult", 2))
            }
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Toast.makeText(this@ScannerResult, dataSnapshot.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        supportFragmentManager.beginTransaction().remove(notificationsFragment).commit()
        showView()
        super.onBackPressed()
    }
}