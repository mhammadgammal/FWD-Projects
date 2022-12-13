package com.udacity.project4.locationreminders.savereminder

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.locationreminders.geofence.GeofencingUtil
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import kotlinx.android.synthetic.main.fragment_save_reminder.*
import org.koin.android.ext.android.inject

class SaveReminderFragment : BaseFragment() {
    //Get the view model this time as a single to be shared with the another fragment
    override val _viewModel: SaveReminderViewModel by inject()
    private lateinit var binding: FragmentSaveReminderBinding
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var geofence: GeofencingUtil
    private lateinit var reminder: ReminderDataItem
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_save_reminder, container, false)

        setDisplayHomeAsUpEnabled(true)

        binding.viewModel = _viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.selectLocation.setOnClickListener {
            //            Navigate to another fragment to get the user location
            _viewModel.navigationCommand.value =
                NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToSelectLocationFragment())
        }
        geofencingClient = GeofencingClient(requireActivity())
        geofence = GeofencingUtil(requireActivity())

        binding.saveReminder.setOnClickListener {
            val title = _viewModel.reminderTitle.value
            val description = _viewModel.reminderDescription.value
            val location = _viewModel.reminderSelectedLocationStr.value
            val latitude = _viewModel.latitude.value
            val longitude = _viewModel.longitude.value
            reminder = ReminderDataItem(
                title,
                description,
                location,
                latitude,
                longitude
            )
            if(checkDeviceLocationStatues())
                addGeofence(reminder)
            else
                Snackbar.make(binding.root, R.string.location_required_error, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok){
                        checkDeviceLocationStatues()
                    }.show()
            _viewModel.validateAndSaveReminder(reminder)
        }
    }

    private fun addGeofence(reminder: ReminderDataItem) {
        val pendingIntent = geofence.pendingIntent()
        val geofence = geofence.geofence(
            reminder.id,
            LatLng(reminder.latitude!!, reminder.longitude!!),
            100f
        )
        val geofencingRequest = this.geofence.requestGeofence(geofence)

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "geofence added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                exception as ApiException
                when(exception.statusCode){
                    GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> Log.d(
                        tag,
                        "addGeofence: GEOFENCE_NOT_AVAILABLE"
                    )
                    else -> Log.d(tag, "addGeofence: An Error Occurred")
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        //make sure to clear the view model after destroy, as it's a single view model.
        _viewModel.onClear()
    }

    private fun checkDeviceLocationStatues(): Boolean{
        var locationState = true
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }

        val locationRequestBuilder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(requireActivity())

        settingsClient.checkLocationSettings(locationRequestBuilder.build()).apply {
            addOnCompleteListener{
                locationState = true
            }
            addOnFailureListener{
                if(it is ResolvableApiException){
                    try {
                        it.startResolutionForResult(
                            requireActivity(),
                            REQUEST_TURN_DEVICE_LOCATION_ON
                        )
                    }catch (ex: java.lang.Exception){
                        Log.d("SaveFragment", "checkDeviceLocationStatues: Error")
                    }
                }else{
                    locationState = false
                }
            }
        }
        return locationState
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_TURN_DEVICE_LOCATION_ON){
            if (resultCode == Activity.RESULT_OK){
                addGeofence(reminder)
            }else
                checkDeviceLocationStatues()
        }
    }

    companion object{
       private const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
    }
}
