package green.tautest.data.api

import green.tautest.data.ConnectorMarker
import green.tautest.data.Information
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("v1/locations")
    fun getConnectorLocations(
        @Query("ne_lat") neLat: Double,
        @Query("ne_lng") neLng: Double,
        @Query("sw_lat") swLat: Double,
        @Query("sw_lng") swLng: Double,
        @Query("mode") mode: String = "short"
    ): Single<List<ConnectorMarker>>

    @GET("v1/locations/{locationId}")
    fun getInformation(@Path("locationId") id: String): Single<Information>
}