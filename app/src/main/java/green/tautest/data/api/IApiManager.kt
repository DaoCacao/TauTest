package green.tautest.data.api

import com.google.android.gms.maps.model.LatLngBounds
import green.tautest.data.ConnectorMarker
import green.tautest.data.Information
import io.reactivex.Single

interface IApiManager {
    fun getConnectorLocations(bounds: LatLngBounds): Single<List<ConnectorMarker>>
    fun getInformation(id: String): Single<Information>
}