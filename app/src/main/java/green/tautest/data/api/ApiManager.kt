package green.tautest.data.api

import com.google.android.gms.maps.model.LatLngBounds
import green.tautest.data.ConnectorMarker
import green.tautest.data.Information
import io.reactivex.Single
import javax.inject.Inject

class ApiManager @Inject constructor() : IApiManager {

    @Inject lateinit var api: Api

    override fun getConnectorLocations(bounds: LatLngBounds): Single<List<ConnectorMarker>> {
        return api.getConnectorLocations(
            bounds.northeast.latitude,
            bounds.northeast.longitude,
            bounds.southwest.latitude,
            bounds.southwest.longitude
        )
    }

    override fun getInformation(id: String): Single<Information> {
        return api.getInformation(id)
    }
}