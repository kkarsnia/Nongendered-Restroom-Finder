package com.kkco.nongenderedrestroomfinder

//TODO: clean up
class LocationUtils {
//    private var fusedLocationProviderClient: FusedLocationProviderClient?= null
//private var fusedLocationProviderClient: FusedLocationProviderClient?= null
//    private var location : MutableLiveData<Location> = MutableLiveData()
//
//    private var lastKnownLocation: Location? = null
//
//    private lateinit var context: Context
//
//    // using singleton pattern to get the locationProviderClient
//    fun getInstance(appContext: Context): FusedLocationProviderClient? {
//        Log.d("LocationUtils","getInstance FIRED")
//        context = appContext
//        if(fusedLocationProviderClient == null)
//            Log.d("LocationUtils","getInstance fusedLocationProviderClient == null FIRED")
//            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(appContext)
//        return fusedLocationProviderClient
//    }
//
//    //TODO: maybe pass in the context in the constructor?
//    //TODO: check for device Location turned on
////    @SuppressLint("MissingPermission")
////    fun getLocation() : LiveData<Location> {
////        Log.d("LocationUtils","getLocation FIRED")
////        fusedLocationProviderClient?.lastLocation
////            ?.addOnSuccessListener { loc: Location? ->
////                Log.d("LocationUtils","Location: ${loc?.latitude} ${loc?.longitude}")
////                location.value = loc
////            }
////        return location
////    }
//    @SuppressLint("MissingPermission")
//    fun getLocation() : LiveData<Location> {
//        Log.d("LocationUtils","getLocation FIRED")
//        if(fusedLocationProviderClient == null) {
//            Log.d("LocationUtils","fusedLocationProviderClient == null")
//        }
//        fusedLocationProviderClient?.lastLocation
//            ?.addOnSuccessListener { loc: Location? ->
//                Log.d("LocationUtils","Location: ${loc?.latitude} ${loc?.longitude}")
//                location.value = loc
//            }
//        return location
//    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */

}