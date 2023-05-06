package com.example.remap

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.remap.databinding.ActivityMainBinding
import com.example.remap.ml.TestModel
import com.example.remap.models.EcoMarkers
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        var REQUEST_CODE = 200;
    }
    var imageSize = 224

    var mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("EcoMarkersDetails")
    var ecoMarkersArrayList: ArrayList<EcoMarkers> = arrayListOf<EcoMarkers>()

    var CAMERA_PERMISSION_CODE = 1;

    private lateinit var binding: ActivityMainBinding

    //private lateinit var ScannerButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ReMap)
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

        /*ScannerButton = findViewById(R.id.scannerButton)

        readEcoMarkersData()

        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf<String>(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE);
        }

        ScannerButton.setOnClickListener{
            var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(cameraIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(cameraIntent, CAMERA_PERMISSION_CODE)
            }
        }*/

    }

    fun classifyImage(image: Bitmap) {
        val model = TestModel.newInstance(applicationContext)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        var byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        var intValues = IntArray(imageSize * imageSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0
        for(i in 0 until imageSize){
            for(j in 0 until imageSize){
                var value = intValues[pixel++]
                byteBuffer.putFloat((value and 0xFF) * (1f / 255f)) //Blue
                byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255f)) //Green
                byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255f)) //Red
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        var confidences = outputFeature0.floatArray;
        var maxConfidence = 0f;
        var maxPos = 0;
        var predMaxConfidence = 0f;
        var predMaxPos = 0;
        var predPredMaxConfidence = 0f;
        var predPredMaxPos = 0;
        for(i in 0 until confidences.size){
            if (confidences[i] > maxConfidence) {
                predPredMaxConfidence = predMaxConfidence
                predPredMaxPos = predMaxPos
                predMaxConfidence = maxConfidence
                predMaxPos = maxPos
                maxConfidence = confidences[i]
                maxPos = i
            }
            else if (confidences[i] > predMaxConfidence) {
                predPredMaxConfidence = predMaxConfidence
                predPredMaxPos = predMaxPos
                predMaxConfidence = confidences[i]
                predMaxPos = i
            }
            else if (confidences[i] > predPredMaxConfidence) {
                predPredMaxConfidence = confidences[i]
                predPredMaxPos = i
            }
        }

        var classes = arrayOf<String>("PET1", "HDPE2", "PVC3", "PELD4", "PP5", "PS6", "OTHER7")
        //resultTV.text = "Результат: " + classes[maxPos]

        val intent = Intent(this, ScannerResult::class.java)
        intent.putExtra("FirstResult", maxPos)
        intent.putExtra("SecondResult", predMaxPos)
        intent.putExtra("ThirdResult", predPredMaxPos)
        startActivity(intent)

        /*val intent = Intent(this, EcoMarkersDetails::class.java)
        intent.putExtra("MARKER", ecoMarkersArrayList[maxPos])
        startActivity(intent)*/



        // Releases model resources if no longer used.*/
        model.close()
    }

    fun readEcoMarkersData() {
        mDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                ecoMarkersArrayList.clear()
                for (ds in dataSnapshot.children){
                    var ecoMarkers = ds.getValue(EcoMarkers::class.java)
                    ecoMarkersArrayList.add(ecoMarkers!!)
                }
            }
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Toast.makeText(applicationContext, dataSnapshot.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_PERMISSION_CODE) {
            if (resultCode == RESULT_OK) {
                var image = data?.extras?.get("data") as? Bitmap
                var dimension = image?.let { Math.min(it.width, image!!.height) }
                image = dimension?.let { ThumbnailUtils.extractThumbnail(image, it, dimension) }
                //imageResult.setImageBitmap(image) тут должна быть картинка, она нам не нужна
                image = image?.let { Bitmap.createScaledBitmap(it, imageSize, imageSize, false) }
                if (image != null) {
                    classifyImage(image)
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("123123", "Cancelled")
            }
        }
    }*/

    override fun recreate() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        startActivity(getIntent())
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}