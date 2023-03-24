package com.quickbirdstudios.surveykit.backend.helpers.extensions

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat.checkSelfPermission

fun Context.isLocationPermissionGranted(): Boolean =
    checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED &&
            checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
