package com.jan.products.ui.contact

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.jan.products.MainActivity
import com.jan.products.R
import com.jan.products.base.BaseFragment
import com.jan.products.factory.ViewModelFactory
import com.jan.products.location.LocationRequestService
import javax.inject.Inject

class ContactFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private lateinit var mapAcceptService: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    var viewModelFactory: ViewModelFactory? = null
        @Inject set
    private lateinit var contactViewModel: ContactViewModel
    var latitudeLocation = 0.0
    var longitudeLocation = 0.0
    private var bandLoadLocation = false
    private var loadMap = false
    private lateinit var locationService: LocationRequestService

    override fun layoutRes(): Int {
        return R.layout.fragment_contact
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contactViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ContactViewModel::class.java)
        locationService = LocationRequestService(activity!! as MainActivity)
        locationService.contactFragment = this

        mapAcceptService = SupportMapFragment.newInstance();
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.map, mapAcceptService)
        fragmentTransaction.commit()
        mapAcceptService.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.googleMap = map!!
        if (googleMap != null) {
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            googleMap.uiSettings.isScrollGesturesEnabled = true
        }

        googleMap.setOnMapLoadedCallback {
            loadMap = true
            if (!bandLoadLocation) {
                bandLoadLocation = true
                initialPosition(latitudeLocation, longitudeLocation)
            }
        }
        googleMap.setOnCameraIdleListener(this)
    }

    override fun onCameraIdle() {

    }

    fun location(location: Location) {
        this.latitudeLocation = location.latitude
        this.longitudeLocation = location.longitude
        if (latitudeLocation != 0.0 && latitudeLocation != 0.0) {
            if (loadMap) {
                bandLoadLocation = true
                initialPosition(latitudeLocation, longitudeLocation)
            }
        }
    }

    private fun initialPosition(latitudeLocation: Double, longitudeLocation: Double) {

        if (this.latitudeLocation == 0.0 && this.longitudeLocation == 0.0) {
            this.latitudeLocation = latitudeLocation
            this.longitudeLocation = longitudeLocation
        }
        googleMap.clear()
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    latitudeLocation,
                    longitudeLocation
                ), 16f
            )
        )
    }

}
