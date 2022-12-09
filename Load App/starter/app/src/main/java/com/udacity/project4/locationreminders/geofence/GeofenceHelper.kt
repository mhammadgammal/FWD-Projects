package com.udacity.project4.locationreminders.geofence

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

class GeofenceHelper(val context: Context): ContextWrapper(context) {
    private lateinit var pendingIntent: PendingIntent
companion object{
    val GEOFENCE_ACTION = "GEOFENCE_ACTION"
}
    @SuppressLint("UnspecifiedImmutableFlag")
    fun getPendingIntent(reminderDataItem: ReminderDataItem): PendingIntent {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        intent.apply {
            putExtra("reminder_location", reminderDataItem.location)
            putExtra("reminder_lat", reminderDataItem.latitude)
            putExtra("reminder_lon", reminderDataItem.longitude)
            putExtra("reminder_title", reminderDataItem.title)
            putExtra("reminder_description", reminderDataItem.description)
            action = GEOFENCE_ACTION
        }
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return pendingIntent
    }

    fun addGeofence(id:String, latLng: LatLng, radius: Float): Geofence{
        return Geofence.Builder()
            .setCircularRegion(latLng.latitude, latLng.longitude, radius)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setRequestId(id)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }

    fun geofenceRequest(geofence: Geofence): GeofencingRequest{
        return GeofencingRequest.Builder()
            .addGeofence(geofence)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()
    }


}