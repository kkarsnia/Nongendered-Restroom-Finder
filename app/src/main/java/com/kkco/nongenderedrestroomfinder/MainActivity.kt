package com.kkco.nongenderedrestroomfinder

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mapFragment: SupportMapFragment

    private var lastKnownLocation: Location? = null

    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        //TODO: this is not working with savedInstanceState != null
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            //TODO: add lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MapsFragment.newInstance())
                .commitNow()

//      mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//      LocationUtils(mapFragment, this)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MapsFragment.newInstance())
            .commitNow()

        if (checkPermissions()) {
            getDeviceLocation()
        } else {
            requestPermissions()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
//      map?.let { map ->
//        outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
//        outState.putParcelable(KEY_LOCATION, lastKnownLocation)
//      }
        outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        super.onSaveInstanceState(outState)
    }

    private fun getDeviceLocation() {
        /*
             * Get the best and most recent location of the device, which may be null in rare
             * cases when a location is not available.
             */
        try {
            if (fusedLocationProviderClient != null) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            Log.d(
                                "MainActivity",
                                "lastKnownLocation: " + lastKnownLocation!!.latitude + ", " + lastKnownLocation!!.longitude
                            )
//              map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                  LatLng(lastKnownLocation!!.latitude,
//                      lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.d("MainActivity", "Current location is null. Using defaults.")
                        Log.e("MainActivity", "Exception: %s", task.exception)
//            map?.moveCamera(CameraUpdateFactory
//                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
//            map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun checkPermissions(): Boolean {
        return !(ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
        )
    }

    //TODO: create a dialog to warn user if they deny permissions for negative path
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                getDeviceLocation()
            }
        }
    }
//
//  fun getLastKnownLocation() {
//    Timber.d("MainActivity FIRED")
//    Log.d("MainActivity","FIRED")
//    LocationUtils().getInstance(this)
//    LocationUtils().getLocation().observe(this, Observer { loc: Location? ->
//      //TODO: fix this null stuff
//      Timber.d("MainActivity Location: ${loc?.latitude} ${loc?.longitude}")
//      Log.d("MainActivity","Location: ${loc?.latitude} ${loc?.longitude}")
//      val location: Location = loc!!
//    })
//  }

    companion object {

        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        private const val KEY_LOCATION = "location"
    }
}