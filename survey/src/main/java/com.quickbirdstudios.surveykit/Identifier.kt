package com.quickbirdstudios.surveykit

import android.os.Parcelable
import java.util.UUID
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Identifier(open val id: String) : Parcelable

@Parcelize
data class TaskIdentifier(override val id: String = UUID.randomUUID().toString()) : Identifier(id),
    Parcelable

@Parcelize
data class StepIdentifier(
    override val id: String = UUID.randomUUID().toString()
) : Identifier(id), Parcelable
