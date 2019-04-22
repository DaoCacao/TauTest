package green.tautest.mapScreen

import com.google.android.gms.maps.GoogleMap
import green.tautest.base.MvpPresenter
import green.tautest.base.MvpView
import green.tautest.data.Information

interface View : MvpView {
    fun showSearch()
    fun showFilter()
    fun switchToList()

    fun showInformation(information: Information)

    fun showNeedPermission()

    fun showLocationSettings()
}

interface Presenter : MvpPresenter {
    fun onViewInit(map: GoogleMap)
    fun onViewResumed()
    fun onViewPaused()

    fun onSearchClick()
    fun onFilterClick()
    fun onMyLocationClick()

    fun onCheapestRouteClick()
    fun onQuickestRouteClick()

    fun onMenuClick()
}