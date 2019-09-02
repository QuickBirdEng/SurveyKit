package com.quickbirdstudios.survey_kit.public_api.result

import android.os.Parcelable
import com.quickbirdstudios.survey_kit.public_api.Identifier
import java.util.*

interface Result : Parcelable {
    val id: Identifier
    val startDate: Date
    var endDate: Date
}

