package green.tautest.data

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.annotations.SerializedName
import green.tautest.R

class ConnectorMarker(
    @SerializedName("id") val id: String,
    @SerializedName("coordinates") val coordinates: Coordinates,
    @SerializedName("connector_statuses") val status: Status
) {
    fun toMarker(): MarkerOptions {
        return MarkerOptions()
            .position(coordinates.toLatLng())
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round))
            .title(id)
    }
}

class Information(
    @SerializedName("id") val id: String,
    @SerializedName("manager_id") val managerId: String,
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("city") val city: String,
    @SerializedName("street") val street: String,
    @SerializedName("postcode") val postcode: String,
    @SerializedName("coordinates") val coordinates: Coordinates,
    @SerializedName("rating") val rating: Int
)

class Coordinates(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
) {
    fun toLatLng() = LatLng(lat, lng)
}

class Status(
    @SerializedName("available") val available: Int,
    @SerializedName("busy") val busy: Int,
    @SerializedName("out_of_use") val outOfUse: Int
)