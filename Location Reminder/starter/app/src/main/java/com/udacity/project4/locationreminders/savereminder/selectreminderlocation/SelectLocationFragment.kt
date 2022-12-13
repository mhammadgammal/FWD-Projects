package com.udacity.project4.locationreminders.savereminder.selectreminderlocation


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSelectLocationBinding
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

    companion object {
        private const val REQUEST_LOCATION_PERMISSION: Int = 0
    }

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

    private fun getUserLocation(map: GoogleMap) {
        val userCurrentLocation = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (isPermissionGranted()) {

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
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
}
