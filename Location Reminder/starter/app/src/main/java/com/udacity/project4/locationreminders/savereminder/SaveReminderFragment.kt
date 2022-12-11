package com.udacity.project4.locationreminders.savereminder

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.BuildConfig
import com.udacity.project4.R
import com.udacity.project4.base.BaseFragment
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.FragmentSaveReminderBinding
import com.udacity.project4.locationreminders.geofence.GeofenceHelper
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import com.udacity.project4.utils.setDisplayHomeAsUpEnabled
import org.koin.android.ext.android.inject

class SaveReminderFragment : BaseFragment() {
    //Get the view model this time as a single to be shared with the another fragment
    override val _viewModel: SaveReminderViewModel by inject()
    private lateinit var binding: FragmentSaveReminderBinding
    private lateinit var geofenceClient: GeofencingClient
    private lateinit var geofenceHelper: GeofenceHelper
    private lateinit var reminderItem: ReminderDataItem

    companion object {
        const val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 33
        const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
        const val LOCATION_PERMISSION_INDEX = 0
        const val BACKGROUND_LOCATION_PERMISSION_INDEX = 1
        const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
        const val TAG = "SaveReminderFragment"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_save_reminder, container, false)

        setDisplayHomeAsUpEnabled(true)

        binding.viewModel = _viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geofenceHelper = GeofenceHelper(requireActivity())
        geofenceClient = GeofencingClient(requireActivity())
        binding.lifecycleOwner = this
        binding.selectLocation.setOnClickListener {
            _viewModel.navigationCommand.value =
                NavigationCommand.To(SaveReminderFragmentDirections.actionSaveReminderFragmentToSelectLocationFragment())
        }

        binding.saveReminder.setOnClickListener {
            val reminderTitle = _viewModel.reminderTitle.value
            val reminderDescription = _viewModel.reminderDescription.value
            val reminderLocation = _viewModel.reminderSelectedLocationStr.value
            val reminderLatitude = _viewModel.latitude.value
            val reminderLongitude = _viewModel.longitude.value
            reminderItem = ReminderDataItem(
                reminderTitle,
                reminderDescription,
                reminderLocation,
                reminderLatitude,
                reminderLongitude
            )
            checkDeviceLocationSettingsAndStartGeofence()
            _viewModel.validateAndSaveReminder(reminderItem)


        }


    }

    override fun onStart() {
        super.onStart()
        checkPermissions()

    }

    private fun checkPermissions() {
        if (!foregroundAndBackgroundLocationPermissionApproved())
            requestForegroundAndBackgroundLocationPermissions()
    }

    private fun foregroundAndBackgroundLocationPermissionApproved(): Boolean {
        val foregroundLocationApproved = (
                PackageManager.PERMISSION_GRANTED ==
                        ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                )
        val backgroundLocationApproved = (
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    PackageManager.PERMISSION_GRANTED ==
                            ActivityCompat.checkSelfPermission(
                                requireContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )

                } else true
                )

        return foregroundLocationApproved && backgroundLocationApproved
    }

    @TargetApi(29)
    private fun requestForegroundAndBackgroundLocationPermissions() {
        if (foregroundAndBackgroundLocationPermissionApproved())
            return
        var permissionsArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        val resultCode = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                permissionsArray += Manifest.permission.ACCESS_BACKGROUND_LOCATION
                REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE
            }
            else -> REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
        }
        Log.d(TAG, "Request foreground only location permission")
        ActivityCompat.requestPermissions(
            requireActivity(),
            permissionsArray,
            resultCode
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (
            grantResults.isEmpty() ||
            grantResults[LOCATION_PERMISSION_INDEX] == PackageManager.PERMISSION_DENIED ||
            (requestCode == REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE &&
                    grantResults[BACKGROUND_LOCATION_PERMISSION_INDEX] ==
                    PackageManager.PERMISSION_DENIED)
        ) {
            Snackbar.make(
                binding.root,
                R.string.permission_denied_explanation,
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(R.string.settings) {
                    startActivity(Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                }.show()
        } else {
            checkDeviceLocationSettingsAndStartGeofence()
        }
    }

    private fun checkDeviceLocationSettingsAndStartGeofence(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_LOW_POWER
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(requireActivity())

        settingsClient.checkLocationSettings(builder.build()).apply {
            addOnCompleteListener {
                addGeofence(reminderItem)
            }
            addOnFailureListener { exception ->
                if (exception is ResolvableApiException && resolve) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        exception.startResolutionForResult(
                            requireActivity(),
                            REQUEST_TURN_DEVICE_LOCATION_ON
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        Log.d(TAG, "Error getting location settings resolution: " + sendEx.message)
                    }
                } else {
                    Snackbar.make(
                        binding.root,
                        R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                    ).setAction(android.R.string.ok) {
                        checkDeviceLocationSettingsAndStartGeofence()
                    }.show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun addGeofence(reminderDataItem: ReminderDataItem) {
        val pendingIntent = geofenceHelper.getPendingIntent()
        val geofence = geofenceHelper.addGeofence(
            reminderDataItem.id,
            LatLng(reminderDataItem.latitude!!, reminderDataItem.latitude!!),
            100f
        )
        val geofencingRequest = geofenceHelper.geofenceRequest(geofence)


        geofenceClient.addGeofences(geofencingRequest, pendingIntent)
            .addOnSuccessListener {
                Log.d(TAG, "addGeofence: geofence added successfully")
            }
            .addOnFailureListener { ex ->
                ex as ApiException
                when (ex.statusCode) {
                    GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> Log.d(
                        tag,
                        "addGeofence: GEOFENCE_NOT_AVAILABLE"
                    )
                    else -> Log.d(tag, "addGeofence: An Error Occurred")
                }

            }
    }


    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_TURN_DEVICE_LOCATION_ON) {
            if (resultCode == Activity.RESULT_OK) {
                addGeofence(reminderItem)
            } else {
                checkDeviceLocationSettingsAndStartGeofence(false)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewModel.onClear()
    }


}
