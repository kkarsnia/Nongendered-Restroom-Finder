package com.kkco.nongenderedrestroomfinder

// import androidx.navigation.NavController
// import androidx.navigation.findNavController
// import androidx.navigation.ui.AppBarConfiguration
// import androidx.navigation.ui.setupWithNavController
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kkco.nongenderedrestroomfinder.databinding.ActivityMainBinding
import com.kkco.nongenderedrestroomfinder.maps.RestroomMapFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    // private lateinit var navController: NavController

    private var lastKnownLocation: Location? = null

    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.executePendingBindings()
        // navController = findNavController(R.id.nav_host_fragment)
        // binding.navigationView.setupWithNavController(navController)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//         supportFragmentManager.beginTransaction()
//             .replace(R.id.container, MapsFragment.newInstance())
//             .commitNow()

//         supportFragmentManager.beginTransaction()
//             .replace(R.id.container, RestroomListFragment())
//             .commitNow()
        onOpenMap()

        // if (checkPermissions()) {
        //     getDeviceLocation()
        // } else {
        //     requestPermissions()
        // }
    }

    override fun onSaveInstanceState(outState: Bundle) {
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
                            //TODO: inform fragment
                        }
                    } else {
                        Log.d("MainActivity", "Current location is null. Using defaults.")
                        Log.e("MainActivity", "Exception: %s", task.exception)
                        //TODO: inform fragment
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

    internal fun onOpenMap() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RestroomMapFragment())
            .commitNow()
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