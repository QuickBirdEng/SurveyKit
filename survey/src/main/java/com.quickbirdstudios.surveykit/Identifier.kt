package com.quickbirdstudios.surveykit

import android.os.Parcelable
import java.util.UUID
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Identifier() : Parcelable

@Parcelize
data class TaskIdentifier( val id: String = UUID.randomUUID().toString()) :
    Identifier(),
    Parcelable

@Parcelize
data class StepIdentifier(
     val id: String = UUID.randomUUID().toString()
) : Identifier(), Parcelable
