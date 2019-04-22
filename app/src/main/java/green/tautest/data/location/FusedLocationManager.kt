package green.tautest.data.location

import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import javax.inject.Inject

class FusedLocationManager @Inject constructor() : LocationCallback(), ILocationManager {

    companion object {
        private const val intervalMs = 10000L
        private const val fastestIntervalMs = 10000L
    }

    @Inject lateinit var fusedLocationProvider: FusedLocationProviderClient
    @Inject lateinit var locationManager: LocationManager
    @Inject lateinit var geoCoder: Geocoder

    private val locationRequest by lazy {
        LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = intervalMs
            fastestInterval = fastestIntervalMs
        }
    }

    private var onLocationUpdates: (Pair<LatLng, String>) -> Unit = {}

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override fun connect(onLocationUpdates: (Pair<LatLng, String>) -> Unit) {
        this.onLocationUpdates = onLocationUpdates
        fusedLocationProvider.requestLocationUpdates(locationRequest, this, Looper.getMainLooper())
    }

    override fun disconnect() {
        fusedLocationProvider.removeLocationUpdates(this)
    }

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override fun getLastLocation(): Single<Pair<LatLng, String>> {
        return Single.create { emitter ->
            fusedLocationProvider.lastLocation
                    .addOnCompleteListener { task ->
                        task.apply {
                            if (isSuccessful) {
                                result?.let { location ->
                                    emitter.onSuccess(getLatLngAddressPair(location))
                                }
                            }
                        }
                    }
        }
    }

    override fun onLocationResult(result: LocationResult) {
        super.onLocationResult(result)
        onLocationUpdates.invoke(getLatLngAddressPair(result.lastLocation))
    }

    override fun isLocationEnabled(): Boolean {
        val isGps = locationManager.isProviderEnabled(GPS_PROVIDER)
        val isNetwork = locationManager.isProviderEnabled(NETWORK_PROVIDER)
        return isGps || isNetwork
    }

    private fun getLatLngAddressPair(location: Location): Pair<LatLng, String> {
        val latLng = LatLng(location.latitude, location.longitude)
        val address = geoCoder.getFromLocation(location.latitude, location.longitude, 1)[0]
                .let { "${it.thoroughfare}, ${it.subThoroughfare}" }
        return latLng to address
    }
}