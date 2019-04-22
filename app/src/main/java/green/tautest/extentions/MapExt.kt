package green.tautest.extentions

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

fun GoogleMap.addMarker(latLng: LatLng) = addMarker(MarkerOptions().position(latLng))
fun GoogleMap.animateCamera(latLng: LatLng) = animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))