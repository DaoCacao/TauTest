package green.tautest.data.location

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Geocoder
import android.location.LocationManager
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class FusedLocationModule {

    @Binds
    abstract fun fisedLocationManager(manager: FusedLocationManager): ILocationManager

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun fusedLocationProvider(context: Context) =
            LocationServices.getFusedLocationProviderClient(context)

        @Provides
        @JvmStatic
        fun locationManager(context: Context) =
            context.getSystemService(LOCATION_SERVICE) as LocationManager

        @Provides
        @JvmStatic
        fun geoCoder(context: Context) = Geocoder(context)
    }
}