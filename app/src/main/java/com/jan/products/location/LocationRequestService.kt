package com.jan.products.location

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.jan.products.MainActivity
import com.jan.products.ui.contact.ContactFragment
import com.jan.products.util.Singleton
import java.text.DateFormat
import java.util.*

class LocationRequestService(context: MainActivity) : LocationListener,
    OnSuccessListener<LocationSettingsResponse>, OnFailureListener {

    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationSettingsRequest: LocationSettingsRequest
    private var mCurrentLocation: Location? = null
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()
    private var mRequestingLocationUpdates: Boolean? = null
    private var mLastUpdateTime: String
    private var getLocation: Boolean? = false
    private var mContext: MainActivity =
        MainActivity()
    private val singleton = Singleton

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationCallback: LocationCallback? = null
    var contactFragment: ContactFragment? = null

    init {
        this.mContext = context
        mRequestingLocationUpdates = false
        mLastUpdateTime = ""
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        mSettingsClient = LocationServices.getSettingsClient(context)
        createLocationCallback()
        createLocationRequest()
        buildLocationSettingsRequest()
    }

    override fun onLocationChanged(location: Location?) {
        mCurrentLocation = location
        mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
        updateLocationUI()
    }

    override fun onSuccess(p0: LocationSettingsResponse?) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
        updateLocationUI()
    }

    override fun onFailure(p0: Exception) {
        val exception = (p0 as ApiException)
        when (exception.statusCode) {
            LocationSettingsStatusCodes.SUCCESS -> startLocationUpdates()
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                try {
                    val rae: ResolvableApiException = (p0 as ResolvableApiException)
                    rae.startResolutionForResult(
                        mContext,
                        REQUEST_LOCATION
                    )
                } catch (sie: IntentSender.SendIntentException) {

                }
            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                mRequestingLocationUpdates = false
            }
        }
    }

    private fun updateLocationUI() {
        if (mCurrentLocation != null) {
            latitude = mCurrentLocation!!.latitude
            longitude = mCurrentLocation!!.longitude
            //stopGps()
            singleton.latitude = latitude
            singleton.longitude = longitude
            if (contactFragment != null) {
                contactFragment!!.latitudeLocation = mCurrentLocation!!.latitude
                contactFragment!!.longitudeLocation = mCurrentLocation!!.longitude
                contactFragment!!.location(mCurrentLocation!!)
            }
        }
    }

    /*
    private fun stopGps() {
        try {
            mFusedLocationClient!!.removeLocationUpdates(mLocationCallback).addOnCompleteListener {
                mRequestingLocationUpdates = false
            }
        } catch (e: Exception) {

        }
    }

    */

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest.create()
        mLocationRequest.interval =
            UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.fastestInterval =
            FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        builder.setAlwaysShow(true)
        mLocationSettingsRequest = builder.build()
        mSettingsClient!!.checkLocationSettings(mLocationSettingsRequest).addOnSuccessListener(this)
            .addOnFailureListener(this)
    }

    companion object {
        const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
        internal const val REQUEST_LOCATION = 199
    }

    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                mCurrentLocation = locationResult.lastLocation
                mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
                updateLocationUI()
            }
        }
    }

    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        builder.setAlwaysShow(true)
        mLocationSettingsRequest = builder.build()
    }

    private fun startLocationUpdates() {
        getLocation = true
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        LocationServices.getFusedLocationProviderClient(mContext)
            .requestLocationUpdates(mLocationRequest, object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult?) {
                    super.onLocationResult(p0)
                    mRequestingLocationUpdates = true
                }
            }, Looper.myLooper())
    }
}
