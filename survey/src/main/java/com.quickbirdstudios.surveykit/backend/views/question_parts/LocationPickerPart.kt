package com.quickbirdstudios.surveykit.backend.views.question_parts

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.quickbirdstudios.surveykit.backend.address.AddressSuggestion
import com.quickbirdstudios.surveykit.backend.address.AddressSuggestionProvider
import com.quickbirdstudios.surveykit.backend.helpers.extensions.afterTextChanged
import com.quickbirdstudios.surveykit.backend.helpers.extensions.hideKeyboard
import com.quickbirdstudios.surveykit.backend.helpers.extensions.isLocationPermissionGranted
import com.quickbirdstudios.surveykit.backend.views.main_parts.StyleablePart

@SuppressLint("MissingPermission")
class LocationPickerPart @JvmOverloads constructor(
    context: Context,
    private val lifecycle: Lifecycle,
    addressProvider: AddressSuggestionProvider,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), StyleablePart, LifecycleObserver {

    //region Members
    private var search: AutoCompleteTextView
    private val mapView: MapView by lazy {
        MapView(
            context,
            GoogleMapOptions().scrollGesturesEnabled(true)
                .tiltGesturesEnabled(true).rotateGesturesEnabled(false)
        )
    }
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
        get() = Selection(latitude, longitude)
        set(selected) {
            if (::map.isInitialized && selected.longitude != null && selected.latitude != null) {
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(selected.latitude, selected.longitude),
                        15f
                    )
                )
            } else {
                mapReadySelection = selected
            }
        }

    //endregion

    //region Overrides

    override fun style(surveyTheme: SurveyTheme) = Unit

    //endregion

    //region Subtypes

    data class Selection(val latitude: Double?, val longitude: Double?)

    //endregion

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            parent?.requestDisallowInterceptTouchEvent(true)
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

    init {
        this.orientation = VERTICAL
        this.gravity = Gravity.CENTER

        search = AutoCompleteTextView(context).apply {
            id = R.id.locationAnswerSearchField
            setHint(android.R.string.search_go)
            setSingleLine(true)
            setCompoundDrawables(
                resources.getDrawable(R.drawable.ic_baseline_search_24),
                null,
                null,
                null
            )
            compoundDrawablePadding =
                resources.getDimensionPixelSize(R.dimen.search_drawable_padding)
        }

        mapView.getMapAsync { map ->
            this.map = map
            if (::mapReadySelection.isInitialized) {
                selected = mapReadySelection
            }

            with(this.map) {
                if (context.isLocationPermissionGranted()) {
                    isMyLocationEnabled = true
                }

                uiSettings.isMyLocationButtonEnabled = true

                map.setOnMyLocationChangeListener { location ->
                    if (selected.latitude == 0.0 && selected.longitude == 0.0) {
                        moveCamera(
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

                setOnCameraMoveStartedListener { reason ->
                    if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                        locationMarker.animate().translationY(-10f)
                            .setInterpolator(BounceInterpolator()).setDuration(300)
                            .start()
                    }
                }

                setOnCameraIdleListener {
                    locationMarker.animate().translationY(10f)
                        .setInterpolator(BounceInterpolator()).setDuration(300)
                        .start()
                }
            }

            this.map
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

        with(search) {
            threshold = 4
            afterTextChanged { s ->
                s.let {
                    if (!isPerformingCompletion && s.isNotEmpty()) {
                        addressProvider.input(s)
                    }
                }
            }
            setOnItemClickListener { adapter: AdapterView<*>, view: View, pos: Int, id: Long ->
                val addressSuggestion =
                    adapter.adapter.getItem(pos) as AddressSuggestion
                selected =
                    Selection(
                        addressSuggestion.location.latitude,
                        addressSuggestion.location.longitude
                    )
                dismissDropDown()
                hideKeyboard()
            }
        }

        addressProvider.onSuggestionListReady = { suggestions ->
            val adapter: ArrayAdapter<AddressSuggestion> =
                ArrayAdapter(
                    search.context,
                    android.R.layout.simple_list_item_1,
                    suggestions
                )
            search.setAdapter(adapter)
            search.showDropDown()
        }
    }
}
