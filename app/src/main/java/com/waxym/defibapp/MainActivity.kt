package com.waxym.defibapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import com.google.gson.GsonBuilder
import java.util.Collections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null

    private var currentLocation: Location? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar!!.hide()
        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.e("LOCATION CHANGED", "NEW LOCATION")
                currentLocation = location
                getList()
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

            }

            override fun onProviderEnabled(provider: String) {

            }

            override fun onProviderDisabled(provider: String) {

            }
        }

    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        } else {
            initLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLocation()
                } else {
                    Log.e("ERROR", "DENIED")
                }
            }
        }
    }

    private fun initLocation() {
        try {
            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 5f, locationListener)
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 5f, locationListener)
            currentLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            getList()
        } catch (e: SecurityException) {
            Log.e("Location error", "Location error")
        }

    }

    override fun onStop() {
        super.onStop()
        locationManager!!.removeUpdates(locationListener)
    }

    private fun getList() {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://data.toulouse-metropole.fr")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val retrofitInterface = retrofit.create<RetrofitInterface>(RetrofitInterface::class.java!!)

        val call = retrofitInterface.getResult("defibrillateurs", 100)

        call.enqueue(object : Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                Log.e("SUCCESS", "SUCCESS")
                val list = response.body().records
                calculateDistance(list!!)
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                Log.e("ERROR", "ERROR")
            }
        })
    }

    private fun calculateDistance(list: List<Record>) {
        for (i in list.indices) {
            val defibLocation = Location("")
            defibLocation.latitude = list[i].fields!!.geoPoint2d!![0]
            defibLocation.longitude = list[i].fields!!.geoPoint2d!![1]

            val distanceInMeters = currentLocation!!.distanceTo(defibLocation).toInt()
            list[i].fields!!.distance = distanceInMeters
        }
        filterByDistance(list)
    }

    private fun filterByDistance(list: List<Record>) {
        Collections.sort(list) { o1, o2 -> Integer.compare(o1.fields!!.distance, o2.fields!!.distance) }
        displayList(list)
    }

    private fun displayList(list: List<Record>) {
        val listView = findViewById<ListView>(R.id.listView)
        val defibAdapter = DefibAdapter(this, list)
        listView.adapter = defibAdapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val record = listView.adapter.getItem(position) as Record
            val latitude = record.fields!!.geoPoint2d!![0]
            val longitude = record.fields!!.geoPoint2d!![1]
            val url = "https://www.google.com/maps/dir/?api=1&destination=$latitude,$longitude&travelmode=walking"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}
