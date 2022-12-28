package com.udacity.project4.locationreminders.savereminder.selectreminderlocation


import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.databinding.FragmentSelectLocationBinding
import com.udacity.project4.locationreminders.savereminder.SaveReminderFragment
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import org.koin.android.ext.android.inject
import java.util.*

class SelectLocationFragment : BaseFragment(), OnMapReadyCallback {

    //Use Koin to get the view model of the SaveReminder
    override val _viewModel: SaveReminderViewModel by inject()
    private lateinit var binding: FragmentSelectLocationBinding
    private lateinit var map: GoogleMap
    private lateinit var marker: Marker
    private lateinit var reminderLocationTitle: String
    private lateinit var location: LatLng



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_location, container, false)

        binding.viewModel = _viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(true)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)



        binding.saveBTN.setOnClickListener{
            onLocationSelected()
            findNavController().navigateUp()
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE_BACKGROUND){
            getUserLocation(map)
        }
    }

    private fun onLocationSelected() {
        _viewModel.apply {
            latitude.value = location.latitude
            longitude.value = location.longitude
            reminderSelectedLocationStr.value = reminderLocationTitle
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap
        getUserLocation(map)
        onPoiClick(map)
        setOnLongClick(map)
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation(map: GoogleMap) {
        val userCurrentLocation = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (isPermissionGranted()) {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
                checkDeviceLocationStatues(requireActivity(), requireView())
            }
            else requestLocationPermission()
            map.isMyLocationEnabled = true
            userCurrentLocation.lastLocation
                .addOnSuccessListener { currentLocation ->
                    currentLocation?.let {
                        location = LatLng(currentLocation.latitude, currentLocation.longitude)
                        reminderLocationTitle = "Current Location"
                        map.moveCamera(CameraUpdateFactory.newLatLng(location))
                        marker = map.addMarker(
                            MarkerOptions().position(location)
                                .title(reminderLocationTitle)
                        )

                        marker.showInfoWindow()
                    }
                }
        } else
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private fun requestLocationPermission(){
        val isForegroundPermissionGranted = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if(isForegroundPermissionGranted){
            val backgroundPermission = ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if(backgroundPermission)
                checkDeviceLocationStatues(requireActivity(), requireView())
            else ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                REQUEST_CODE_BACKGROUND
            )
        }
    }
    private fun onPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            map.clear()
            reminderLocationTitle = poi.name
            marker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(reminderLocationTitle)
            )
                .apply {
                    showInfoWindow()
                }
            location = LatLng(poi.latLng.latitude, poi.latLng.longitude)
        }
    }

    private fun setOnLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { location ->
            map.clear()
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1.5f, Long: %2.5f",
                location.latitude,
                location.longitude
            )
            reminderLocationTitle = snippet
            marker = map.addMarker(
                MarkerOptions().position(location)
                    .snippet(reminderLocationTitle)
                    .title(location.toString())
            )
                .apply {
                    showInfoWindow()
                }

            this.location = location
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION: Int = 0
        private const val REQUEST_CODE_BACKGROUND = 102929
        private fun checkDeviceLocationStatues(activity: Activity, view: View): Boolean{
            var locationState = true
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_LOW_POWER
            }

            val locationRequestBuilder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

            val settingsClient = LocationServices.getSettingsClient(activity)

            settingsClient.checkLocationSettings(locationRequestBuilder.build()).apply {
                addOnCompleteListener{
                    locationState = true
                }
                addOnFailureListener{
                    if(it is ResolvableApiException){
                        try {
                            it.startResolutionForResult(
                                activity,
                                SaveReminderFragment.REQUEST_TURN_DEVICE_LOCATION_ON
                            )
                        }catch (ex: java.lang.Exception){
                            Log.d("SaveFragment", "checkDeviceLocationStatues: Error")
                        }
                    }else{
                        locationState = false
                        Snackbar.make(
                            view,
                            R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                        ).setAction(android.R.string.ok) {
                            checkDeviceLocationStatues(activity, view)
                        }.show()
                    }
                }
            }
            return locationState
        }
    }
}
