package com.udacity.project4.locationreminders.savereminder.selectreminderlocation


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
    private var isMapReady = false

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

        if (!isForegroundPermissionGranted())
            requestLocationPermission()


        binding.saveBTN.setOnClickListener {
            onLocationSelected()
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun isForegroundPermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

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


    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap
        isMapReady = true
        onPoiClick(map)
        setOnLongClick(map)
        if (isForegroundPermissionGranted())
            getUserLocation(map)
    }


    @SuppressLint("MissingPermission")
    private fun getUserLocation(map: GoogleMap) {
        val userCurrentLocation = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (isForegroundPermissionGranted()) {
            checkDeviceLocationStatues()
            map.isMyLocationEnabled = true
            userCurrentLocation.lastLocation
                .addOnSuccessListener { currentLocation ->
                    currentLocation?.let {
                        location = LatLng(currentLocation.latitude, currentLocation.longitude)
                        reminderLocationTitle = "Current Location"
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))
                        marker = map.addMarker(
                            MarkerOptions().position(location)
                                .title(reminderLocationTitle)
                        )

                        marker.showInfoWindow()
                    }
                }
        } else {
            Toast.makeText(
                requireContext(),
                "Error: Unable to determine current location",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun checkDeviceLocationStatues(): Boolean {
        var locationState = true
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }

        val locationRequestBuilder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(requireActivity())

        settingsClient.checkLocationSettings(locationRequestBuilder.build()).apply {
            addOnCompleteListener {
                locationState = true
            }
            addOnFailureListener {
                if (it is ResolvableApiException) {
                    try {
                        it.startResolutionForResult(
                            requireActivity(),
                            SaveReminderFragment.REQUEST_TURN_DEVICE_LOCATION_ON
                        )
                    } catch (ex: java.lang.Exception) {
                        Log.d("SaveFragment", "checkDeviceLocationStatues: Error")
                    }
                } else {
                    locationState = false
                    Snackbar.make(
                        requireView(),
                        R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                    ).setAction(android.R.string.ok) {
                        checkDeviceLocationStatues()
                    }.show()
                }
            }
        }
        return locationState
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
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

    override fun onResume() {
        super.onResume()
        if (isMapReady)
            if (isForegroundPermissionGranted())
                getUserLocation(map)
            else
                Toast.makeText(
                    requireContext(),
                    "Allow Location permission to get your current location",
                    Toast.LENGTH_LONG
                ).show()
        if (!isForegroundPermissionGranted())
            Toast.makeText(
                requireContext(),
                "Error: Unable to determine current location",
                Toast.LENGTH_SHORT
            ).show()
    }

    companion object {
        const val REQUEST_LOCATION_PERMISSION: Int = 0
    }
}
