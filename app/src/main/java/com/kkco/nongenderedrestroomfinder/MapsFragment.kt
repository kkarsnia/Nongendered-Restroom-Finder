package com.kkco.nongenderedrestroomfinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.utils.collection.addMarker

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val orlando = LatLng(28.555680, -81.375171)
        googleMap.addMarker(MarkerOptions().position(orlando).title("ORLANDO"))
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                orlando,
                12F
            )
        )

        val markerManager = MarkerManager(googleMap)
        addMarker(markerManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun addMarker(markerManager: MarkerManager) {
        val markerCollection: MarkerManager.Collection = markerManager.newCollection()
        markerCollection.addMarker {
            position(LatLng(28.943598, -81.456740))
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            title("MORELANDO")
        }
        markerCollection.setOnMarkerClickListener { marker ->
            Toast.makeText(
                context,
                "Marker clicked: " + marker.title,
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    companion object {
        fun newInstance(): MapsFragment {
            val args = Bundle()

            val fragment = MapsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}