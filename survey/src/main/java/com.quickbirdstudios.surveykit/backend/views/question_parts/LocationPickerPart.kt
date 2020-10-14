package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.quickbirdstudios.surveykit.R
import com.quickbirdstudios.surveykit.SurveyTheme
import com.quickbirdstudios.surveykit.backend.address.AddressSuggestionProvider
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart

class LocationPickerPart @JvmOverloads constructor(
    context: Context,
    private val lifecycle: Lifecycle,
    addressProvider: AddressSuggestionProvider,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), StyleablePart, LifecycleObserver {

    //region Members
    private var search: AutoCompleteTextView
    private val mapView: MapView
    private lateinit var map: GoogleMap
    private val frame = FrameLayout(context)
    private val locationMarker = ImageView(context)

    private val latitude: Double?
        get() {
            return if (::map.isInitialized) {
                map.cameraPosition.target.latitude
            } else {
                null
            }
        }

    private val longitude: Double?
        get() {
            return if (::map.isInitialized) {
                map.cameraPosition.target.longitude
            } else {
                null
            }
        }

    private lateinit var mapReadySelection: Selection

    //endregion

    //region Public API

    var selected: Selection
        get() = Selection(
            latitude, longitude
        )
        set(selected) {
            if (::map.isInitialized && selected.longitude != null && selected.latitude != null) {
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            selected.longitude,
                            selected.latitude
                        ),
                        15f
                    )
                )
            } else {
                mapReadySelection = selected
            }
        }

    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) {}

    //endregion

    //region Subtypes

    data class Selection(val latitude: Double?, val longitude: Double?)

    //endregion

    init {
        this.orientation = VERTICAL
        this.gravity = Gravity.CENTER

        mapView = MapView(
            context,
            GoogleMapOptions().scrollGesturesEnabled(true)
                .tiltGesturesEnabled(true).rotateGesturesEnabled(false)
        )

        search = AutoCompleteTextView(context)
        search.setHint(android.R.string.search_go)
        search.setSingleLine(true)
        search.setCompoundDrawables(
            resources.getDrawable(R.drawable.ic_baseline_search_24),
            null,
            null,
            null
        )
        search.compoundDrawablePadding = 32

        mapView.getMapAsync { map ->
            this.map = map
            if (::mapReadySelection.isInitialized) {
                selected = mapReadySelection
            }

            if (ActivityCompat.checkSelfPermission(
                    mapView.context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mapView.context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO ask location permission
                return@getMapAsync
            }
            this.map.isMyLocationEnabled = true
            this.map.uiSettings.isMyLocationButtonEnabled = true

            this.map.setOnMyLocationChangeListener { location ->
                if (selected.latitude == 0.0 && selected.longitude == 0.0) {
                    this.map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                location.latitude,
                                location.longitude
                            ),
                            15f
                        )
                    )
                }
            }

            this.map.setOnCameraMoveStartedListener { reason ->
                if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                    locationMarker.animate().translationY(-10f)
                        .setInterpolator(BounceInterpolator()).setDuration(300)
                        .start()
                }
            }

            this.map.setOnCameraIdleListener {
                locationMarker.animate().translationY(10f)
                    .setInterpolator(BounceInterpolator()).setDuration(300)
                    .start()
            }
        }
        lifecycle.addObserver(this)

        this.addView(search)

        locationMarker.setImageResource(R.drawable.ic_baseline_location_on)
        frame.addView(mapView, LayoutParams(LayoutParams.MATCH_PARENT, 800))
        val locationMarkerParams =
            FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        locationMarkerParams.gravity = Gravity.CENTER
        frame.addView(locationMarker, locationMarkerParams)

        this.addView(frame, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))

        search.threshold = 4
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (!search.isPerformingCompletion) {
                        addressProvider.input(s.toString())
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        search.setOnItemClickListener { adapter: AdapterView<*>, view: View, pos: Int, id: Long ->
            val addressSuggestion =
                adapter.adapter.getItem(pos) as AddressSuggestionProvider.AddressSuggestion
            selected =
                Selection(addressSuggestion.location.latitude, addressSuggestion.location.longitude)
            search.dismissDropDown()
            search.hideKeyboard()
        }

        addressProvider.onSuggestionListReady = { suggestions ->
            val adapter: ArrayAdapter<AddressSuggestionProvider.AddressSuggestion> =
                ArrayAdapter(
                    search.context,
                    android.R.layout.simple_list_item_1,
                    suggestions
                )
            search.setAdapter(adapter)
            search.showDropDown()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            val p = parent
            p?.requestDisallowInterceptTouchEvent(true)
        }
        return false
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lifecycle.removeObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        mapView.onCreate(null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        mapView.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        mapView.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        mapView.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        mapView.onStop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mapView.onDestroy()
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
