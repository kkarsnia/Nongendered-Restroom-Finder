package com.kkco.nongenderedrestroomfinder.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.kkco.nongenderedrestroomfinder.R
import com.kkco.nongenderedrestroomfinder.data.Result
import com.kkco.nongenderedrestroomfinder.databinding.FragmentRestroomMapBinding
import com.kkco.nongenderedrestroomfinder.di.Injectable
import com.kkco.nongenderedrestroomfinder.di.injectViewModel
import com.kkco.nongenderedrestroomfinder.restrooms.data.Restroom
import com.kkco.nongenderedrestroomfinder.restrooms.ui.RestroomListViewModel
import com.kkco.nongenderedrestroomfinder.ui.hide
import com.kkco.nongenderedrestroomfinder.ui.show
import javax.inject.Inject

class RestroomMapFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RestroomListViewModel

    private lateinit var mMap: GoogleMap

    // private lateinit var viewModel: RestroomListViewModel
    private lateinit var restrooms: Result<List<Restroom>>
    private var mapReady = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        // viewModel.restrooms.observe(this, Observer { restrooms ->
        //     this.restrooms = restrooms
        //     updateMap()
        // })
        val binding = FragmentRestroomMapBinding.inflate(inflater, container, false)
        context ?: return binding.root
        // var rootView = inflater.inflate(R.layout.fragment_restroom_map, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            mapReady = true
            val orlando = LatLng(28.555680, -81.375171)
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    orlando,
                    12F
                )
            )
            // updateMap()
            subscribeUi(binding)
        }
        // subscribeUi(binding)
        return binding.root
    }

    // override fun onActivityCreated(savedInstanceState: Bundle?) {
    //     super.onActivityCreated(savedInstanceState)
    //     activity.let {
    //         // viewModel = ViewModelProviders.of(it!!).get(RestroomListViewModel::class.java)
    //         viewModel = injectViewModel(viewModelFactory)
    //         viewModel.restrooms.observe(this, Observer { restrooms ->
    //             this.restrooms = restrooms
    //             updateMap()
    //         })
    //     }
    // }

    private fun subscribeUi(binding: FragmentRestroomMapBinding) {
        viewModel.restrooms.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    result.data?.forEach { restroom ->
                        if (!restroom.longitude.toString().isEmpty()
                            && !restroom.latitude.toString().isEmpty()
                        ) {
                            val marker = LatLng(restroom.latitude, restroom.longitude)
                            mMap.addMarker(MarkerOptions().position(marker).title(restroom.name))
                        }
                    }
                }
                Result.Status.LOADING -> binding.progressBar.show()
                Result.Status.ERROR -> {
                    binding.progressBar.hide()
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun updateMap() {
        if (mapReady && restrooms != null) {
            restrooms.data?.forEach { restroom ->
                if (!restroom.longitude.toString().isEmpty()
                    && !restroom.latitude.toString().isEmpty()
                ) {
                    val marker = LatLng(restroom.latitude, restroom.longitude)
                    mMap.addMarker(MarkerOptions().position(marker).title(restroom.name))
                }
            }
        }
    }
}