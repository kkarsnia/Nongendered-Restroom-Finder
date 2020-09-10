package com.kkco.nongenderedrestroomfinder.maps.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kkco.nongenderedrestroomfinder.R
import com.kkco.nongenderedrestroomfinder.data.Result
import com.kkco.nongenderedrestroomfinder.databinding.FragmentRestroomMapBinding
import com.kkco.nongenderedrestroomfinder.di.Injectable
import com.kkco.nongenderedrestroomfinder.di.injectViewModel
import com.kkco.nongenderedrestroomfinder.ui.hide
import com.kkco.nongenderedrestroomfinder.ui.show
import com.kkco.nongenderedrestroomfinder.util.LOCATION_PERMISSION_REQUEST_CODE
import javax.inject.Inject

class RestroomMapFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mMap: GoogleMap
    private lateinit var locationViewModel: LocationViewModel

    private var mapReady = false
    private lateinit var progressBar: ProgressBar
    private var subscribeUiFired = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationViewModel = injectViewModel(viewModelFactory)

        //TODO: add lifecycle stuff
        // val binding = DataBindingUtil.inflate<FragmentLegoSetBinding>(
        //     inflater, R.layout.fragment_lego_set, container, false).apply {
        //     lifecycleOwner = this@LegoSetFragment
        //     fab.setOnClickListener { _ -> set.url?.let { intentOpenWebsite(activity!!,it) } }
        // }

        val binding = FragmentRestroomMapBinding.inflate(inflater, container, false)
        context ?: return binding.root

        progressBar = binding.progressBar

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            mapReady = true
            val orlando = LatLng(28.555680, -81.375171)
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    orlando,
                    14F
                )
            )
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkLocationPermissions()
    }

    private fun subscribeUi() {
        subscribeUiFired = true
        locationViewModel.locationData.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    progressBar.hide()
                    if (mapReady && locationViewModel.locationData != null) {
                        // restrooms = viewModel.restrooms
                        // restrooms = result.data
                        result.data?.forEach { restroom ->
                            if (restroom.longitude.toString().isNotEmpty()
                                && restroom.latitude.toString().isNotEmpty()
                            ) {
                                val marker = LatLng(restroom.latitude, restroom.longitude)
                                mMap.addMarker(
                                    MarkerOptions().position(marker).title(restroom.name)
                                )
                            }
                        }
                    }
                }
                Result.Status.LOADING -> progressBar.show()
                Result.Status.ERROR -> {
                    progressBar.hide()
                    //TODO: make this work without binding.root
                    // Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun startLocationUpdates() {
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        locationViewModel.getLocationLiveData().observe(this, Observer {

            // Set the location data to pass to API
            locationViewModel.locationDetails = it
            if (locationViewModel.locationDetails != null && !subscribeUiFired) {
                subscribeUi()
            }
            val marker = LatLng(it.latitude, it.longitude)
            mMap.addMarker(
                MarkerOptions()
                    .position(marker)
                    .title("Your Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    marker,
                    14F
                )
            )
            Log.d("RestroomMapFragment", "it.lat/lng: ${it}")
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("RestroomMapFragment", "onRequestPermissionsResult FIRED")
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates()
                } else {
                    //TODO: use snackbar here
                    Toast.makeText(
                        context,
                        "Unable to update location without permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}