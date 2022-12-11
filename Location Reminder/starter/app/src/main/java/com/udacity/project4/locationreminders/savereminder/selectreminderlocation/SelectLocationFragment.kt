package com.udacity.project4.locationreminders.savereminder.selectreminderlocation


import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.BuildConfig
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSelectLocationBinding
import com.udacity.project4.locationreminders.geofence.GeofenceHelper
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import org.koin.android.ext.android.inject
import java.util.*

class SelectLocationFragment : BaseFragment(), OnMapReadyCallback {
    private var reminderLocation: String = ""
    private val TAG = "SelectLocationFragment"
    private val REQUEST_LOCATION_PERMISSION = 0
    private lateinit var geofenceClient: GeofencingClient
    private lateinit var geofenceHelper: GeofenceHelper
    private lateinit var marker: Marker
    //Use Koin to get the view model of the SaveReminder
    override val _viewModel: SaveReminderViewModel by inject()
    private lateinit var binding: FragmentSelectLocationBinding
    private lateinit var map: GoogleMap
    private var lon: Double = 0.0
    private var lat: Double = 0.0
    private lateinit var latLng: LatLng


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_select_location, container, false)

        binding.viewModel = _viewModel
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        setDisplayHomeAsUpEnabled(true)
        geofenceClient = LocationServices.getGeofencingClient(requireActivity())

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        geofenceHelper = GeofenceHelper(requireContext())



        binding.saveBTN.setOnClickListener {
            onLocationSelected()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onMapReady(gMap: GoogleMap?) {

        map = gMap!!
        poiClick(map)
        mapsOnLongClickListener(map)
        enableMyLocation(map)
    }

    private fun onLocationSelected() {

        marker.let { marker ->
            _viewModel.latitude.value = marker.position.latitude
            _viewModel.longitude.value = marker.position.longitude
            _viewModel.reminderSelectedLocationStr.value = marker.title
            _viewModel.navigationCommand.value = NavigationCommand.Back
        }
        latLng = LatLng(lat, lon)
        Log.d(TAG, "onLocationSelected: lat: $lat  lon: $lon")
        findNavController().navigateUp()
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

    private fun enableMyLocation(map: GoogleMap) {
        val currentUserLocation = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            map.isMyLocationEnabled = true
            currentUserLocation.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val zoomLevel = 10f
                        val userLocation = LatLng(location.latitude, location.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, zoomLevel))
                        marker = map.addMarker(
                            MarkerOptions()
                                .position(userLocation)
                                .title("My Location")
                        )
                        marker.showInfoWindow()
                    }
                }
        } else
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
    }

    private fun poiClick(map: GoogleMap) {

        map.setOnPoiClickListener { poi ->
            reminderLocation = poi.name
            map.clear()
            map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            ).showInfoWindow()
            lon = poi.latLng.longitude
            lat = poi.latLng.latitude
        }
    }


    private fun mapsOnLongClickListener(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1.5f, Long: %2.5f",
                latLng.latitude,
                latLng.longitude
            )

            reminderLocation = snippet
            map.clear()
            marker = map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(latLng.toString())
                    .snippet(snippet)
            )

            marker.showInfoWindow()

            lon = latLng.longitude
            lat = latLng.latitude
        }
    }



}
