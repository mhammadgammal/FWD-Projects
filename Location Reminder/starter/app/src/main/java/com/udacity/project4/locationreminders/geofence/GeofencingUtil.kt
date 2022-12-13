package com.udacity.project4.locationreminders.geofence

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng

class GeofencingUtil(val context: Context): ContextWrapper(context) {
    companion object{
        private const val GEOFENCE_REQUEST_ACTION = "GEOFENCE_REQUEST_ACTION"
    }

    fun pendingIntent(): PendingIntent {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
            .apply {
                action = GEOFENCE_REQUEST_ACTION
            }

        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun geofence(id:String, locationLatLng: LatLng, radius: Float): Geofence{
        return Geofence.Builder()
            .setCircularRegion(locationLatLng.latitude, locationLatLng.longitude, radius)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setRequestId(id)
            .build()

    }

    fun requestGeofence(geofence: Geofence): GeofencingRequest? {
        return GeofencingRequest.Builder()
            .addGeofence(geofence)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()
    }

}