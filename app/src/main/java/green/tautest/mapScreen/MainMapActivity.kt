package green.tautest.mapScreen

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.view.View.VISIBLE
import android.widget.Toast
import com.google.android.gms.maps.SupportMapFragment
import green.tautest.R
import green.tautest.base.BaseActivity
import green.tautest.data.Information
import kotlinx.android.synthetic.main.activity_main_map.fabFilter
import kotlinx.android.synthetic.main.activity_main_map.fabMenu
import kotlinx.android.synthetic.main.activity_main_map.fabMyLocation
import kotlinx.android.synthetic.main.activity_main_map.fabSearch
import kotlinx.android.synthetic.main.activity_main_map.textCheapestRoute
import kotlinx.android.synthetic.main.activity_main_map.textQuickestRoute
import kotlinx.android.synthetic.main.include_card_info.cardDetails
import kotlinx.android.synthetic.main.include_card_info.textAddress

class MainMapActivity : BaseActivity<Presenter>(), View {

    private val mapFragment by lazy { supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_map)

        fabSearch.setOnClickListener { presenter.onSearchClick() }
        fabFilter.setOnClickListener { presenter.onFilterClick() }
        fabMyLocation.setOnClickListener { presenter.onMyLocationClick() }

        textCheapestRoute.setOnClickListener { presenter.onCheapestRouteClick() }
        textQuickestRoute.setOnClickListener { presenter.onQuickestRouteClick() }

        fabMenu.setOnClickListener { presenter.onMenuClick() }

        mapFragment.getMapAsync(presenter::onViewInit)
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewResumed()
    }

    override fun onPause() {
        super.onPause()
        presenter.onViewPaused()
    }

    override fun showSearch() = showToast("navigate to search")
    override fun showFilter() = showToast("navigate to filter")
    override fun switchToList() = showToast("switch to list")

    override fun showInformation(information: Information) {
        cardDetails.visibility = VISIBLE
        `@+id/textConnectorName`.text = information.name
        textAddress.text = "${information.country}, ${information.city}, ${information.street}"
    }

    override fun showNeedPermission() = showToast("Can't work without permission")

    override fun showLocationSettings() = startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS))

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}