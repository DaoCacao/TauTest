package green.tautest.mapScreen

import android.Manifest.permission.ACCESS_FINE_LOCATION
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE
import com.tbruyelle.rxpermissions2.RxPermissions
import green.tautest.base.BasePresenter
import green.tautest.data.ConnectorMarker
import green.tautest.data.api.IApiManager
import green.tautest.data.location.ILocationManager
import green.tautest.extentions.animateCamera
import green.tautest.extentions.saveSubscribe
import green.tautest.extentions.scheduleIo
import javax.inject.Inject

class MainMapPresenter @Inject constructor() : BasePresenter<View>(), Presenter {

    @Inject lateinit var rxPermissions: RxPermissions
    @Inject lateinit var locationManager: ILocationManager
    @Inject lateinit var apiManager: IApiManager

    private lateinit var map: GoogleMap

    private val hasLocationPermission get() = rxPermissions.isGranted(ACCESS_FINE_LOCATION)
    private val isLocationEnabled get() = locationManager.isLocationEnabled()
    private val latLngBounds get() = map.projection.visibleRegion.latLngBounds

    private var watchingByLocation = false

    override fun onViewInit(map: GoogleMap) {
        this.map = map.apply {
            uiSettings.apply {
                //TODO --> disable unused ui
                isCompassEnabled = false
                isMyLocationEnabled = false
            }
            setOnCameraMoveStartedListener { if (it == REASON_GESTURE) watchingByLocation = false }
            setOnMarkerClickListener {
                apiManager.getInformation(it.title)
                    .scheduleIo()
                    .saveSubscribe(view::showInformation)
                true
            }
            setOnCameraIdleListener {
                apiManager.getConnectorLocations(latLngBounds)
                    .scheduleIo()
                    .saveSubscribe(::addConnectorToMap)
            }
        }
    }

    override fun onViewResumed() {
        if (hasLocationPermission && isLocationEnabled)
            locationManager.connect { if (watchingByLocation) map.animateCamera(it.first) }
    }

    override fun onViewPaused() {
        locationManager.disconnect()
    }

    override fun onSearchClick() = view.showSearch()
    override fun onFilterClick() = view.showFilter()

    override fun onMyLocationClick() {
        rxPermissions.requestEachCombined(ACCESS_FINE_LOCATION)
            .subscribe { permission ->
                when {
                    permission.granted ->
                        if (isLocationEnabled) {
                            watchingByLocation = true
                            locationManager.getLastLocation().saveSubscribe { map.animateCamera(it.first) }
                        } else {
                            view.showLocationSettings()
                        }
                    permission.shouldShowRequestPermissionRationale -> view.showNeedPermission()
                    else -> view.showNeedPermission()
                }
            }
    }

    override fun onCheapestRouteClick() {
    }

    override fun onQuickestRouteClick() {
    }

    override fun onMenuClick() = view.switchToList()

    private fun addConnectorToMap(connectors: List<ConnectorMarker>) {
        map.clear()
        connectors.forEach { map.addMarker(it.toMarker()) }
    }
}