package green.tautest.data.location

import androidx.annotation.RequiresPermission
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface ILocationManager {

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    fun connect(onLocationUpdates: (Pair<LatLng, String>) -> Unit = {})

    fun disconnect()

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    fun getLastLocation(): Single<Pair<LatLng, String>>

    fun isLocationEnabled(): Boolean
}