package com.quickbirdstudios.surveykit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
open class Identifier(open val id: String) : Parcelable

@Parcelize
data class TaskIdentifier(override val id: String = UUID.randomUUID().toString()) :
    Identifier(id),
    Parcelable

@Parcelize
data class StepIdentifier(
    override val id: String = UUID.randomUUID().toString()
) : Identifier(id), Parcelable
