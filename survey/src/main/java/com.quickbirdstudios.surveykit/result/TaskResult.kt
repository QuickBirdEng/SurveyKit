package com.quickbirdstudios.surveykit.result

import android.os.Parcelable
import com.quickbirdstudios.surveykit.TaskIdentifier
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskResult(
    override val id: TaskIdentifier,
    override val startDate: Date,
    override var endDate: Date = Date(),
    val results: List<StepResult>
) : Result, Parcelable
